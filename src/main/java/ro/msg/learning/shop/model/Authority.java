package ro.msg.learning.shop.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Table
@Entity
@ToString(exclude = {"users"})
public class Authority {
    @Id
    @SequenceGenerator(name = "authority_generator", sequenceName = "authority_sequence", allocationSize = 1)
    @GeneratedValue(generator = "authority_generator")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserType userType;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users;
}
