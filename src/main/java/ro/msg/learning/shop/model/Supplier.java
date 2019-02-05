package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Supplier")
@Table(name = "supplier")
@Data
public class Supplier {
    @Id
    @GeneratedValue
    private int id;
    private String name;
}
