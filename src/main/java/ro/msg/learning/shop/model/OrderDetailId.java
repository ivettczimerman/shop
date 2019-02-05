package ro.msg.learning.shop.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDetailId implements Serializable {
    @Column(name = "order")
    private int order;

    @Column(name = "product")
    private int product;

    public OrderDetailId() {
    }

    public OrderDetailId(int order, int product) {
        this.order = order;
        this.product = product;
    }

    public int getOrder() {
        return order;
    }

    public int getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailId)) return false;
        OrderDetailId that = (OrderDetailId) o;
        return Objects.equals(getOrder(), that.getOrder()) &&
                Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getProduct());
    }
}
