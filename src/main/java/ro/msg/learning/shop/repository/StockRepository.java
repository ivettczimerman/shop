package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer>, StockRepositoryCustom {
    List<Stock> findByIdLocation(int locationId);

    List<Stock> findByIdIn(List<StockId> ids);
}
