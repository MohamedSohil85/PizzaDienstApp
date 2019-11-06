package pizzarestaurantapp.demo.dao;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pizzarestaurantapp.demo.entity.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {

    @Query(value = "select * from CUSTOMER where CUSTOMER.NAME LIKE CONCAT(:keyword,'%')  ",nativeQuery = true)
    public List<Customer>getCustomersByName(@Param("keyword")String keyword);
// filter customer by address
    @Query(value = "select * from CUSTOMER where CUSTOMER.CITY LIKE CONCAT(:keyword,'%') ",nativeQuery = true)
    public List<Customer>getCustomerByCityContaining(@Param("keyword")String keyword);
   @Query(value = "select * from CUSTOMER where CUSTOMER.NAME =:name ",nativeQuery = true)
   public Optional<Customer>getCustomerByName(@Param("name") String name);
   @Query(value = "SELECT * FROM CUSTOMER WHERE CUSTOMER.NAME =:name and CUSTOMER.FIRST_NAME =:firstName ORDER BY CUSTOMER_ID DESC  ",nativeQuery = true)

   public Customer checkifCustomerExists(@Param("name")String name,@Param("firstName")String firstName );

}
