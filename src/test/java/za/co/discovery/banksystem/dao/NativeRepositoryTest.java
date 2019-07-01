package za.co.discovery.banksystem.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.discovery.banksystem.dto.FinancialPositionDto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NativeRepositoryTest {

  @Autowired
  private NativeRepository nativeRepository;

  @Test
  public void whenCalculateClientFinancialPosition_thenResultsIsNotNull() {
    // when
    List<FinancialPositionDto> financialPositionDtos = nativeRepository.calculateClientFinancialPosition();

    // then
    assertThat(financialPositionDtos.get(0).getFullName()).isNotNull();
  }
}
