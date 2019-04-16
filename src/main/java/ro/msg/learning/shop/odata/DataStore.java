package ro.msg.learning.shop.odata;

import ro.msg.learning.shop.edm.OrderDetailEdmProvider;
import ro.msg.learning.shop.edm.OrderEdmProvider;
import ro.msg.learning.shop.edm.ProductEdmProvider;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {

    public Map<String, Object> getOrder(int orderId) {
        Map<String, Object> data = null;
        switch (orderId) {
            case 1:
                Map<String, Object> address1 = createAddress("Cluj", "Romania", "CJ", "Brassai");
                data = createOrder(1, new Timestamp(System.currentTimeMillis()), address1, 1, 2);
                break;
            case 2:
                Map<String, Object> address2 = createAddress("Timisoara", "Romania", "TM", "Torontalului");
                data = createOrder(2, new Timestamp(System.currentTimeMillis()), address2, 2, 1);
                break;
            default:
                break;
        }
        return data;
    }

    public Map<String, Object> getProduct(int productId) {
        Map<String, Object> data = null;
        switch (productId) {
            case 1:
                data = createProduct(1, "Laptop", "Dell", BigDecimal.valueOf(1234), 3.4, 1, 2);
                break;
            case 2:
                data = createProduct(2, "Phone", "Samsung", BigDecimal.valueOf(953), 0.2, 2, 1);
                break;
            default:
                break;
        }
        return data;
    }

    public Map<String, Object> getOrderDetail(Map<String, Object> orderDetailId) {
        Map<String, Object> data = null;
        Map<String, Object> id1 = createOrderDetailId(1, 2);
        Map<String, Object> id2 = createOrderDetailId(2, 1);

        if (orderDetailId.equals(id1)) {
            data = createOrderDetail(23, orderDetailId);
        } else if (orderDetailId.equals(id2)) {
            data = createOrderDetail(4, orderDetailId);
        }
        return data;
    }

    public List<Map<String, Object>> getOrders() {
        List<Map<String, Object>> orders = new ArrayList<>();
        orders.add(getOrder(1));
        orders.add(getOrder(2));
        return orders;
    }

    public List<Map<String, Object>> getProducts() {
        List<Map<String, Object>> products = new ArrayList<>();
        products.add(getProduct(1));
        products.add(getProduct(2));
        return products;
    }

    public List<Map<String, Object>> getOrderDetails() {
        List<Map<String, Object>> orderDetails = new ArrayList<>();
        Map<String, Object> id1 = createOrderDetailId(1, 2);
        Map<String, Object> id2 = createOrderDetailId(2, 1);

        orderDetails.add(getOrderDetail(id1));
        orderDetails.add(getOrderDetail(id2));
        return orderDetails;
    }

    public Map<String, Object> getOrdersFor(Map<String, Object> orderDetailId) {
        Map<String, Object> orderDetail = getOrderDetail(orderDetailId);
        if (orderDetail != null) {
            Object orderId = orderDetail.get(OrderDetailEdmProvider.ID_PROPERTY_ORDER_ID);
            if (orderId != null) {
                return getOrder((Integer) orderId);
            }
        }
        return null;
    }

    public Map<String, Object> getProductFor(Map<String, Object> orderDetailId) {
        Map<String, Object> orderDetail = getOrderDetail(orderDetailId);
        if (orderDetail != null) {
            Object productId = orderDetail.get(OrderDetailEdmProvider.ID_PROPERTY_PRODUCT_ID);
            if (productId != null) {
                return getProduct((Integer) productId);
            }
        }
        return null;
    }

    public List<Map<String, Object>> getOrderDetailsForOrder(int orderId) {
        List<Map<String, Object>> orderDetails = getOrderDetails();
        List<Map<String, Object>> orderDetailsForOrder = new ArrayList<>();

        for (Map<String, Object> orderDetail : orderDetails) {
            Map<String, Object> odId = (Map<String, Object>) orderDetail.get(OrderDetailEdmProvider.ENTITY_PROPERTY_ID);
            if ((Integer) odId.get(OrderDetailEdmProvider.ID_PROPERTY_ORDER_ID) == orderId) {
                orderDetailsForOrder.add(orderDetail);
            }
        }
        return orderDetailsForOrder;
    }

    public List<Map<String, Object>> getOrderDetailsForProduct(int productId) {
        List<Map<String, Object>> orderDetails = getOrderDetails();
        List<Map<String, Object>> orderDetailsForProduct = new ArrayList<>();

        for (Map<String, Object> orderDetail : orderDetails) {
            Map<String, Object> odId = (Map<String, Object>) orderDetail.get(OrderDetailEdmProvider.ENTITY_PROPERTY_ID);
            if ((Integer) odId.get(OrderDetailEdmProvider.ID_PROPERTY_PRODUCT_ID) == productId) {
                orderDetailsForProduct.add(orderDetail);
            }
        }
        return orderDetailsForProduct;
    }

    public Map<String, Object> createOrderDetailId(int orderId, int productId) {
        Map<String, Object> data = new HashMap<>();
        data.put(OrderDetailEdmProvider.ID_PROPERTY_ORDER_ID, orderId);
        data.put(OrderDetailEdmProvider.ID_PROPERTY_PRODUCT_ID, productId);
        return data;
    }

    private Map<String, Object> createOrderDetail(int quantity, Map<String, Object> id) {
        Map<String, Object> data = new HashMap<>();
        data.put(OrderDetailEdmProvider.ENTITY_PROPERTY_ID, id);
        data.put(OrderDetailEdmProvider.ENTITY_PROPERTY_QUANTITY, quantity);
        return data;
    }

    private Map<String, Object> createProduct(int id, String name, String description, BigDecimal price, Double weight, int category, int supplier) {
        Map<String, Object> data = new HashMap<>();
        data.put(ProductEdmProvider.ENTITY_PROPERTY_ID, id);
        data.put(ProductEdmProvider.ENTITY_PROPERTY_NAME, name);
        data.put(ProductEdmProvider.ENTITY_PROPERTY_DESCRIPTION, description);
        data.put(ProductEdmProvider.ENTITY_PROPERTY_PRICE, price);
        data.put(ProductEdmProvider.ENTITY_PROPERTY_WEIGHT, weight);
        data.put(ProductEdmProvider.ENTITY_PROPERTY_CATEGORY, category);
        data.put(ProductEdmProvider.ENTITY_PROPERTY_SUPPLIER, supplier);
        return data;
    }

    private Map<String, Object> createOrder(int id, Timestamp createdOn, Map<String, Object> address, int customer, int shippedFrom) {
        Map<String, Object> data = new HashMap<>();
        data.put(OrderEdmProvider.ENTITY_PROPERTY_ID, id);
        data.put(OrderEdmProvider.ENTITY_PROPERTY_CREATED_ON, createdOn);
        data.put(OrderEdmProvider.ENTITY_PROPERTY_ADDRESS, address);
        data.put(OrderEdmProvider.ENTITY_PROPERTY_CUSTOMER, customer);
        data.put(OrderEdmProvider.ENTITY_PROPERTY_SHIPPED_FROM, shippedFrom);
        return data;
    }

    private Map<String, Object> createAddress(String city, String country, String county, String streetAddress) {
        Map<String, Object> data = new HashMap<>();
        data.put(OrderEdmProvider.ADDRESS_PROPERTY_CITY, city);
        data.put(OrderEdmProvider.ADDRESS_PROPERTY_COUNTRY, country);
        data.put(OrderEdmProvider.ADDRESS_PROPERTY_COUNTY, county);
        data.put(OrderEdmProvider.ADDRESS_PROPERTY_STREET_ADDRESS, streetAddress);
        return data;
    }
}
