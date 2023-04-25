package spring6restmvc.springframework;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import spring6restmvc.springframework.model.Beer;
import spring6restmvc.springframework.services.BeerService;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID uuid){
        log.debug("Get beer by Id - in controller");
        return beerService.getBeerById(uuid);
    }
}
