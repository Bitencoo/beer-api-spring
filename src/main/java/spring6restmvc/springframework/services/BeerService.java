package spring6restmvc.springframework.services;

import spring6restmvc.springframework.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID uuid);
}
