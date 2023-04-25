package spring6restmvc.springframework.services;

import spring6restmvc.springframework.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(UUID uuid);
    Customer saveCustomer(Customer customer);
    void updateCustomerById(UUID customerId, Customer customer);
    void deleteByCustomerId(UUID customerId);
    void updateCustomerPatchById(UUID customerId, Customer customer);
}
