package ro.msg.learning.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.LocationFinderStrategy;
import ro.msg.learning.shop.strategy.LocationStrategyType;
import ro.msg.learning.shop.strategy.SingleLocationFinder;

import static ro.msg.learning.shop.strategy.LocationStrategyType.SINGLE_LOCATION;

@Configuration
@RequiredArgsConstructor
public class LocationStrategyConfig {

    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;
    private final ProductRepository productRepository;

    @Value("${location-strategy}")
    private LocationStrategyType locationStrategyType;

    @Bean
    public LocationFinderStrategy getLocationFinderStrategy() {
        if (locationStrategyType.equals(SINGLE_LOCATION)) {
            return new SingleLocationFinder(stockRepository, locationRepository, productRepository);
        } else return new SingleLocationFinder(stockRepository, locationRepository, productRepository);
    }
}
