package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.LocationProductQuantity;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockService {
    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getStockForLocation(int locationId) {
        return stockRepository.findAllByIdLocation(locationId);
    }

    void subtractShippedGoods(List<LocationProductQuantity> locationProductQuantities) {
        Map<StockId, LocationProductQuantity> locationProductQuantityMap = locationProductQuantities
                .stream()
                .collect(Collectors.toMap(LocationProductQuantity::getStockId, item -> item));

        List<StockId> stockIds = new ArrayList<>(locationProductQuantityMap.keySet());
        stockRepository.saveAll(
                stockRepository.findByIdIn(stockIds)
                        .stream()
                        .map(stock -> subtractOrderedProductsQuantity(
                                stock, locationProductQuantityMap.get(stock.getId()).getQuantity()))
                        .collect(Collectors.toList())
        );
    }

    private Stock subtractOrderedProductsQuantity(Stock stock, int orderedQuantity) {
        int oldQuantity = stock.getQuantity();
        stock.setQuantity(oldQuantity - orderedQuantity);
        return stock;
    }
}
