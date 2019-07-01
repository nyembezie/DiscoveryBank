package za.co.discovery.banksystem.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.discovery.banksystem.model.AtmAllocation;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AtmLocationRepositoryTest {

  @Autowired
  private AtmAllocationRepository atmLocationRepository;

  @Test
  public void findByAtmId_theReturnAtmLocation() {

    // when
    List<AtmAllocation> atmLocations1 = atmLocationRepository.findAllByAtmId(1);
    List<AtmAllocation> atmLocations3 = atmLocationRepository.findAllByAtmId(3);

    // then
    assertThat(atmLocations1.size()).isEqualTo(5);
    assertThat(atmLocations3.size()).isEqualTo(5);
  }

}
