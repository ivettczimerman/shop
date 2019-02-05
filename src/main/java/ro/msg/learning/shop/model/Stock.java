package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Stock")
@Table(name = "stock")
@Data
public class Stock {
    @EmbeddedId
    private StockId id;
    private int quantity;
}
