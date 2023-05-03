package spring6restmvc.springframework.mappers;

import org.mapstruct.Mapper;
import spring6restmvc.springframework.entities.Beer;
import spring6restmvc.springframework.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}
