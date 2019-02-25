package ro.msg.learning.shop.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class NewOrder {
    private Timestamp timestamp;

    private String country;
    private String city;
    private String county;
    private String streetAddress;

    private List<ProductIdQuantity> products;
}
