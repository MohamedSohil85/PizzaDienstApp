package pizzarestaurantapp.demo.controller;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import pizzarestaurantapp.demo.dao.CustomerRepository;
import pizzarestaurantapp.demo.dao.PizzaRepository;
import pizzarestaurantapp.demo.entity.Customer;
import pizzarestaurantapp.demo.entity.Pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
class PizzaRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private PizzaRepository repository;

    Pizza pizza=new Pizza();
    Customer customer=new Customer();

    List<Pizza> pizzaList=new ArrayList<>();

    @BeforeEach
    void setUp() {
        pizza.setPizzaId(5L);
        pizza.setPizzaName("Thunfisch");
        pizza.setQuantity(2);
        pizza.setPrice(4.5f);
        pizza.setTotalPrice(9);
        //
        customer.setCustomerId(21L);
        customer.setFirstName("Mani");
        customer.setName("Sohormann");
        customer.setCity("Darmstadt");
        customer.setStreet("Kurt-Schumacher 60");
        customer.setPostal(65487);
        pizza.setCustomer(customer);

        pizzaList.add(pizza);
        MockitoAnnotations.initMocks(this);
        //

    }

    @Test
    public void getPizzaById(){

        //
        Mockito.when(repository.findById(5L)).thenReturn(Optional.of(pizza));
        //
        HttpHeaders httpHeaders=new HttpHeaders();
        ResponseEntity<String>responseEntity =restTemplate.getForEntity("/findPizzaById/{id}",String.class,5);
        assertThat(HttpStatus.OK,is(responseEntity.getStatusCode()));
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8,responseEntity.getHeaders().getContentType());

    }

    @Test
    void getPizzas() {

        Mockito.when(repository.findAll()).thenReturn(pizzaList);

        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<String>responseEntity=restTemplate.exchange("/getAllPizza",HttpMethod.GET,new HttpEntity<>(null,httpHeaders),String.class);
        assertThat(HttpStatus.OK,is(responseEntity.getStatusCode()));
        assertThat(MediaType.APPLICATION_JSON_UTF8,is(responseEntity.getHeaders().getContentType()));
    }

    @Test
    void getPizzaByName(){

        Mockito.when(repository.findPizzaByPizzaName("Thunfisch")).thenReturn(Optional.of(pizza));
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<String>responseEntity=restTemplate.exchange("/findPizzaByName/{name}",HttpMethod.GET,new HttpEntity<>(null,httpHeaders),String.class,"Thunfisch");
        assertThat(HttpStatus.FOUND,is(responseEntity.getStatusCode()));
        assertThat(MediaType.APPLICATION_JSON_UTF8,is(responseEntity.getHeaders().getContentType()));


    }
    @Test
    void postPizza(){
        Mockito.when(repository.save(pizza)).thenReturn(pizza);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/addPizzaToCustomerByName/{name}/pizza",HttpMethod.POST,new HttpEntity<>(pizza,httpHeaders),String.class,"Schormann");
        assertThat(HttpStatus.CREATED,is(responseEntity.getStatusCode()));
        assertThat(MediaType.APPLICATION_JSON_UTF8,is(responseEntity.getHeaders().getContentType()));
    }

}