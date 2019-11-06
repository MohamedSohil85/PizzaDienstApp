package pizzarestaurantapp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pizzarestaurantapp.demo.dao.CustomerRepository;
import pizzarestaurantapp.demo.entity.Customer;
import pizzarestaurantapp.demo.exception.ResourceNotFound;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class CustomerRestController {
 @Autowired
private CustomerRepository customerRepository;

 // get all Customers
 @RequestMapping(value = "/customers",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
public List<Customer> getAllcustomers(){
    return (List<Customer>)customerRepository.findAll();
}
 // save new Customer
    @RequestMapping(value = "/saveCustomer",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
public ResponseEntity addNewCustomer(@RequestBody Customer customer){
   List<Customer>customerList=(List<Customer>) customerRepository.findAll();
  for(Customer temp:customerList){
       if(temp.getName().equalsIgnoreCase(customer.getName()))
         if(temp.getFirstName().equalsIgnoreCase(customer.getFirstName())){
           return new ResponseEntity(HttpStatus.CONFLICT);
       }
   }


   return new ResponseEntity(customerRepository.save(customer),HttpStatus.CREATED);

}
 // get Customer by Id
@RequestMapping(value = "/getCustomerById/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
 // get Customer by his Name
public Customer getCustomerById(@PathVariable("id") Long id) throws ResourceNotFound {
    Optional<Customer>optionalCustomer=customerRepository.findById(id);
    if(!optionalCustomer.isPresent()){
       throw new ResourceNotFound("Customer not found");
    }else{
        return optionalCustomer.get();
    }

}
 // filter  customers by there names
    public List<Customer>getCustomersContaining(String name){
     List<Customer>customerList=getAllcustomers().stream().filter(c->c.getName().startsWith(name)).collect(Collectors.toList());
     return customerList;
    }
//filter Customer By names
    @RequestMapping(value = "/lookForCustomer/{keyword}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer>getCustomersContainingKeyword(@PathVariable("keyword") String name){
     List<Customer>customerList=getAllcustomers();
   customerList.stream().forEach(customer -> {
       if( !customer.getName().startsWith(name,0)){
          new ResponseEntity(HttpStatus.NOT_FOUND);
       }
   });
        return customerRepository.getCustomersByName(name);
    }
    @RequestMapping(value ="/deleteCustomerById/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity deleteCustomer(@PathVariable("id")Long id) throws ResourceNotFound{
     return customerRepository.findById(id).map(customer -> {
         customerRepository.delete(customer);
         return new ResponseEntity("Customer with id "+id+" has deleted",HttpStatus.OK);
     }).orElseThrow(()->new ResourceNotFound("Customer not found !"));
    }
@RequestMapping(value = "/deleteCustomers",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
public void deleteAllCustomers(){

    customerRepository.deleteAll();
}

}
