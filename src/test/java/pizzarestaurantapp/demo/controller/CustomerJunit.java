package pizzarestaurantapp.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pizzarestaurantapp.demo.dao.CustomerRepository;
import pizzarestaurantapp.demo.entity.Customer;
import pizzarestaurantapp.demo.exception.ResourceNotFound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerJunit {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerRestController restController;

    List<Customer>customerList=new ArrayList<>();
    Customer customer=Mockito.mock(Customer.class);

    @Before
    public void setUp() throws Exception {

        restController=new CustomerRestController();
        MockitoAnnotations.initMocks(this);
        customer.setCustomerId(21L);
        customer.setFirstName("Mohamed");
        customer.setName("Sohil");
        customer.setCity("Darmstadt");
        customer.setStreet("Kurt-Schumacher 60");
        customer.setPostal(65897);
        customerList.add(customer);



    }

    @Test
    public void getAllcustomers() {


        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        assertThat(restController.getAllcustomers(),is(customerList));
    }

    @Test
    public void addNewCustomer() {
    }

    @Test
    public void getCustomerById() throws ResourceNotFound {

   Mockito.when(customerRepository.findById(21L)).thenReturn(Optional.of(customer));
   assertThat(restController.getCustomerById(21L),is(customer));

    }

    @Test
    public void getCustomersContaining() {

        Mockito.when(customerRepository.getCustomersByName("S")).thenReturn(customerList);
        assertThat(restController.getCustomersContainingKeyword("S"),is(customerList));
    }


}