package spring6restmvc.springframework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import spring6restmvc.springframework.mappers.BeerMapper;
import spring6restmvc.springframework.model.BeerDTO;
import spring6restmvc.springframework.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID uuid) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(uuid)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
    }

    @Override
    public Optional<BeerDTO> updateById(UUID beerId, BeerDTO beerDTO) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            foundBeer.setBeerName(beerDTO.getBeerName());
            foundBeer.setPrice(beerDTO.getPrice());
            foundBeer.setUpc(beerDTO.getUpc());
            foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }

    @Override
    public boolean deleteById(UUID beerId) {
        if (!beerRepository.existsById(beerId)) {
            return false;
        }
        beerRepository.deleteById(beerId);
        return true;
    }

    @Override
    public void updateBeetPatchById(UUID beerId, BeerDTO beerDTO) {

    }
}
