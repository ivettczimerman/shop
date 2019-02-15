package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Supplier")
@Table(name = "supplier")
@Data
public class Supplier {
    @Id
    @SequenceGenerator(name = "supplier_generator", sequenceName = "supplier_sequence", allocationSize = 1)
    @GeneratedValue(generator = "supplier_generator")
    private int id;
    private String name;

    @JsonManagedReference
    @JsonUnwrapped
    @OneToMany(mappedBy = "supplier")
    private List<Product> products;
}
