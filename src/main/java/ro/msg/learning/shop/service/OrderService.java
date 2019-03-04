package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.strategy.LocationFinderStrategy;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final LocationFinderStrategy locationFinderStrategy;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockService stockService;

    @Transactional
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
        Address address = newOrder.getAddress();
        order.setAddress(address);

        //Set customer
        order.setCustomer(customerRepository.findById(1).orElse(null));

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
