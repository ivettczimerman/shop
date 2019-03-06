package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.model.OrderDetail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom {

    private static final String CREATED_ON = "createdOn";
    private static final String DATE_FORMAT = "YYYY-MM-DD";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderDetail> getOrderDetailsWithOrdersPlacedOn(LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderDetail> query = cb.createQuery(OrderDetail.class);
        Root<OrderDetail> orderDetail = query.from(OrderDetail.class);
        List<Predicate> predicates = new ArrayList<>();
        Path<Timestamp> createdOnPath = orderDetail.get("order").get(CREATED_ON);

        predicates.add(
                cb.equal(
                        cb.function("TRUNC", LocalDate.class, createdOnPath),
                        cb.function(
                                "TO_DATE",
                                String.class,
                                cb.parameter(LocalDate.class, CREATED_ON),
                                cb.literal(DATE_FORMAT)
                        )
                )
        );

        query.select(orderDetail)
                .where(cb.or(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query)
                .setParameter(CREATED_ON, date)
                .getResultList();
    }
}
