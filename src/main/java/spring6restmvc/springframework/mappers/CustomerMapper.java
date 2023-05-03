package spring6restmvc.springframework.mappers;

import org.mapstruct.Mapper;
import spring6restmvc.springframework.entities.Customer;
import spring6restmvc.springframework.model.CustomerDTO;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO CustomerDto);
    CustomerDTO customerToCustomerDto(Customer costumer);
}
