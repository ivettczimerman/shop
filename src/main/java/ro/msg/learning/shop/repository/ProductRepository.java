package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
    @Override
    void deleteAll();

    @Override
    void delete(Product product);

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<Product> findById(Integer integer);

    @Override
    List<Product> findAll();

    @Override
    <S extends Product> S save(S s);
}
