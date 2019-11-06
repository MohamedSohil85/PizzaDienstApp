package pizzarestaurantapp.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@Entity
public class Pizza implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pizzaId;
    private String pizzaName;
    private float price;
    private int quantity;
    @Temporal(value =TemporalType.TIMESTAMP )
    private Date orderDate;
    private float totalPrice;
    @ManyToOne
    Customer customer;
    @OneToMany(mappedBy = "pizza",cascade = CascadeType.ALL)
    List<OrderItem>orderItemList;



    public Pizza() {
    }

    public Long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public float getTotalPrice() {
        return price*quantity;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;
        Pizza pizza = (Pizza) o;
        return Float.compare(pizza.getPrice(), getPrice()) == 0 &&
                getQuantity() == pizza.getQuantity() &&
                Objects.equals(getPizzaId(), pizza.getPizzaId()) &&
                Objects.equals(getPizzaName(), pizza.getPizzaName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPizzaId(), getPizzaName(), getPrice(), getQuantity());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pizza{");
        sb.append("pizzaId=").append(pizzaId);
        sb.append(", pizzaName='").append(pizzaName).append('\'');
        sb.append(", price=").append(price);
        sb.append(", quantity=").append(quantity);
        sb.append(", date=").append(orderDate);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", customer=").append(customer);
        sb.append('}');
        return sb.toString();
    }
}
