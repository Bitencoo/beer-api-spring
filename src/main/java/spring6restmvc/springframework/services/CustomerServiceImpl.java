package spring6restmvc.springframework.services;

import org.springframework.stereotype.Service;
import spring6restmvc.springframework.model.CustomerDTO;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();
        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .uuid(UUID.randomUUID())
                .customerName("Tulio")
                .version(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .uuid(UUID.randomUUID())
                .customerName("Jaas")
                .version(2)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        customerMap.put(customerDTO1.getUuid(), customerDTO1);
        customerMap.put(customerDTO2.getUuid(), customerDTO2);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.ofNullable(customerMap.get(uuid));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        CustomerDTO savedCustomerDTO = CustomerDTO.builder()
                .uuid(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .version(2)
                .customerName(customerDTO.getCustomerName())
                .build();

        customerMap.put(savedCustomerDTO.getUuid(), savedCustomerDTO);
        return savedCustomerDTO;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO oldCustomerDTO = customerMap.get(customerId);
        oldCustomerDTO.setCustomerName(customerDTO.getCustomerName());
        oldCustomerDTO.setVersion(customerDTO.getVersion());
        oldCustomerDTO.setUpdatedAt(LocalDateTime.now());

        customerMap.put(oldCustomerDTO.getUuid(), oldCustomerDTO);
        return null;
    }

    @Override
    public boolean deleteByCustomerId(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public void updateCustomerPatchById(UUID customerId, CustomerDTO customerDTO) {

    }
}
