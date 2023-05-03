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
import spring6restmvc.springframework.model.CustomerDTO;
import spring6restmvc.springframework.services.CustomerService;
import spring6restmvc.springframework.services.CustomerServiceImpl;

import java.util.*;

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
        List<CustomerDTO> customerDTOList = customerServiceImpl.getAllCustomers();
        given(customerService.getAllCustomers()).willReturn(customerDTOList);
        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].uuid", is(customerDTOList.get(0).getUuid().toString())));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.getAllCustomers().get(0);
        given(customerService.getCustomerById(testCustomerDTO.getUuid())).willReturn(Optional.of(testCustomerDTO));
        mockMvc.perform(get("/api/v1/customer/" + testCustomerDTO.getUuid())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid", is(testCustomerDTO.getUuid().toString())));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception{
        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);
        mockMvc.perform(get("/api/v1/customer/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCustomer() throws Exception {
        CustomerDTO testCustomerDTO = customerServiceImpl.getAllCustomers().get(0);
        testCustomerDTO.setVersion(null);
        testCustomerDTO.setUuid(null);

        given(customerService.saveCustomer(testCustomerDTO)).willReturn(customerServiceImpl.getAllCustomers().get(1));
        mockMvc.perform(post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
    }

    @Test
    void updateCustomerById() throws Exception{
        List<CustomerDTO> testCustomerDTOS = customerServiceImpl.getAllCustomers();

        mockMvc.perform(put("/api/v1/customer/" + testCustomerDTOS.get(0).getUuid())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomerDTOS.get(1))))
                .andExpect(status().isNoContent());
        verify(customerService).updateCustomerById(testCustomerDTOS.get(0).getUuid(), testCustomerDTOS.get(1));
    }

    @Test
    void deleteCustomerById() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.getAllCustomers().get(0);
        mockMvc.perform(delete("/api/v1/customer/" + customerDTO.getUuid())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(customerService).deleteByCustomerId(any(UUID.class));
    }

    @Test
    void updateCustomerPatchById() throws Exception{
        CustomerDTO customerDTO = customerServiceImpl.getAllCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");

        mockMvc.perform(patch("/api/v1/customer/" + customerDTO.getUuid())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerPatchById(any(UUID.class), any(CustomerDTO.class));
    }
}