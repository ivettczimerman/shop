package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "OrderDetail")
@Table(name = "order_detail")
@Data
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("order")
    private Order order;

    @Column(name = "quantity")
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(Product product, Order order) {
        this.product = product;
        this.order = order;
        this.id = new OrderDetailId(order.getId(), product.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        OrderDetail that = (OrderDetail) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order);
    }
}
