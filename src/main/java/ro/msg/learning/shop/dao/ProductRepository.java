package ro.msg.learning.shop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.rowmappers.ProductRowMapper;

import java.sql.Types;
import java.util.List;

@Repository
public class ProductRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM product",
                new ProductRowMapper()
        );
    }

    public Product getById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, description, price, weight, category, supplier FROM product WHERE id = ?",
                new Object[]{id},
                new ProductRowMapper()
        );
    }

    public void add(Product product) {

        jdbcTemplate.update(
                "INSERT INTO product(name, description, price, weight, category, supplier) VALUES (?,?, ?, ?, ?, ?)",
                product.getName(), product.getDescription(), product.getPrice(), product.getWeight(),
                product.getCategory(), product.getSupplier());
    }

    public void update(Product product) {
        Object[] params = {
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getWeight(),
                product.getCategory(),
                product.getSupplier(),
                product.getId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.NUMERIC, Types.DOUBLE, Types.INTEGER, Types.INTEGER, Types.INTEGER};
        jdbcTemplate.update(
                "UPDATE product SET name = ?, description = ?, price = ?, weight = ?, category = ?, supplier = ? WHERE id = ?",
                params,
                types);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }
}
