package za.co.discovery.banksystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.banksystem.model.Atm;

@Repository
public interface AtmRepository extends JpaRepository<Atm, Integer> {
}
