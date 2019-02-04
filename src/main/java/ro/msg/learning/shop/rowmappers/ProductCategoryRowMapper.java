package ro.msg.learning.shop.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import ro.msg.learning.shop.model.ProductCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCategoryRowMapper implements RowMapper<ProductCategory> {
    @Override
    public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductCategory productCategory = new ProductCategory();

        productCategory.setId(rs.getInt("id"));
        productCategory.setName(rs.getString("name"));
        productCategory.setDescription(rs.getString("description"));

        return productCategory;
    }

}
