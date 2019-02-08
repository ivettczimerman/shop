package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Override
    List<Stock> findAll();

    @Override
    Optional<Stock> findById(Integer integer);

    @Override
    void delete(Stock stock);

    @Override
    void deleteById(Integer integer);

    @Override
    void deleteAll();

    @Override
    <S extends Stock> S save(S s);
}
