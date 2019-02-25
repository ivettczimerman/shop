package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "product_order")
@Data
public class Order {
    @Id
    @SequenceGenerator(name = "order_generator", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(generator = "order_generator")
    private int id;

    @JsonUnwrapped
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shippedFrom")
    private Location shippedFrom;

    @JsonUnwrapped
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    @JsonBackReference
    private Customer customer;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Embedded
    private Address address;

    public void addProduct(Product product, int quantity) {
        OrderDetail orderDetail = new OrderDetail(product, this, quantity);
        orderDetails.add(orderDetail);
        product.getOrderDetails().add(orderDetail);
    }

    public void removeProduct(Product product) {
        for (Iterator<OrderDetail> iterator = orderDetails.iterator(); iterator.hasNext(); ) {
            OrderDetail orderDetail = iterator.next();

            if (orderDetail.getOrder().equals(this) && orderDetail.getProduct().equals(product)) {
                iterator.remove();
                orderDetail.getProduct().getOrderDetails().remove(orderDetail);
                orderDetail.setProduct(null);
                orderDetail.setOrder(null);
            }
        }
    }
}
