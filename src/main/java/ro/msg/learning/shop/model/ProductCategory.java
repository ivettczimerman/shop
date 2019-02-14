package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_category")
@Data
public class ProductCategory {
    @Id
    @SequenceGenerator(name = "product_category_generator", sequenceName = "product_category_sequence", allocationSize = 1)
    @GeneratedValue(generator = "product_category_generator")
    private int id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
