package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Data
@EqualsAndHashCode(of = {"product", "order"})
public class OrderDetail {
    @JsonUnwrapped
    @EmbeddedId
    private OrderDetailId id;

    @JsonUnwrapped
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product")
    private Product product;

    @JsonBackReference
    @JsonUnwrapped
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("order")
    private Order order;

    @Column(name = "quantity")
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(Product product, Order order, int quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.id = new OrderDetailId(order.getId(), product.getId());
    }
}
