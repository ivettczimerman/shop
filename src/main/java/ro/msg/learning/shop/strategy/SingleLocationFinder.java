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
        Map<Integer, Integer> productIdQuantity = products.stream()
                .collect(Collectors.toMap(ProductIdQuantity::getId, ProductIdQuantity::getQuantity));
        List<Integer> requiredProductIds = new ArrayList<>(productIdQuantity.keySet());

        Map<Integer, Stock> stockWithAllProducts = stockRepository
                .findLocationsWithEnoughProductQuantities(products)
                .stream()
                .collect(Collectors.toMap(Stock::getProductId, item -> item));

        if (stockWithAllProducts.isEmpty()) {
            throw new LocationWithRequiredProductsNotFoundException();
        } else {

            Integer locationId = stockWithAllProducts.values().iterator().next().getId().getLocation();
            Optional<Location> location = locationRepository.findById(locationId);
            if (!location.isPresent()) {
                throw new LocationWithRequiredProductsNotFoundException();
            }

            return productRepository.findByIdIn(requiredProductIds)
                    .stream()
                    .map(product -> new LocationProductQuantity(location.orElse(null), product, productIdQuantity.get(product.getId())))
                    .collect(Collectors.toList());


        }
    }
}
