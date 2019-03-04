package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.model.ProductIdQuantity;

import java.util.List;

public interface StockRepositoryCustom {
    List<Integer> findLocationsWithEnoughProductQuantities(List<ProductIdQuantity> productIdQuantities);
}
