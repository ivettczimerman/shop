package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Location")
@Table(name = "location")
@Data
public class Location {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String country;
    private String city;
    private String county;
    private String streetAddress;

    @JoinTable(name = "stock",
            joinColumns = @JoinColumn(name = "location"),
            inverseJoinColumns = @JoinColumn(name = "product")
    )
    private Set<Product> products = new HashSet<>();
}
