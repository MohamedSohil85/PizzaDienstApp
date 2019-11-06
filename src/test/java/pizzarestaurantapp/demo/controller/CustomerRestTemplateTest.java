package pizzarestaurantapp.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import pizzarestaurantapp.demo.dao.CustomerRepository;
import pizzarestaurantapp.demo.entity.Customer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private CustomerRepository customerRepository;
    private static final ObjectMapper om = new ObjectMapper();

    @Test
    public void testSaveCustomer() throws Exception {
        Customer customer=new Customer();
        customer.setCustomerId(21L);
        customer.setFirstName("Mohamed");
        customer.setName("Sohil");
        customer.setCity("Darmstadt");
        customer.setStreet("Kurt-Schumacher 60");
        customer.setPostal(65897);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        String expected=om.writeValueAsString(customer);
            ResponseEntity<String>responseEntity=restTemplate.postForEntity("/saveCustomer",customer,String.class);
            Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
            Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.getHeaders().getContentType());
            JSONAssert.assertEquals(expected, responseEntity.getBody(), false);

    }
    @Test
    public void testgetCustomerById() throws JSONException{
        Customer customer=new Customer();
        customer.setCustomerId(21L);
        customer.setFirstName("Mohamed");
        customer.setName("Sohil");
        customer.setCity("Darmstadt");
        customer.setStreet("Kurt-Schumacher 60");
        customer.setPostal(65897);
        Mockito.when(customerRepository.findById(21L)).thenReturn(Optional.of(customer));

        //
        String except="{\n" +

                "    \"name\": \"Sohil\",\n" +
                "    \"firstName\": \"Mohamed\",\n" +
                "    \"city\": \"Darmstadt\",\n" +
                "    \"street\": \"Kurt-Schumacher 60\",\n" +
                "    \"postal\": 65897\n" +
                "}\n";
        ResponseEntity<String>responseEntity=restTemplate.getForEntity("/getCustomerById/21",String.class);
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8,responseEntity.getHeaders().getContentType());
        JSONAssert.assertEquals(except,responseEntity.getBody(),false);

    }

    @Test
    public void testgetCustomerByKeyword() throws JSONException{
        Customer customer=new Customer();
        customer.setCustomerId(21L);
        customer.setFirstName("Mohamed");
        customer.setName("Sohil");
        customer.setCity("Darmstadt");
        customer.setStreet("Kurt-Schumacher 60");
        customer.setPostal(65897);
        List<Customer>customerList= Arrays.asList(customer);
        Mockito.when(customerRepository.getCustomersByName("S")).thenReturn(customerList);


        String payload="\n" +
                "[\n" +
                "{\n" +
                "\"name\":\"Sohil\",\n" +
                "\"firstName\":\"Mohamed\",\n" +
                "\"city\":\"Darmstadt\",\n" +
                "\"street\":\"Kurt-Schumacher 60\",\n" +
                "\"postal\":65897\n" +
                "}\n" +
                "]";

        ResponseEntity<String>responseEntity=restTemplate.getForEntity("/lookForCustomer/S",String.class);
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8,responseEntity.getHeaders().getContentType());
        JSONAssert.assertEquals(payload,responseEntity.getBody(),false);
    }

}
