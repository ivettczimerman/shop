package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Product")
@Table(name = "product")
@Data
public class Product {
    @Id
    @SequenceGenerator(name = "product_generator", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(generator = "product_generator")
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;

    @JsonUnwrapped
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private ProductCategory category;

    @JsonBackReference
    @JsonUnwrapped
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier")
    private Supplier supplier;

    @JsonManagedReference
    @JsonUnwrapped
    @OneToMany(
            mappedBy = "location",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Stock> locations = new ArrayList<>();

    @JsonManagedReference
    @JsonUnwrapped
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderDetail> orders = new ArrayList<>();
}
