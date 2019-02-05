package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Order")
@Table(name = "order")
@Data
public class Order {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shippedFrom")
    private Location shippedFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    private Customer customer;

    @JoinTable(name = "order_detail",
            joinColumns = @JoinColumn(name = "order"),
            inverseJoinColumns = @JoinColumn(name = "product")
    )
    private Set<Product> products = new HashSet<>();
    private String country;
    private String city;
    private String county;
    private String streetAddress;
}
