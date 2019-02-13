package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity(name = "Order")
@Table(name = "order")
@Data
public class Order {
    @Id
    @SequenceGenerator(name = "order_generator", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(generator = "order_generator")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shippedFrom")
    private Location shippedFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer")
    private Customer customer;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderDetail> orderDetails = new ArrayList<>();

    private String country;
    private String city;
    private String county;
    private String streetAddress;

    public void addProduct(Product product, int quantity) {
        OrderDetail orderDetail = new OrderDetail(product, this, quantity);
        orderDetails.add(orderDetail);
        product.getOrders().add(orderDetail);
    }

    public void removeProduct(Product product) {
        for (Iterator<OrderDetail> iterator = orderDetails.iterator(); iterator.hasNext(); ) {
            OrderDetail orderDetail = iterator.next();

            if (orderDetail.getOrder().equals(this) && orderDetail.getProduct().equals(product)) {
                iterator.remove();
                orderDetail.getProduct().getOrders().remove(orderDetail);
                orderDetail.setProduct(null);
                orderDetail.setOrder(null);
            }
        }
    }
}
