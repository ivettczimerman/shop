package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.model.OrderDetail;

import java.time.LocalDate;
import java.util.List;

public interface OrderDetailRepositoryCustom {
    List<OrderDetail> getOrderDetailsWithOrdersPlacedOn(LocalDate date);
}
