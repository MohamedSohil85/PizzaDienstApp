package pizzarestaurantapp.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pizzarestaurantapp.demo.entity.Pizza;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza,Long> {

    //get all customers who eaten Pizza ...
    // get Bill of Pizza ...
    @Query(value = "select CUSTOMER.NAME ,CUSTOMER.POSTAL,CUSTOMER.STREET ,PIZZA.PIZZA_NAME ,PIZZA.PRICE ,PIZZA.QUANTITY from CUSTOMER , PIZZA WHERE CUSTOMER.CUSTOMER_ID=PIZZA.CUSTOMER_CUSTOMER_ID AND PIZZA.PIZZA_NAME = :pizzaName",nativeQuery = true)
    public List<Pizza> getCustomerAndAndPizzaList(@Param("pizzaName")String pizzaName);

    @Query(value = "select * from PIZZA where PIZZA.PIZZA_NAME =:name",nativeQuery = true)
    public Optional<Pizza> findPizzaByPizzaName(@Param("name")String name);

}
