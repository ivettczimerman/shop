package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Location")
@Table(name = "location")
@Data
public class Location {

    @Id
    @SequenceGenerator(name = "location_generator", sequenceName = "location_sequence", allocationSize = 1)
    @GeneratedValue(generator = "location_generator")
    private int id;
    private String name;
    private String country;
    private String city;
    private String county;
    private String streetAddress;

    @OneToMany(mappedBy = "shippedFrom")
    private List<Order> orders;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Stock> products = new ArrayList<>();

    public void addProduct(Product product) {
        Stock stock = new Stock(product, this);
        products.add(stock);
        product.getLocations().add(stock);
    }

    public void removeProduct(Product product) {
        for (Iterator<Stock> iterator = products.iterator(); iterator.hasNext(); ) {
            Stock stock = iterator.next();

            if (stock.getLocation().equals(this) && stock.getProduct().equals(product)) {
                iterator.remove();
                stock.getProduct().getLocations().remove(stock);
                stock.setProduct(null);
                stock.setLocation(null);
            }
        }
    }
}
