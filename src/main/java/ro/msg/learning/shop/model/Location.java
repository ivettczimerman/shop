package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Data
@ToString(exclude = {"stocks"})
public class Location {

    @Id
    @SequenceGenerator(name = "location_generator", sequenceName = "location_sequence", allocationSize = 1)
    @GeneratedValue(generator = "location_generator")
    private int id;
    private String name;

    @Embedded
    private Address address;

    @JsonUnwrapped
    @OneToMany(mappedBy = "shippedFrom")
    @JsonManagedReference
    private List<Order> orders;

    @JsonManagedReference
    @JsonUnwrapped
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Stock> stocks = new ArrayList<>();

    public void addProduct(Product product) {
        Stock stock = new Stock(product, this);
        stocks.add(stock);
        product.getStocks().add(stock);
    }

    public void removeProduct(Product product) {
        for (Iterator<Stock> iterator = stocks.iterator(); iterator.hasNext(); ) {
            Stock stock = iterator.next();

            if (stock.getLocation().equals(this) && stock.getProduct().equals(product)) {
                iterator.remove();
                stock.getProduct().getStocks().remove(stock);
                stock.setProduct(null);
                stock.setLocation(null);
            }
        }
    }
}
