package ro.msg.learning.shop.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class NewOrder {
    private Timestamp timestamp;
    private Address address;
    private List<ProductIdQuantity> products;
}
