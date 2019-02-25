package ro.msg.learning.shop.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {

    private String city;
    private String county;
    private String country;
    private String streetAddress;

    public Address() {
    }

    public Address(String city, String county, String country, String streetAddress) {
        this.city = city;
        this.county = county;
        this.country = country;
        this.streetAddress = streetAddress;
    }
}
