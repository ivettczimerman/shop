package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.model.ProductIdQuantity;
import ro.msg.learning.shop.model.Stock;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StockRepositoryCustomImpl implements StockRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Integer> findLocationsWithEnoughProductQuantities(List<ProductIdQuantity> productIdQuantities) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<Stock> stock = query.from(Stock.class);
        List<Predicate> predicates = new ArrayList<>();
        Path<Integer> productIdPath = stock.get("id").get("product");
        Path<Integer> locationIdPath = stock.get("id").get("location");
        Path<Integer> quantityPath = stock.get("quantity");

        int size = productIdQuantities.stream()
                .map(productIdQuantity -> predicates.add(
                        cb.and(
                                cb.equal(productIdPath, productIdQuantity.getId()),
                                cb.greaterThanOrEqualTo(quantityPath, productIdQuantity.getQuantity())
                        )
                ))
                .collect(Collectors.toList())
                .size();

        query
                .select(locationIdPath)
                .where(cb.or(predicates.toArray(new Predicate[0])))
                .groupBy(locationIdPath)
                .having(cb.equal(cb.count(productIdPath), size));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
