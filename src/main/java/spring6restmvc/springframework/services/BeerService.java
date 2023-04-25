package spring6restmvc.springframework.services;

import spring6restmvc.springframework.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();
    Beer getBeerById(UUID uuid);
    Beer saveBeer(Beer beer);
    void updateById(UUID beerId, Beer beer);
    void deleteById(UUID beerId);
    void updateBeetPatchById(UUID beerId, Beer beer);
}
