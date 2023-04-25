package spring6restmvc.springframework.services;

import org.springframework.stereotype.Service;
import spring6restmvc.springframework.model.Customer;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();
        Customer customer1 = Customer.builder()
                .uuid(UUID.randomUUID())
                .customerName("Tulio")
                .version(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .uuid(UUID.randomUUID())
                .customerName("Jaas")
                .version(2)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getUuid(), customer1);
        customerMap.put(customer2.getUuid(), customer2);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID uuid) {
        return customerMap.get(uuid);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .uuid(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .version(2)
                .customerName(customer.getCustomerName())
                .build();

        customerMap.put(savedCustomer.getUuid(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {
        Customer oldCustomer = customerMap.get(customerId);
        oldCustomer.setCustomerName(customer.getCustomerName());
        oldCustomer.setVersion(customer.getVersion());
        oldCustomer.setUpdatedAt(LocalDateTime.now());

        customerMap.put(oldCustomer.getUuid(), oldCustomer);
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void updateCustomerPatchById(UUID customerId, Customer customer) {

    }
}
