package spring6restmvc.springframework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spring6restmvc.springframework.exceptions.NotFoundException;
import spring6restmvc.springframework.model.Customer;
import spring6restmvc.springframework.services.CustomerService;
import spring6restmvc.springframework.services.CustomerServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void getAllCustomers() throws Exception {
        List<Customer> customerList = customerServiceImpl.getAllCustomers();
        given(customerService.getAllCustomers()).willReturn(customerList);
        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].uuid", is(customerList.get(0).getUuid().toString())));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().get(0);
        given(customerService.getCustomerById(testCustomer.getUuid())).willReturn(testCustomer);
        mockMvc.perform(get("/api/v1/customer/" + testCustomer.getUuid())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid", is(testCustomer.getUuid().toString())));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception{
        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);
        mockMvc.perform(get("/api/v1/customer/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.getAllCustomers().get(0);
        testCustomer.setVersion(null);
        testCustomer.setUuid(null);

        given(customerService.saveCustomer(testCustomer)).willReturn(customerServiceImpl.getAllCustomers().get(1));
        mockMvc.perform(post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
    }

    @Test
    void updateCustomerById() throws Exception{
        List<Customer> testCustomers = customerServiceImpl.getAllCustomers();

        mockMvc.perform(put("/api/v1/customer/" + testCustomers.get(0).getUuid())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomers.get(1))))
                .andExpect(status().isNoContent());
        verify(customerService).updateCustomerById(testCustomers.get(0).getUuid(), testCustomers.get(1));
    }

    @Test
    void deleteCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().get(0);
        mockMvc.perform(delete("/api/v1/customer/" + customer.getUuid())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(customerService).deleteByCustomerId(any(UUID.class));
    }

    @Test
    void updateCustomerPatchById() throws Exception{
        Customer customer = customerServiceImpl.getAllCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");

        mockMvc.perform(patch("/api/v1/customer/" + customer.getUuid())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerPatchById(any(UUID.class), any(Customer.class));
    }
}