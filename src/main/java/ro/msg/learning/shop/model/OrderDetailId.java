package ro.msg.learning.shop.model;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(of = {"product", "order"})
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
}
