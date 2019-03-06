package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.RevenueRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@Service
@RequiredArgsConstructor
public class RevenueService {

    private final OrderDetailRepository orderDetailRepository;
    private final RevenueRepository revenueRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void aggregateRevenuesFromYesterday() {
        LocalDate yesterday = LocalDate.now().minus(1, DAYS);

        Map<Location, List<OrderDetail>> orderDetailsByLocation =
                orderDetailRepository.getOrderDetailsWithOrdersPlacedOn(yesterday)
                        .stream()
                        .collect(Collectors.groupingBy(orderDetail -> orderDetail.getOrder().getShippedFrom()));

        List<Revenue> revenues = orderDetailsByLocation.keySet()
                .stream()
                .map(location -> createRevenue(yesterday, orderDetailsByLocation.get(location), location))
                .collect(Collectors.toList());

        revenues.forEach(revenueRepository::saveAndFlush);

    }

    @Transactional
    private Revenue createRevenue(LocalDate date, List<OrderDetail> orderDetails, Location location) {
        BigDecimal sum = orderDetails
                .stream()
                .map(orderDetail -> orderDetail.getProduct().getPrice().multiply(new BigDecimal(orderDetail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Revenue(location, Date.valueOf(date), sum);
    }
}
