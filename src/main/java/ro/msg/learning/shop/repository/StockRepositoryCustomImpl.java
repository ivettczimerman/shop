package ro.msg.learning.shop.repository;

import ro.msg.learning.shop.model.ProductIdQuantity;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class StockRepositoryCustomImpl implements StockRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Stock> findLocationsWithEnoughProductQuantities(List<ProductIdQuantity> productIdQuantities) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> query = cb.createQuery(Stock.class);
        Root<Stock> stock = query.from(Stock.class);
        List<Predicate> predicates = new ArrayList<>();
        Path<Integer> productIdPath = stock.get("id").get("product");
        Path<Integer> locationIdPath = stock.get("id").get("location");
        Path<Integer> quantityPath = stock.get("quantity");

        productIdQuantities.forEach(productIdQuantity -> predicates.add(
                cb.and(
                        cb.equal(productIdPath, productIdQuantity.getId()),
                        cb.greaterThanOrEqualTo(quantityPath, productIdQuantity.getId())
                )
        ));

        query
                .select(stock)
                .where(cb.or(predicates.toArray(new Predicate[0])))
                .groupBy(locationIdPath)
                .having(cb.equal(cb.count(productIdPath), productIdQuantities.size()));

        return entityManager.createQuery(query)
                .getResultList();
    }

    @Override
    public List<Stock> findAllByIdLocation(int locationId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> query = cb.createQuery(Stock.class);
        Root<Stock> stock = query.from(Stock.class);
        List<Predicate> predicates = new ArrayList<>();

        Path<Integer> locationIdPath = stock.get("location").get("id");
        predicates.add(cb.equal(locationIdPath, locationId));
        query.select(stock).where(cb.or(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query)
                .getResultList();

    }

    @Override
    public List<Stock> findByIdIn(List<StockId> ids) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stock> query = cb.createQuery(Stock.class);
        Root<Stock> stock = query.from(Stock.class);
        List<Predicate> predicates = new ArrayList<>();
        Path<StockId> stockIdPath = stock.get("id");

        ids.forEach(stockId -> predicates.add(cb.equal(stockIdPath, stockId)));
        query.select(stock).where(cb.or(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
