package ro.msg.learning.shop.model;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(of = {"product", "location"})
public class StockId implements Serializable {

    @Column(name = "product")
    private int product;

    @Column(name = "location")
    private int location;

    public StockId() {
    }

    public StockId(int product, int location) {
        this.product = product;
        this.location = location;
    }

    public int getProduct() {
        return product;
    }

    public int getLocation() {
        return location;
    }
}
