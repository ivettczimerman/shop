package ro.msg.learning.shop.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.model.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductRepositoryTest {
    private static final int ID = 1;
    private static final String NAME = "Phone";
    private static final String DESCRIPTION = "This is a phone";
    private static final BigDecimal PRICE = new BigDecimal(895.56);
    private static final Double WEIGHT = 0.234;
    private static final int CATEGORY = 1;
    private static final int SUPPLIER = 5;
    private static final String UPDATED_DESCRIPTION = "This is an updated phone";

    @Autowired
    private ProductRepository productRepository;

    private Product phone;

    @Before
    public void setUp() {
        phone = new Product();
        phone.setId(ID);
        phone.setName(NAME);
        phone.setDescription(DESCRIPTION);
        phone.setPrice(PRICE);
        phone.setWeight(WEIGHT);
        phone.setCategory(CATEGORY);
        phone.setSupplier(SUPPLIER);
    }

    @Test
    public void add_addsGivenProduct() {
        productRepository.add(phone);
        Product result = productRepository.getById(phone.getId());

        assertThat(result).hasFieldOrPropertyWithValue("name", NAME);
        assertThat(result).hasFieldOrPropertyWithValue("description", DESCRIPTION);
        assertThat(result).hasFieldOrPropertyWithValue("price", PRICE);
        assertThat(result).hasFieldOrPropertyWithValue("weight", WEIGHT);
        assertThat(result).hasFieldOrPropertyWithValue("category", CATEGORY);
        assertThat(result).hasFieldOrPropertyWithValue("supplier", SUPPLIER);
    }

    @Test
    public void getById_returnsValidProduct() {
        productRepository.add(phone);
        Product product = productRepository.getById(phone.getId());
        assertThat(product.getName()).isEqualTo(phone.getName());
        assertThat(product.getDescription()).isEqualTo(phone.getDescription());
        assertThat(product.getPrice()).isEqualTo(phone.getPrice());
        assertThat(product.getWeight()).isEqualTo(phone.getWeight());
        assertThat(product.getCategory()).isEqualTo(phone.getCategory());
        assertThat(product.getSupplier()).isEqualTo(phone.getSupplier());
    }

    @Test
    public void getAll_returnsListOfProductsFromDataSql() {
        productRepository.add(phone);
        List<Product> products = productRepository.getAll();
        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void getAll_addProducts_returnsListOfAllProducts() {
        productRepository.add(phone);
        List<Product> products = productRepository.getAll();
        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();
        assertThat(products).contains(phone);
        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void update_productUpdated() {
        productRepository.add(phone);
        phone.setDescription(UPDATED_DESCRIPTION);
        productRepository.update(phone);

        Product updatedPhone = productRepository.getById(phone.getId());
        assertThat(updatedPhone.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void remove_deletesProduct() {
        productRepository.add(phone);
        productRepository.remove(phone.getId());
        assertThat(productRepository.getAll().contains(phone)).isFalse();
    }
}
