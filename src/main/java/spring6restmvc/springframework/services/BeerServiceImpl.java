package spring6restmvc.springframework.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import spring6restmvc.springframework.model.Beer;
import spring6restmvc.springframework.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();
        Beer beer1 = Beer.builder()
                .uuid(UUID.randomUUID())
                .version(1)
                .beerName("Beer Galactica")
                .beerStyle(BeerStyle.ALE)
                .upc("1234")
                .price(new BigDecimal(12.99))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .uuid(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine")
                .beerStyle(BeerStyle.ALE)
                .upc("5649")
                .price(new BigDecimal(9.99))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .uuid(UUID.randomUUID())
                .version(1)
                .beerName("Autumn City")
                .beerStyle(BeerStyle.ALE)
                .upc("9485")
                .price(new BigDecimal(17.99))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getUuid(), beer1);
        beerMap.put(beer2.getUuid(), beer2);
        beerMap.put(beer3.getUuid(), beer3);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID uuid) {
        log.debug("Get beer by ID - in service ID: " + uuid.toString());
        return beerMap.get(uuid);
    }

    @Override
    public Beer saveBeer(Beer beer) {
        Beer savedBeer = Beer.builder()
                .uuid(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .version(beer.getVersion())
                .upc(beer.getUpc())
                .quantityOnHand(0)
                .build();

        beerMap.put(savedBeer.getUuid(), savedBeer);
        return savedBeer;
    }

    @Override
    public void updateById(UUID beerId, Beer beer) {
        Beer oldBeer = beerMap.get(beerId);
        oldBeer.setBeerName(beer.getBeerName());
        oldBeer.setPrice(beer.getPrice());
        oldBeer.setBeerStyle(beer.getBeerStyle());
        oldBeer.setUpc(beer.getUpc());
        oldBeer.setQuantityOnHand(beer.getQuantityOnHand());

        beerMap.put(oldBeer.getUuid(), oldBeer);
    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void updateBeetPatchById(UUID beerId, Beer beer) {
        Beer oldBeer = beerMap.get(beerId);
        if (StringUtils.hasText(beer.getBeerName())){
            oldBeer.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null){
            oldBeer.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null){
            oldBeer.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null){
            oldBeer.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if (StringUtils.hasText(beer.getUpc())){
            oldBeer.setUpc(beer.getUpc());
        }

        beerMap.put(oldBeer.getUuid(), oldBeer);
    }
}
