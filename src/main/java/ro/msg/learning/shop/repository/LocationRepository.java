package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.Location;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Override
    List<Location> findAll();

    @Override
    Optional<Location> findById(Integer integer);

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(Location location);

    @Override
    void deleteAll();

    @Override
    <S extends Location> S save(S s);
}
