package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Override
    Optional<Customer> findById(Integer integer);

    @Override
    List<Customer> findAll();

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(Customer customer);

    @Override
    void deleteAll();

    @Override
    <S extends Customer> S save(S s);
}
