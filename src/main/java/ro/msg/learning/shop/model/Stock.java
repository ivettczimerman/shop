package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"product", "location"})
public class Stock {
    @JsonUnwrapped
    @EmbeddedId
    private StockId id;

    @JsonUnwrapped
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @MapsId("product")
    private Product product;

    @JsonBackReference
    @JsonUnwrapped
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("location")
    private Location location;

    @Column(name = "quantity")
    private int quantity;

    public Stock() {
    }

    public Stock(Product product, Location location) {
        this.product = product;
        this.location = location;
        this.id = new StockId(product.getId(), location.getId());
    }
}
