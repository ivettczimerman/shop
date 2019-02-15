package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Stock")
@Table(name = "stock")
@Data
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

    public int getProductId() {
        return product.getId();
    }
}
