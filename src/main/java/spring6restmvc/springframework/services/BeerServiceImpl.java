package spring6restmvc.springframework.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import spring6restmvc.springframework.model.BeerDTO;
import spring6restmvc.springframework.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();
        BeerDTO beerDTO1 = BeerDTO.builder()
                .uuid(UUID.randomUUID())
                .version(1)
                .beerName("Beer Galactica")
                .beerStyle(BeerStyle.ALE)
                .upc("1234")
                .price(new BigDecimal(12.99))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        BeerDTO beerDTO2 = BeerDTO.builder()
                .uuid(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine")
                .beerStyle(BeerStyle.ALE)
                .upc("5649")
                .price(new BigDecimal(9.99))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        BeerDTO beerDTO3 = BeerDTO.builder()
                .uuid(UUID.randomUUID())
                .version(1)
                .beerName("Autumn City")
                .beerStyle(BeerStyle.ALE)
                .upc("9485")
                .price(new BigDecimal(17.99))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beerDTO1.getUuid(), beerDTO1);
        beerMap.put(beerDTO2.getUuid(), beerDTO2);
        beerMap.put(beerDTO3.getUuid(), beerDTO3);
    }

    @Override
    public List<BeerDTO> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID uuid) {
        log.debug("Get beer by ID - in service ID: " + uuid.toString());
        return Optional.ofNullable(beerMap.get(uuid));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        BeerDTO savedBeerDTO = BeerDTO.builder()
                .uuid(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .version(beerDTO.getVersion())
                .upc(beerDTO.getUpc())
                .quantityOnHand(0)
                .build();

        beerMap.put(savedBeerDTO.getUuid(), savedBeerDTO);
        return savedBeerDTO;
    }

    @Override
    public Optional<BeerDTO> updateById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO oldBeerDTO = beerMap.get(beerId);
        oldBeerDTO.setBeerName(beerDTO.getBeerName());
        oldBeerDTO.setPrice(beerDTO.getPrice());
        oldBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
        oldBeerDTO.setUpc(beerDTO.getUpc());
        oldBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());

        beerMap.put(oldBeerDTO.getUuid(), oldBeerDTO);
        return Optional.of(oldBeerDTO);
    }

    @Override
    public boolean deleteById(UUID beerId) {
        beerMap.remove(beerId);
        return true;
    }

    @Override
    public void updateBeetPatchById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO oldBeerDTO = beerMap.get(beerId);
        if (StringUtils.hasText(beerDTO.getBeerName())){
            oldBeerDTO.setBeerName(beerDTO.getBeerName());
        }

        if (beerDTO.getBeerStyle() != null){
            oldBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
        }

        if (beerDTO.getPrice() != null){
            oldBeerDTO.setPrice(beerDTO.getPrice());
        }

        if (beerDTO.getQuantityOnHand() != null){
            oldBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
        }

        if (StringUtils.hasText(beerDTO.getUpc())){
            oldBeerDTO.setUpc(beerDTO.getUpc());
        }

        beerMap.put(oldBeerDTO.getUuid(), oldBeerDTO);
    }
}
