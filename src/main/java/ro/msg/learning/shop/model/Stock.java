package ro.msg.learning.shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"product", "location"})
public class Stock {
    @EmbeddedId
    private StockId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product")
    private Product product;

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
