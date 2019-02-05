package ro.msg.learning.shop.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockId)) return false;
        StockId that = (StockId) o;
        return Objects.equals(getLocation(), that.getLocation()) &&
                Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocation(), getProduct());
    }
}
