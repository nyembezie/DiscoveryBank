package za.co.discovery.banksystem.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.discovery.banksystem.dao.AtmAllocationRepository;
import za.co.discovery.banksystem.dao.ClientAccountRepository;
import za.co.discovery.banksystem.dao.DenominationRepository;
import za.co.discovery.banksystem.exception.ActionNotAllowedException;
import za.co.discovery.banksystem.model.AtmAllocation;
import za.co.discovery.banksystem.model.ClientAccount;
import za.co.discovery.banksystem.model.Denomination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientAccountServiceTest {

  @Autowired
  private ClientAccountService clientAccountService;

  @Autowired
  private DenominationRepository denominationRepository;

  @Autowired
  private AtmAllocationRepository atmAllocationRepository;

  @Autowired
  private ClientAccountRepository clientAccountRepository;

  @Test
  public void givenListOfNoteDenominationsAndRequestedAmount_whenGetHighestDenominationLessThanRequestedAmount_thenHighestDenominationIs100() {
    // given
    List<Denomination> denominations = denominationRepository.findAllByDenominationType_DenominationTypeCodeOrderByValueDesc("N");
    Double amount = 180.0;

    // when
    Denomination highestDenomination = clientAccountService.getHighestDenominationLessThanRequestedAmount(denominations, amount);

    // then
    assertThat(highestDenomination.getValue()).isEqualTo(100.0);

  }

  @Test
  public void givenAtmLocationsAndRequestedAmount_whenCalculateNotesToDispense_returnMapOfDenominationValuesAndCountsOfEach() throws Exception {

    // given
    List<AtmAllocation> atmAllocations = atmAllocationRepository.findAllByAtmId(2);
    Double requestedAmount1 = 250.0;
    Double requestedAmount2 = 20.0;
    Double requestedAmount3 = 2760.0;

    // when
    Map<Double, Integer> dispenseMap1 = clientAccountService.calculateNotesToDispense(atmAllocations, requestedAmount1);
    Map<Double, Integer> dispenseMap2 = clientAccountService.calculateNotesToDispense(atmAllocations, requestedAmount2);
    Map<Double, Integer> dispenseMap3 = clientAccountService.calculateNotesToDispense(atmAllocations, requestedAmount3);

    // then
    assertThat(dispenseMap1.get(200.0)).isEqualTo(1);
    assertThat(dispenseMap1.get(100.0)).isEqualTo(null);
    assertThat(dispenseMap1.get(50.0)).isEqualTo(1);
    assertThat(dispenseMap1.get(20.0)).isEqualTo(null);
    assertThat(dispenseMap1.get(10.0)).isEqualTo(null);

    assertThat(dispenseMap2.get(200.0)).isEqualTo(null);
    assertThat(dispenseMap2.get(100.0)).isEqualTo(null);
    assertThat(dispenseMap2.get(50.0)).isEqualTo(null);
    assertThat(dispenseMap2.get(20.0)).isEqualTo(null);
    assertThat(dispenseMap2.get(10.0)).isEqualTo(2);

    assertThat(dispenseMap3.get(200.0)).isEqualTo(13);
    assertThat(dispenseMap3.get(100.0)).isEqualTo(1);
    assertThat(dispenseMap3.get(50.0)).isEqualTo(1);
    assertThat(dispenseMap3.get(20.0)).isEqualTo(null);
    assertThat(dispenseMap3.get(10.0)).isEqualTo(1);

  }

  @Test(expected = ActionNotAllowedException.class)
  public void givenAtmLocationsAndRequestedAmountThatIsMoreThanAtmCanDispense_whenCalculateNotesToDispense_throwActionNotAllowedException() throws Exception {

    // given
    List<AtmAllocation> atmAllocations = atmAllocationRepository.findAllByAtmId(1);
    Double requestedAmount = 1890.0;

    // when
    Map<Double, Integer> dispenseMap = clientAccountService.calculateNotesToDispense(atmAllocations, requestedAmount);

  }

  @Test
  public void whenUpdateDbWithdrawCash_thenUpdateAccountBalanceAndAtmAllocations() {
    // given
    List<AtmAllocation> originalAtmAllocations = atmAllocationRepository.findAllByAtmId(2);
    ClientAccount clientAccount = clientAccountRepository.findById("1053664521").get();
    Double amount = 250.0;
    Map<Double, Integer> dispenseMap = new HashMap<>();
    dispenseMap.put(200.0, 1);
    dispenseMap.put(50.0, 1);

    Double originalBalance = clientAccount.getDisplayBalance();
    int original200sCount = 0;
    int original50sCount = 0;
    for (AtmAllocation aa : originalAtmAllocations) {
      if(aa.getDenomination().getValue() == 200.0) {
        original200sCount = aa.getCount();
      }
      if(aa.getDenomination().getValue() == 50.0) {
        original50sCount = aa.getCount();
      }
    }


    // when
    clientAccountService.updateDbWithdrawCash(dispenseMap, clientAccount, originalAtmAllocations, amount);
    Double newBalance = clientAccountRepository.findById("1053664521").get().getDisplayBalance();
    List<AtmAllocation> updatedAtmAllocations = atmAllocationRepository.findAllByAtmId(2);

    int new200sCount = 0;
    int new50sCount = 0;
    for (AtmAllocation aa : originalAtmAllocations) {
      if(aa.getDenomination().getValue() == 200.0) {
        new200sCount = aa.getCount();
      }
      if(aa.getDenomination().getValue() == 50.0) {
        new50sCount = aa.getCount();
      }
    }

    // then
    assertThat(originalBalance - newBalance).isEqualTo(amount);
    assertThat(original200sCount - new200sCount).isEqualTo(1);
    assertThat(original50sCount - new50sCount).isEqualTo(1);

  }
}
