package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.model.ProductIdQuantity;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;

import java.util.List;

public interface StockRepositoryCustom {
    List<Integer> findLocationsWithEnoughProductQuantities(List<ProductIdQuantity> productIdQuantities);

    List<Stock> findAllByIdLocation(int locationId);

    List<Stock> findByIdIn(List<StockId> ids);
}
