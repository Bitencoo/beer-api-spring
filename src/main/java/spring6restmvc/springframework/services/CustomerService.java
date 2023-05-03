package spring6restmvc.springframework.services;

import spring6restmvc.springframework.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    Optional<CustomerDTO> getCustomerById(UUID uuid);
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO);
    boolean deleteByCustomerId(UUID customerId);
    void updateCustomerPatchById(UUID customerId, CustomerDTO customerDTO);
}
