package spring6restmvc.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring6restmvc.springframework.controllers.BeerController;

import java.util.UUID;

@SpringBootTest
class BeerControllerTest {

    @Autowired
    BeerController beerController;

    @Test
    void getBeerByid() {
        System.out.println(beerController.getBeerById(UUID.randomUUID()));
    }
}