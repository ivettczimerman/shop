package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    @Override
    Optional<Supplier> findById(Integer integer);

    @Override
    List<Supplier> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(Supplier supplier);

    @Override
    <S extends Supplier> S save(S s);
}
