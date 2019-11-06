package pizzarestaurantapp.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    private String name;
    private String firstName;
    private String city;
    private String street;
    private int postal;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Pizza>pizzaList;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<OrderItem>orderItemList;

    public Customer() {
    }



    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostal() {
        return postal;
    }

    public void setPostal(int postal) {
        this.postal = postal;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getPostal() == customer.getPostal() &&
                Objects.equals(customerId, customer.customerId) &&
                Objects.equals(getName(), customer.getName()) &&
                Objects.equals(getCity(), customer.getCity()) &&
                Objects.equals(getStreet(), customer.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, getName(), getCity(), getStreet(), getPostal());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Customer{");
        sb.append("customerId=").append(customerId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", postal=").append(postal);
        sb.append('}');
        return sb.toString();
    }
}
