package spring6restmvc.springframework.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring6restmvc.springframework.exceptions.NotFoundException;
import spring6restmvc.springframework.model.CustomerDTO;
import spring6restmvc.springframework.services.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Optional<CustomerDTO> getCustomerById(@PathVariable("customerId") UUID customerId) {
        Optional<CustomerDTO> customerDTO = customerService.getCustomerById(customerId);

        System.out.println(customerDTO.toString());
        if (customerDTO.isEmpty()) {
            throw new NotFoundException();
        }
        return customerDTO;
    }

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO savedCustomerDTO = customerService.saveCustomer(customerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "/api/v1/customer/" + savedCustomerDTO.getUuid().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public  ResponseEntity updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customerDTO){
        if (customerService.updateCustomerById(customerId, customerDTO).isEmpty()) {
            throw new NotFoundException();
        };
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId) {
        if (!customerService.deleteByCustomerId(customerId)){
            throw new NotFoundException();
        }
        customerService.deleteByCustomerId(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customerDTO){
        customerService.updateCustomerPatchById(customerId, customerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
