package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.OrderDetail;

import java.util.Optional;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {
    @Override
    Iterable<OrderDetail> findAll();

    @Override
    Optional<OrderDetail> findById(Integer integer);

    @Override
    void delete(OrderDetail orderDetail);

    @Override
    void deleteById(Integer integer);

    @Override
    void deleteAll();

    @Override
    <S extends OrderDetail> S save(S s);
}
