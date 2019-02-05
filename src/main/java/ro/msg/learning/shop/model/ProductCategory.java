package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ProductCategory")
@Table(name = "product_category")
@Data
public class ProductCategory {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
}
