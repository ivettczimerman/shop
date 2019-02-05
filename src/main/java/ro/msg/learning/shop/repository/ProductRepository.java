package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.Product;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Override
    void deleteAll();

    @Override
    void delete(Product product);

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<Product> findById(Integer integer);

    @Override
    Iterable<Product> findAll();

    @Override
    <S extends Product> S save(S s);
}
