package ro.msg.learning.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.LocationFinderStrategy;
import ro.msg.learning.shop.strategy.NearestLocationFinder;
import ro.msg.learning.shop.strategy.SingleLocationFinder;

import static ro.msg.learning.shop.strategy.LocationStrategyType.NEAREST_LOCATION;
import static ro.msg.learning.shop.strategy.LocationStrategyType.SINGLE_LOCATION;

@Configuration
public class LocationStrategyConfig {

    private StockRepository stockRepository;
    private LocationRepository locationRepository;
    private ProductRepository productRepository;

    @Value("${location-strategy}")
    private String locationStrategyType;

    public LocationStrategyConfig(StockRepository stockRepository, LocationRepository locationRepository,
                                  ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
    }

    @Bean
    public LocationFinderStrategy getLocationFinderStrategy() {

        if (locationStrategyType.equalsIgnoreCase(SINGLE_LOCATION.name())) {
            return new SingleLocationFinder(stockRepository, locationRepository, productRepository);
        } else if (locationStrategyType.equalsIgnoreCase(NEAREST_LOCATION.name()))
            return new NearestLocationFinder(stockRepository, locationRepository, productRepository);
        else {
            return new SingleLocationFinder(stockRepository, locationRepository, productRepository);
        }
    }
}
