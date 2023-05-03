package spring6restmvc.springframework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import spring6restmvc.springframework.entities.Customer;
import spring6restmvc.springframework.mappers.CustomerMapper;
import spring6restmvc.springframework.model.CustomerDTO;
import spring6restmvc.springframework.repositories.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.ofNullable(customerMapper
                .customerToCustomerDto(customerRepository.findById(uuid).orElse(null)));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return customerMapper
                .customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setCustomerName(customerDTO.getCustomerName());
            foundCustomer.setUpdatedAt(LocalDateTime.now());
            atomicReference.set(Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer))));
            },
                () -> {
                    System.out.println("ENTROU");
            atomicReference.set(Optional.empty());
                });

        return atomicReference.get();
    }

    @Override
    public boolean deleteByCustomerId(UUID customerId) {
        if (customerRepository.findById(customerId).isEmpty()) {
            return false;
        }
        customerRepository.deleteById(customerId);
        return true;
    }

    @Override
    public void updateCustomerPatchById(UUID customerId, CustomerDTO customerDTO) {

    }
}
