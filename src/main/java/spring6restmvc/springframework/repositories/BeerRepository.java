package spring6restmvc.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring6restmvc.springframework.entities.Beer;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
