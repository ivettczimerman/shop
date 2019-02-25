package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.strategy.LocationFinderStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private LocationFinderStrategy locationFinderStrategy;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private StockService stockService;

    @Autowired
    public OrderService(LocationFinderStrategy locationFinderStrategy, OrderRepository orderRepository,
                        StockService stockService, CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.locationFinderStrategy = locationFinderStrategy;
        this.orderRepository = orderRepository;
        this.stockService = stockService;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(NewOrder newOrder) {
        List<LocationProductQuantity> locationProductQuantities = locationFinderStrategy
                .findLocationProductQuantity(newOrder.getProducts());

        Order order = mapNewOrderToOrder(newOrder, locationProductQuantities.get(0).getLocation());
        orderRepository.saveAndFlush(order);

        stockService.subtractShippedGoods(locationProductQuantities);

        return order;
    }

    private Order mapNewOrderToOrder(NewOrder newOrder, Location location) {
        Order order = new Order();
        order.setCountry(newOrder.getCountry());
        order.setCity(newOrder.getCity());
        order.setCounty(newOrder.getCounty());
        order.setStreetAddress(newOrder.getStreetAddress());

        //Set customer
        customerRepository.findById(1);

        //Set Location
        order.setShippedFrom(location);

        Map<Integer, Integer> productIdQuantity = newOrder.getProducts()
                .stream()
                .collect(Collectors.toMap(ProductIdQuantity::getId, ProductIdQuantity::getQuantity));

        List<OrderDetail> orderDetails = productRepository.findByIdIn(new ArrayList<>(productIdQuantity.keySet()))
                .stream()
                .map(product -> new OrderDetail(product, order, productIdQuantity.get(product.getId())))
                .collect(Collectors.toList());

        order.setOrderDetails(orderDetails);

        return order;
    }
}
