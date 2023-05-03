package spring6restmvc.springframework.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring6restmvc.springframework.entities.Beer;
import spring6restmvc.springframework.entities.Customer;
import spring6restmvc.springframework.model.BeerStyle;
import spring6restmvc.springframework.repositories.BeerRepository;
import spring6restmvc.springframework.repositories.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Beer Galactica")
                    .beerStyle(BeerStyle.ALE)
                    .upc("1234")
                    .price(new BigDecimal(12.99))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Sunshine")
                    .beerStyle(BeerStyle.ALE)
                    .upc("5649")
                    .price(new BigDecimal(9.99))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Autumn City")
                    .beerStyle(BeerStyle.ALE)
                    .upc("9485")
                    .price(new BigDecimal(17.99))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("Tulio")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Jaas")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            customerRepository.save(customer1);
            customerRepository.save(customer2);
        }
    }
}
