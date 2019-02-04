package ro.msg.learning.shop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.rowmappers.ProductCategoryRowMapper;

import java.sql.Types;
import java.util.List;

@Repository
public class ProductCategoryRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ProductCategory getById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, description FROM product_category WHERE id = ?",
                new Object[]{id},
                new ProductCategoryRowMapper()
        );
    }

    public List<ProductCategory> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM product_category",
                new ProductCategoryRowMapper());
    }

    public void add(ProductCategory productCategory) {

        jdbcTemplate.update(
                "INSERT INTO product_category(name, description) VALUES (?,?)",
                productCategory.getName(), productCategory.getDescription());
    }

    public void update(ProductCategory category) {
        Object[] params = {category.getName(), category.getDescription(), category.getId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
        jdbcTemplate.update(
                "UPDATE product_category SET name = ?, description = ? WHERE id = ?",
                params,
                types);
    }

    public void remove(int id) {
        jdbcTemplate.update("DELETE FROM product_category WHERE id = ?", id);
    }
}
