package za.co.discovery.banksystem.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.discovery.banksystem.model.Client;
import za.co.discovery.banksystem.model.ClientAccount;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientAccountRepositoryTest {

  @Autowired
  private ClientAccountRepository clientAccountRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Test
  public void findAllByClientOrderByDisplayBalanceDesc_thenReturnClientAccount_orderedByDisplayAccount_descingOrder() {
    // given
    Client client = clientRepository.findById(1).get();

    // when
    List<Object[]> clientAccountsResults = clientAccountRepository.findTransactionalClientAccounts(client);

    // then
    assertThat(clientAccountsResults.size()).isEqualTo(3);
    assertThat((Double)clientAccountsResults.get(0)[2]).isGreaterThanOrEqualTo((Double)clientAccountsResults.get(1)[2]);
    assertThat((Double)clientAccountsResults.get(1)[2]).isGreaterThanOrEqualTo((Double)clientAccountsResults.get(2)[2]);
  }

  @Test
  public void getCurrencyAccountWithConvertedValues_thenReturnListOfObjectsArrays_showingCurrencyValues() {
    // given
    Client client = clientRepository.findById(2).get();

    // when
    List<Object[]> clientAccounts = clientAccountRepository.getCurrencyAccountWithConvertedValues(client);

    // then
    assertThat(clientAccounts.size()).isEqualTo(6);
    assertThat((Double)clientAccounts.get(0)[4]).isGreaterThanOrEqualTo((Double)clientAccounts.get(1)[4]);
    assertThat((Double)clientAccounts.get(1)[4]).isGreaterThanOrEqualTo((Double)clientAccounts.get(2)[4]);
    assertThat((Double)clientAccounts.get(2)[4]).isGreaterThanOrEqualTo((Double)clientAccounts.get(3)[4]);
  }

  @Test
  public void getByClientAccountNumberAndClient_thenReturnClientAccount() {

    // when
    ClientAccount clientAccount = clientAccountRepository.findAllByClientAccountNumberAndClient_Id("1073616681", 14);

    // then
    assertThat(clientAccount.getAccountType().getAccountTypeCode()).isEqualTo("SVGS");
    assertThat(clientAccount).isInstanceOf(ClientAccount.class);

  }

  @Test
  public void isAccountTransactional_thenReturnTransactionalAttributeOfAccountType() {

    // when
    Boolean isTransactional1 = clientAccountRepository.isAccountTransactional("1073616681");
    Boolean isTransactional2 = clientAccountRepository.isAccountTransactional("6037788392");

    // then
    assertThat(isTransactional1).isTrue();
    assertThat(isTransactional2).isFalse();
  }

  @Test
  public void whenGetAccountType_thenReturnAccountTypeDescription() {

    // when
    String accountType1 = clientAccountRepository.getAccountType("1073616681");
    String accountType2 = clientAccountRepository.getAccountType("6037788392");

    // then
    assertThat(accountType1).isNotNull();
    assertThat(accountType2).isNotNull();
    assertThat(accountType1).isEqualTo("Savings Account");
    assertThat(accountType2).isEqualTo("Personal Loan Account");

  }
}
