package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Override
    List<OrderDetail> findAll();

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
