package ro.msg.learning.shop.repository;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.model.Location;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    @Override
    Iterable<Location> findAll();

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
