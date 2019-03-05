package ro.msg.learning.shop.strategy;

import ro.msg.learning.shop.model.LocationProductQuantity;
import ro.msg.learning.shop.model.NewOrder;

import java.util.List;

public interface LocationFinderStrategy {

    List<LocationProductQuantity> findLocationProductQuantity(NewOrder order);
}
