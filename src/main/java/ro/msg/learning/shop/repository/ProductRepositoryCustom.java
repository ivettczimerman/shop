package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findByIdIn(List<Integer> ids);
}
