package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.Order;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Override
    Optional<Order> findById(Integer integer);

    @Override
    Iterable<Order> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(Order order);

    @Override
    <S extends Order> S save(S s);
}
