package ro.msg.learning.shop.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.model.ProductCategory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductCategoryRepositoryTest {

    private static final String NAME = "Product category";
    private static final String DESCRIPTION = "This is a product category";
    private static final String UPDATED_DESCRIPTION = "This is an updated product category";

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    private ProductCategory productCategory;

    @Before
    public void setUp() {
        productCategory = new ProductCategory();
        productCategory.setId(1);
        productCategory.setName(NAME);
        productCategory.setDescription(DESCRIPTION);
    }

    @Test
    public void add_addsProductCategory() {
        productCategoryRepository.add(productCategory);
        ProductCategory result = productCategoryRepository.getById(productCategory.getId());
        assertThat(result).hasFieldOrPropertyWithValue("name", NAME);
        assertThat(result).hasFieldOrPropertyWithValue("description", DESCRIPTION);
    }

    @Test
    public void getById_returnsValidProductCategory() {
        productCategoryRepository.add(productCategory);
        ProductCategory result = productCategoryRepository.getById(productCategory.getId());

        assertThat(result.getName()).isEqualTo(productCategory.getName());
        assertThat(result.getDescription()).isEqualTo(productCategory.getDescription());
    }

    @Test
    public void getAll_returnsListOfAllProductCategories() {
        productCategoryRepository.add(productCategory);
        List<ProductCategory> productCategories = productCategoryRepository.getAll();
        assertThat(productCategories).isNotNull();
        assertThat(productCategories).isNotEmpty();
        assertThat(productCategories).contains(productCategory);
        assertThat(productCategories.size()).isEqualTo(1);
    }

    @Test
    public void update_productCategoryUpdated() {
        productCategoryRepository.add(productCategory);
        productCategoryRepository.update(productCategory);
        productCategory.setDescription(UPDATED_DESCRIPTION);
        productCategoryRepository.update(productCategory);

        ProductCategory pc = productCategoryRepository.getById(productCategory.getId());
        assertThat(pc.getName()).isEqualTo(productCategory.getName());
        assertThat(pc.getDescription()).isEqualTo(productCategory.getDescription());
    }

    @Test
    public void remove_deletesProductCategory() {
        productCategoryRepository.add(productCategory);
        productCategoryRepository.remove(productCategory.getId());
    }
}
