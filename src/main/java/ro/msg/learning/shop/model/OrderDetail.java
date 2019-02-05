package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "OrderDetail")
@Table(name = "order_detail")
@Data
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;
    private int quantity;
}
