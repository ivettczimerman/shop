package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop_user")
@Data
public class User {

    @Id
    private String username;

    @Column(name = "pwd")
    private String password;
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;
}
