package ro.msg.learning.shop.csvconverter;

import lombok.Data;

@Data
class TestStock {
    private int id;
    private int location;
    private int product;
    private int quantity;

    TestStock() {
    }

    TestStock(int id, int location, int product, int quantity) {
        this.id = id;
        this.location = location;
        this.product = product;
        this.quantity = quantity;
    }
}

