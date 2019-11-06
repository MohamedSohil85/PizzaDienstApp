package pizzarestaurantapp.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pizzarestaurantapp.demo.entity.OrderItem;

@Repository
public interface OrderItemsRepository extends CrudRepository<OrderItem,Long> {

}
