package ro.msg.learning.shop.strategy;

import ro.msg.learning.shop.exception.LocationWithRequiredProductsNotFoundException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.LocationProductQuantity;
import ro.msg.learning.shop.model.ProductIdQuantity;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SingleLocationFinder implements LocationFinderStrategy {

    private StockRepository stockRepository;
    private LocationRepository locationRepository;
    private ProductRepository productRepository;

    public SingleLocationFinder(StockRepository stockRepository, LocationRepository locationRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<LocationProductQuantity> findLocationProductQuantity(List<ProductIdQuantity> products) {
        List<LocationProductQuantity> locationProductQuantities = new ArrayList<>();

        Map<Integer, Integer> productIdQuantity = products.stream()
                .collect(Collectors.toMap(ProductIdQuantity::getId, ProductIdQuantity::getQuantity));
        List<Integer> requiredProductIds = new ArrayList<>(productIdQuantity.keySet());

        Map<Integer, Stock> stockForProduct = stockRepository
                .findById_ProductInGroupById_LocationCountById_Product(requiredProductIds, products.size())
                .stream()
                .collect(Collectors.toMap(Stock::getProductId, item -> item));

        List<Integer> locations = stockForProduct
                .values()
                .stream()
                .distinct()
                .map(stock -> stock.getId().getLocation())
                .collect(Collectors.toList());

        for (Integer locationId : locations) {
            long countProductsWithEnoughStockInLocation = stockForProduct
                    .entrySet()
                    .stream()
                    .filter(stock -> stock.getValue().getId().getLocation() == locationId)
                    .filter(stock -> stock.getValue().getQuantity() >= productIdQuantity.get(stock.getKey()))
                    .count();

            if (countProductsWithEnoughStockInLocation == products.size()) {
                Optional<Location> location = locationRepository.findById(locationId);
                productRepository.findByIdIn(requiredProductIds)
                        .stream()
                        .map(product -> locationProductQuantities.add(new LocationProductQuantity(location.get(), product, productIdQuantity.get(product.getId()))));

                return locationProductQuantities;
            }
        }
        throw new LocationWithRequiredProductsNotFoundException();
    }
}
