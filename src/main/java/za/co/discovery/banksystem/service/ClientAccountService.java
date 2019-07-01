package za.co.discovery.banksystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.banksystem.dao.AtmAllocationRepository;
import za.co.discovery.banksystem.dao.AtmRepository;
import za.co.discovery.banksystem.dao.ClientAccountRepository;
import za.co.discovery.banksystem.dao.ClientRepository;
import za.co.discovery.banksystem.dao.DenominationRepository;
import za.co.discovery.banksystem.exception.ActionNotAllowedException;
import za.co.discovery.banksystem.exception.ClientNotFoundException;
import za.co.discovery.banksystem.exception.DataNotFounfException;
import za.co.discovery.banksystem.model.Atm;
import za.co.discovery.banksystem.model.AtmAllocation;
import za.co.discovery.banksystem.model.Client;
import za.co.discovery.banksystem.model.ClientAccount;
import za.co.discovery.banksystem.model.Denomination;
import za.co.discovery.banksystem.util.Constants;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientAccountService {

  private static final Logger LOG = LoggerFactory.getLogger(ClientAccountService.class);

  @Autowired
  private ClientAccountRepository clientAccountRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AtmRepository atmRepository;

  @Autowired
  private AtmAllocationRepository atmAllocationRepository;

  @Autowired
  private DenominationRepository denominationRepository;


  public List<Object[]> getClientTransactionalAccount(Integer clientId) {

    Client client = clientRepository.findById(clientId).get();
    List<Object[]> accounts = null;

    if(client != null) {
      accounts = clientAccountRepository.findTransactionalClientAccounts(client);
      if(accounts == null || accounts.size() < 1) {
        throw new DataNotFounfException(Constants.NO_ACCOUNTS_MESSAGE);
      }
    } else {
      throw new ClientNotFoundException("Could not find client with id:" + clientId);
    }
    return accounts;
  }

  public List<Object[]> getCurrencyAccountWithConvertedValues(Integer clientId) {

    Client client = clientRepository.findById(clientId).get();

    if(client != null) {
      return clientAccountRepository.getCurrencyAccountWithConvertedValues(client);
    } else {
      throw new ClientNotFoundException("Could not find client with id:" + clientId);
    }

  }

  public Map<Double, Integer> withdrawCash(Integer clientId, Integer atmId, String accountNumber, Double amount) throws ActionNotAllowedException {

    Client client = clientRepository.findById(clientId).get();
    if(client == null) {
      throw new ClientNotFoundException("Could not find client with id:" + clientId);
    }

    ClientAccount cAccount = clientAccountRepository.findById(accountNumber).get();
    if(cAccount == null) {
      throw new DataNotFounfException(Constants.ACCOUNT_NOT_FOUND_MESSAGE);
    }

    Boolean isTransactionalAccount = clientAccountRepository.isAccountTransactional(accountNumber);
    if(!isTransactionalAccount) {
      throw new ActionNotAllowedException(String.format("Withdrawals can only be done from transactional account. %s not transactional", accountNumber));
    }

    Atm atm = atmRepository.findById(atmId).get();
    if(atm == null) {
      throw new ActionNotAllowedException(Constants.ATM_EXCEPTION_MESSAGE);
    }

    if(amount % 10 > 0) {
      throw new ActionNotAllowedException(String.format(Constants.ATM_AMOUNT_MESSAGE,  roundDownToNearestTen(amount)));
    }

    List<AtmAllocation> atmAllocations = atmAllocationRepository.findAllByAtmId(atm.getId());
    Double maxDispensableAmountInAtm = maximumDispensableAmountForAtm(atmAllocations);
    if(maxDispensableAmountInAtm < amount) {
      throw new ActionNotAllowedException(String.format(Constants.ATM_AMOUNT_MESSAGE, maxDispensableAmountInAtm));
    }

    ClientAccount clientAccount = clientAccountRepository.findAllByClientAccountNumberAndClient_Id(accountNumber,clientId);
    Double currentAccountBalance = clientAccount.getDisplayBalance();
    String accountType = clientAccountRepository.getAccountType(accountNumber);

    Map<Double, Integer> dispenseMap = new HashMap<>();

    if(amount > currentAccountBalance)  {
      if(accountType.equals("Cheque Account")) {
        Double chequeAccountLimit = currentAccountBalance + Constants.MAXIMUM_NEGATIVE_BALANCE;

        if(amount > chequeAccountLimit) {
          throw new ActionNotAllowedException(Constants.INSUFFICIENT_FUNDS);
        } else {
          dispenseMap = calculateNotesToDispense(atmAllocations, amount);
        }

      } else {
        throw new ActionNotAllowedException(Constants.INSUFFICIENT_FUNDS);
      }
    } else {
      dispenseMap = calculateNotesToDispense(atmAllocations, amount);
    }

    try {
      updateDbWithdrawCash(dispenseMap, clientAccount, atmAllocations, amount);
    } catch(Exception ex) {
      LOG.error("withdrawCash: error withdrawing cash");
      throw ex;
    }
    return dispenseMap;

  }

  public Map<Double, Integer> calculateNotesToDispense(List<AtmAllocation> atmAllocations, Double amount) throws ActionNotAllowedException {

    List<Denomination> denominations = denominationRepository.findAllByDenominationType_DenominationTypeCodeOrderByValueDesc("N");

    // start dispensing from this denomination
    Denomination highestDenominationLessThanRequestedAmount = getHighestDenominationLessThanRequestedAmount(denominations, amount);

    Double remainingAmount = amount;
    Double currentRemainder = 0.0;
    Double currentTotal = 0.0;

    Map<Double, Integer> dispenseMap = new HashMap<>();

    for(Denomination d : denominations) {

      if(d.getValue() <= highestDenominationLessThanRequestedAmount.getValue() && atmContainsDenomination(atmAllocations,d) && remainingAmount >= d.getValue()) { //start here

        int noOfDenominationsInAtm = getDenominationsCountInAtm(atmAllocations,d); // how many of these denoms do I have in this ATM
        Double currentDenomValue = d.getValue();
        Integer numberOfCurrentDenom = ((Double)(remainingAmount / currentDenomValue)).intValue(); // how many of these denoms do I need

        if(noOfDenominationsInAtm <= numberOfCurrentDenom) { // I have less than I need
          // use all of them
          numberOfCurrentDenom = noOfDenominationsInAtm;
          currentRemainder = remainingAmount - (currentDenomValue * noOfDenominationsInAtm);

        } else { // I have more than I need
          currentRemainder = remainingAmount % currentDenomValue;

        }
        currentTotal += remainingAmount - currentRemainder;

        remainingAmount = amount - currentTotal;

        dispenseMap.put(currentDenomValue, numberOfCurrentDenom);

        if(currentTotal.equals(amount)) break;

        if(currentTotal < amount && d.getValue().equals(10.0)) {
          // we have a problem, amount cannot be dispensed\
          throw new ActionNotAllowedException(String.format(Constants.ATM_AMOUNT_MESSAGE, currentTotal));
        }

      }
    }
    return dispenseMap;

  }

  private int getDenominationsCountInAtm(List<AtmAllocation> atmAllocations, Denomination d) {
    int denomCount = 0;
    for(AtmAllocation al : atmAllocations) {
      if(al.getDenomination().equals(d)) {
        denomCount = al.getCount();
      }
    }
    return denomCount;
  }

  private boolean atmContainsDenomination(List<AtmAllocation> atmAllocations, Denomination denomination) {
    for(AtmAllocation al: atmAllocations) {
      if(al.getDenomination().equals(denomination) && al.getCount() > 0) {
        return true;
      }
    }
    return false;
  }

  public Denomination getHighestDenominationLessThanRequestedAmount(List<Denomination> denominations, Double requestedAmount) {

    Denomination highestDenomination = null;

    for(Denomination d : denominations) {
      if(requestedAmount > d.getValue() ) {
        highestDenomination = d;
        break;
      }
    }
    return highestDenomination;
  }


  private Double roundDownToNearestTen(Double amount) {
    // assume ATM can only dispense notes
    return amount % 10 == 0 ? amount : amount - (amount % 10);
  }


  private Double maximumDispensableAmountForAtm(List<AtmAllocation> atmAllocations) {

    Double maxAmount = 0.0;

    for(AtmAllocation al: atmAllocations) {
      if(al.getDenomination().getDenominationType().getDenominationTypeCode().equals("N")) {
        maxAmount += (al.getDenomination().getValue() * al.getCount());
      }
    }
    return maxAmount;
  }


  @Transactional
  public void updateDbWithdrawCash(Map<Double, Integer> dispenseMap, ClientAccount accountNumber,
      List<AtmAllocation> atmAllocations, Double amount) {

    List<AtmAllocation> updatedAllocations = new ArrayList<>();
    for(AtmAllocation aa : atmAllocations) {

      if(dispenseMap.containsKey(aa.getDenomination().getValue())) {
        int updatedDenominationCount = aa.getCount() - dispenseMap.get(aa.getDenomination().getValue());
        aa.setCount(updatedDenominationCount);
        updatedAllocations.add(aa);
      }
    }

    atmAllocationRepository.saveAll(updatedAllocations);

    accountNumber.setDisplayBalance(accountNumber.getDisplayBalance() - amount);
    clientAccountRepository.save(accountNumber);
    
  }
}
