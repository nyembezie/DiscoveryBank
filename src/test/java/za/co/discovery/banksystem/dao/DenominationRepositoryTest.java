package za.co.discovery.banksystem.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.discovery.banksystem.model.Denomination;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DenominationRepositoryTest {

  @Autowired
  private DenominationRepository denominationRepository;

  @Test
  public void whenFindAllByDenominationType_DenominationTypeCode_thenReturnListOfDenominations() {

    // when
    List<Denomination> denominationsList = denominationRepository.findAllByDenominationType_DenominationTypeCodeOrderByValueDesc("N");

    // then
    assertThat(denominationsList.get(0)).isInstanceOf(Denomination.class);
    assertThat(denominationsList.get(0).getValue()).isEqualTo(200.0);
    assertThat(denominationsList.get(1).getValue()).isEqualTo(100.0);
    assertThat(denominationsList.get(2).getValue()).isEqualTo(50.0);
    assertThat(denominationsList.get(3).getValue()).isEqualTo(20.0);
    assertThat(denominationsList.get(4).getValue()).isEqualTo(10.0);
  }

}
