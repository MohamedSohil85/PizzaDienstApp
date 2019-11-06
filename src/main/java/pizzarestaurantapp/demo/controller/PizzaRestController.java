package pizzarestaurantapp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzarestaurantapp.demo.dao.CustomerRepository;
import pizzarestaurantapp.demo.dao.PizzaRepository;
import pizzarestaurantapp.demo.entity.Pizza;
import pizzarestaurantapp.demo.exception.ResourceNotFound;

import java.util.List;
import java.util.Optional;


@RestController
public class PizzaRestController {
    @Autowired
PizzaRepository pizzaRepository;
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(value = "/addPizzatoCustomer/{id}/pizza",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    public Pizza addPizzaToCustomer(@PathVariable("id")Long id, @RequestBody Pizza pizza) throws ResourceNotFound {
        return customerRepository.findById(id).map(customer->{
            pizza.setCustomer(customer);
            return pizzaRepository.save(pizza);
        }).orElseThrow(()->new ResourceNotFound("Customer not found !"));
    }

    // get all pizzas

@RequestMapping(value = "/getAllPizza",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public List<Pizza> getPizzas(){
        return (List<Pizza>)pizzaRepository.findAll();
}
    //get Bill of the Order
@RequestMapping(value = "/deletePizzaById/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePizzaById(@PathVariable("id")Long id) throws ResourceNotFound{
        return pizzaRepository.findById(id).map(pizza -> {
            pizzaRepository.delete(pizza);
            return new ResponseEntity(HttpStatus.OK);
        }).orElseThrow(()->new ResourceNotFound("Pizza not found !"));
}


@RequestMapping(value = "/addPizzaToCustomerByName/{name}/pizza",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)

public ResponseEntity addPizzatoCustomer(@RequestBody Pizza pizza,@PathVariable("name") String name){
        return customerRepository.getCustomerByName(name).map(customer -> {
            pizza.setCustomer(customer);
            return new ResponseEntity(pizzaRepository.save(pizza),HttpStatus.CREATED);
        }).orElse(new ResponseEntity(HttpStatus.NO_CONTENT));
}
@RequestMapping(value = "/findPizzaByName/{name}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findPizzaByName(@PathVariable("name")String name){

     Optional<Pizza>optionalPizza=pizzaRepository.findPizzaByPizzaName(name);
     if(! optionalPizza.isPresent()){
         return new ResponseEntity(HttpStatus.NO_CONTENT);
     }
     return new ResponseEntity(optionalPizza.get(),HttpStatus.FOUND);
}
@RequestMapping(value = "/findPizzaById/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Pizza getPizzaById(@PathVariable("id")Long id) throws ResourceNotFound{
        Optional<Pizza>optionalPizza=pizzaRepository.findById(id);
        if(!optionalPizza.isPresent()){
           throw new ResourceNotFound("Pizza with id "+id+" not found");
        }
           return optionalPizza.get();

}


}
