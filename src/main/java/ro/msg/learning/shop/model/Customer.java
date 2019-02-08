package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Customer")
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_sequence", allocationSize = 1)
    @GeneratedValue(generator = "customer_generator")
    private int id;
    private String firstName;
    private String lastName;
    private String username;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
