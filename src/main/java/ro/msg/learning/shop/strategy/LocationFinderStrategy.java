package ro.msg.learning.shop.strategy;

import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.LocationProductQuantity;
import ro.msg.learning.shop.model.ProductIdQuantity;

import java.util.List;

public interface LocationFinderStrategy {

    List<LocationProductQuantity> findLocationProductQuantity(List<ProductIdQuantity> products, Address shipTo);
}
