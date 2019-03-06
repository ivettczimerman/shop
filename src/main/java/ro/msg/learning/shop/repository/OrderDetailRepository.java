package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>, OrderDetailRepositoryCustom {
}
