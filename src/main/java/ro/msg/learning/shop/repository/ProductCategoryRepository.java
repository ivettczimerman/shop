package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    @Override
    Optional<ProductCategory> findById(Integer integer);

    @Override
    List<ProductCategory> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(ProductCategory productCategory);

    @Override
    <S extends ProductCategory> S save(S s);
}
