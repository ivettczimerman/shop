package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@Table
public class Revenue {

    @Id
    @SequenceGenerator(name = "revenue_generator", sequenceName = "revenue_sequence", allocationSize = 1)
    @GeneratedValue(generator = "revenue_generator")
    private int id;

    @JsonUnwrapped
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    @JsonBackReference
    private Location location;

    private Date date;
    private BigDecimal sum;

    public Revenue(Location location, Date date, BigDecimal sum) {
        this.location = location;
        this.date = date;
        this.sum = sum;
    }
}
