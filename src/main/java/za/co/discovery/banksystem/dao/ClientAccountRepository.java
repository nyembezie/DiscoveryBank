package za.co.discovery.banksystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.discovery.banksystem.model.Client;
import za.co.discovery.banksystem.model.ClientAccount;

import java.util.List;

@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {

  @Query("SELECT ca.clientAccountNumber, ca.client, ca.accountType, "
      + " ca.clientAccountNumber, ca.currency, ca.displayBalance FROM ClientAccount ca\n"
      + " JOIN AccountType at ON \n"
      + " ca.accountType = at.accountTypeCode \n"
      + " WHERE at.transactional = true\n"
      + " AND ca.client = ?1"
      + " ORDER BY ca.displayBalance DESC")
  List<Object[]> findTransactionalClientAccounts(Client client);


  @Query("SELECT ca.clientAccountNumber, ccr.currencyCode, ca.displayBalance,\n"
      + " ccr.rate, (ca.displayBalance * ccr.rate) as ZAR_Amount \n"
      + " FROM ClientAccount ca join CurrencyConversionRate ccr on \n"
      + " ca.currency = ccr.currencyCode \n"
      + " where ca.client = ?1"
      + " order by ZAR_Amount")
  List<Object[]> getCurrencyAccountWithConvertedValues(Client client);

  ClientAccount findAllByClientAccountNumberAndClient_Id(String accountNumber, Integer clientId);

  @Query("SELECT at.transactional FROM ClientAccount ca join AccountType at on ca.accountType = at.accountTypeCode where ca.clientAccountNumber = ?1")
  Boolean isAccountTransactional(String accountNumber);

  @Query("SELECT at.description FROM ClientAccount ca join AccountType at on ca.accountType = at.accountTypeCode where ca.clientAccountNumber = ?1")
  String getAccountType(String accountNumber);


}
