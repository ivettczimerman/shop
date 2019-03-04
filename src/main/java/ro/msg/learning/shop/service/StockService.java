package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.model.LocationProductQuantity;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;
import ro.msg.learning.shop.repository.StockRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    @Transactional
    public List<Stock> getStockForLocation(int locationId) {
        List<Stock> stocks = stockRepository.findByIdLocation(locationId);
        if (stocks.isEmpty()) {
            throw new LocationNotFoundException();
        }
        return stocks;
    }

    @Transactional
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
