package pizzarestaurantapp.demo.controller;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pizzarestaurantapp.demo.dao.CustomerRepository;
import pizzarestaurantapp.demo.dao.PizzaRepository;
import pizzarestaurantapp.demo.entity.Customer;
import pizzarestaurantapp.demo.entity.Pizza;
import pizzarestaurantapp.demo.exception.ResourceNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class PizzaJUnit {

    private Pizza pizza= Mockito.mock(Pizza.class);
    private Customer customer=new Customer();
    @Mock
    PizzaRepository pizzaRepository;
    @Mock
    CustomerRepository customerRepository;
    @InjectMocks
    PizzaRestController pizzaRestController;

    //

    List<Pizza> pizzaList=new ArrayList<>();
    @BeforeEach
    public void setUp(){
        //
        MockitoAnnotations.initMocks(this);

        pizzaRestController=new PizzaRestController();

        //
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

    }

    @Test
    public void getPizzaById() throws ResourceNotFound {
     Mockito.when(pizzaRepository.findById(5L)).thenReturn(Optional.of(pizza));
     assertThat(pizzaRestController.getPizzaById(5L),is(pizza));
    }

    @Test
    public void getPizzas() {
        Mockito.when(pizzaRepository.findAll()).thenReturn(pizzaList);
        assertThat(pizzaRestController.getPizzas(),is(pizzaList));
    }

}