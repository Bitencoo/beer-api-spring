package spring6restmvc.springframework.services;

import spring6restmvc.springframework.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> listBeers();
    Optional<BeerDTO> getBeerById(UUID uuid);
    BeerDTO saveBeer(BeerDTO beerDTO);
    Optional<BeerDTO> updateById(UUID beerId, BeerDTO beerDTO);
    boolean deleteById(UUID beerId);
    void updateBeetPatchById(UUID beerId, BeerDTO beerDTO);
}
