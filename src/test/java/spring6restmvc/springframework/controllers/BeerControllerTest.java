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
import spring6restmvc.springframework.model.BeerDTO;
import spring6restmvc.springframework.services.BeerService;
import spring6restmvc.springframework.services.BeerServiceImpl;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerByid() throws Exception {
        BeerDTO testBeerDTO = beerServiceImpl.listBeers().get(0);
        given(beerService.getBeerById(testBeerDTO.getUuid())).willReturn(Optional.of(testBeerDTO));
        mockMvc.perform(get("/api/v1/beer/" + testBeerDTO.getUuid())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid", is(testBeerDTO.getUuid().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeerDTO.getBeerName())));
    }

    @Test
    void testGetBeerByIdNotFound() throws Exception{
        given(beerService.getBeerById(any(UUID.class))).willThrow(NotFoundException.class);
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListBeers() throws Exception {
        List<BeerDTO> testBeerDTOS = beerServiceImpl.listBeers();
        given(beerService.listBeers()).willReturn(testBeerDTOS);
        mockMvc.perform(get("/api/v1/beer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].uuid", is(testBeerDTOS.get(0).getUuid().toString())));
    }

    @Test
    void testCreateBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);
        beerDTO.setVersion(null);
        beerDTO.setUuid(null);

        given(beerService.saveBeer(beerDTO)).willReturn(beerServiceImpl.listBeers().get(2));
        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);
        mockMvc.perform(put("/api/v1/beer/" + beerDTO.getUuid())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isAccepted());
        verify(beerService).updateById(beerDTO.getUuid(), beerDTO);
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);
        mockMvc.perform(delete("/api/v1/beer/" + beerDTO.getUuid())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(beerService).deleteById(any(UUID.class));
    }

    @Test
    void testPatchBeer() throws Exception{
        BeerDTO beerDTO = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch("/api/v1/beer/" + beerDTO.getUuid())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeetPatchById(any(UUID.class), any(BeerDTO.class));
    }


}