package za.co.discovery.banksystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.banksystem.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
