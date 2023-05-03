package spring6restmvc.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring6restmvc.springframework.entities.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
