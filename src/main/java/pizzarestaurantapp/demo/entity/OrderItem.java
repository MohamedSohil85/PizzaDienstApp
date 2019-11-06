package pizzarestaurantapp.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date dateOrder;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Pizza pizza;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrderItem{");
        sb.append("id=").append(id);
        sb.append(", dateOrder=").append(dateOrder);
        sb.append(", customer=").append(customer);
        sb.append(", pizza=").append(pizza);
        sb.append('}');
        return sb.toString();
    }
}
