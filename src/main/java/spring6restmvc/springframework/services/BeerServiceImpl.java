package spring6restmvc.springframework.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring6restmvc.springframework.model.Beer;
import spring6restmvc.springframework.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerById(UUID uuid) {
        log.debug("Get beer by ID - in service ID: " + uuid.toString());
        return Beer.builder()
                .uuid(uuid)
                .version(1)
                .beerName("Beer Galactica")
                .beerStyle(BeerStyle.ALE)
                .upc("1234")
                .price(new BigDecimal(12.99))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
