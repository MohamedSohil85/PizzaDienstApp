package pizzarestaurantapp.demo.controller;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import org.hamcrest.MatcherAssert.*;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pizzarestaurantapp.demo.dao.CustomerRepository;
import pizzarestaurantapp.demo.entity.Customer;

import javax.net.ssl.SSLEngineResult;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerRestControllerTest {
@MockBean
    CustomerRepository customerRepository;
    @Autowired
    private MockMvc mockMvc;



    @Test
    public void getAllcustomers() throws Exception{
        String payload="{\n" +
                "    \"customerId\": 2,\n" +
                "    \"name\": \"Sohil\",\n" +
                "    \"firstName\": \"Mohamed\",\n" +
                "    \"city\": \"Darmstadt\",\n" +
                "    \"street\": \"Kurt-Schumacher 60\",\n" +
                "    \"postal\": 65897\n" +
                "}\n";

        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
              .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(payload))
              .andExpect(status().isOk())
              .andExpect(content().contentType("application/json;charset=UTF-8")).andReturn();


    }


    @Test
    public void getCustomerById() throws Exception {
        Customer customer=new Customer();
        customer.setCustomerId(21L);
        customer.setFirstName("Mohamed");
        customer.setName("Sohil");
        customer.setCity("Darmstadt");
        customer.setStreet("Kurt-Schumacher 60");
        customer.setPostal(65897);
        Mockito.when(customerRepository.findById(21L)).thenReturn(Optional.of(customer));

        String payload="{\n" +
                "    \"name\": \"Sohil\",\n" +
                "    \"firstName\": \"Mohamed\",\n" +
                "    \"city\": \"Darmstadt\",\n" +
                "    \"street\": \"Kurt-Schumacher 60\",\n" +
                "    \"postal\": 65897\n" +
                "}\n";
        mockMvc.perform(MockMvcRequestBuilders.get("/getCustomerById/"+21)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(payload))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
    }



    @Test
    public void getCustomersContainingKeyword() throws Exception {
        Customer customer=new Customer();
        customer.setCustomerId(21L);
        customer.setFirstName("Mohamed");
        customer.setName("Sohil");
        customer.setCity("Darmstadt");
        customer.setStreet("Kurt-Schumacher 60");
        customer.setPostal(65897);
        List<Customer>customerList=Arrays.asList(customer);

        Mockito.when(customerRepository.getCustomersByName("M")).thenReturn(customerList);



        String payload="\n" +
                "[\n" +
                "{\n" +
                "\"name\":\"Sohil\",\n" +
                "\"firstName\":\"Mohamed\",\n" +
                "\"city\":\"Darmstadt\",\n" +
                "\"street\":\"Kurt-Schumacher 60\",\n" +
                "\"postal\":65897\n" +
                "}\n" +
                "]";        mockMvc.perform(MockMvcRequestBuilders.get("/lookForCustomer/"+"M")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(payload))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();


    }
}