package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Stock")
@Table(name = "stock")
@Data
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

    public Stock(){}

    public Stock(Product product, Location location) {
        this.product = product;
        this.location = location;
        this.id = new StockId(product.getId(), location.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Stock that = (Stock) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, location);
    }
}
