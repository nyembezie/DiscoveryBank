package za.co.discovery.banksystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.banksystem.model.Denomination;

import java.util.List;

@Repository
public interface DenominationRepository extends JpaRepository<Denomination, Integer> {

  List<Denomination> findAllByDenominationType_DenominationTypeCodeOrderByValueDesc(String code);

}
