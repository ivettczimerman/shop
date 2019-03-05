package ro.msg.learning.shop.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.exception.LocationNotFoundException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NearestLocationFinder implements LocationFinderStrategy {
    private static final String DISTANCE_MATRIX_API = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
    private static final String DISTANCE_MATRIX_OUTPUT_FORMAT = "json";
    private static final String DISTANCE_MATRIX_PARAM_ORIGINS = "?origins=";
    private static final String DISTANCE_MATRIX_PARAM_DESTINATIONS = "&destinations=";
    private static final String DISTANCE_MATRIX_PARAM_KEY = "&key=";

    @Value("${GOOGLE_KEY}")
    private String distanceMatrixApiKey;

    private StockRepository stockRepository;
    private LocationRepository locationRepository;
    private ProductRepository productRepository;

    @Autowired
    public NearestLocationFinder(StockRepository stockRepository, LocationRepository locationRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<LocationProductQuantity> findLocationProductQuantity(NewOrder order) {
        List<ProductIdQuantity> products = order.getProducts();
        Map<Integer, Integer> productIdQuantity = products.stream()
                .collect(Collectors.toMap(ProductIdQuantity::getId, ProductIdQuantity::getQuantity));
        List<Integer> requiredProductIds = new ArrayList<>(productIdQuantity.keySet());

        List<Integer> stockWithAllProducts = stockRepository
                .findLocationsWithEnoughProductQuantities(products);

        if (stockWithAllProducts.isEmpty()) {
            throw new LocationNotFoundException();
        } else {
            List<Location> locations = locationRepository.findAllById(stockWithAllProducts);
            List<Address> stockAddresses = locations.stream()
                    .map(Location::getAddress)
                    .collect(Collectors.toList());
            DistanceMatrixResponse response = findDistanceToLocation(
                    convertAddressToDistanceMatrixParam(order.getAddress()),
                    convertAddressesToDistanceMatrixParam(stockAddresses)
            );

            Integer locationId = locations
                    .get(response.getNearestLocationIndex())
                    .getId();

            Optional<Location> location = locationRepository.findById(locationId);
            if (!location.isPresent()) {
                throw new LocationNotFoundException();
            }

            return productRepository.findByIdIn(requiredProductIds)
                    .stream()
                    .map(product -> new LocationProductQuantity(location.orElse(null), product, productIdQuantity.get(product.getId())))
                    .collect(Collectors.toList());
        }
    }

    private DistanceMatrixResponse findDistanceToLocation(String origins, String destinations) {
        String url = DISTANCE_MATRIX_API + DISTANCE_MATRIX_OUTPUT_FORMAT + DISTANCE_MATRIX_PARAM_ORIGINS + origins +
                DISTANCE_MATRIX_PARAM_DESTINATIONS + destinations + DISTANCE_MATRIX_PARAM_KEY + distanceMatrixApiKey;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, DistanceMatrixResponse.class);
    }

    private String convertAddressesToDistanceMatrixParam(List<Address> addresses) {
        return addresses.stream()
                .map(this::convertAddressToDistanceMatrixParam)
                .collect(Collectors.joining("|"));
    }

    private String convertAddressToDistanceMatrixParam(Address address) {
        return address.getStreetAddress() + "+" +
                address.getCity() + "+" +
                address.getCounty() + "+" +
                address.getCountry();
    }
}
