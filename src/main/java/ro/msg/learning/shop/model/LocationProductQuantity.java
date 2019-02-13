package ro.msg.learning.shop.model;

import lombok.Data;

@Data
public class LocationProductQuantity {
    private Location location;
    private Product product;
    private int quantity;

    public LocationProductQuantity(Location location, Product product, int quantity) {
        this.location = location;
        this.product = product;
        this.quantity = quantity;
    }

    public StockId getStockId() {
        return new StockId(product.getId(), location.getId());
    }
}
