package ro.msg.learning.shop.model;

import lombok.Data;

@Data
public class ProductIdQuantity {
    private int id;
    private int quantity;

    public ProductIdQuantity(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
