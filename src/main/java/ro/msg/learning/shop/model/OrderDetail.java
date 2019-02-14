package ro.msg.learning.shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Data
@EqualsAndHashCode(of = {"product", "order"})
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
}
