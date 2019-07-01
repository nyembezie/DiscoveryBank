package za.co.discovery.banksystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.banksystem.model.AtmAllocation;

import java.util.List;

@Repository
public interface AtmAllocationRepository extends JpaRepository<AtmAllocation, Integer> {

  List<AtmAllocation> findAllByAtmId(Integer atmId);

}
