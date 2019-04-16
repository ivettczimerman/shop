package ro.msg.learning.shop.edm;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;

import java.util.Arrays;
import java.util.Collections;

import static ro.msg.learning.shop.edm.ShopEdmProvider.NAMESPACE;

public class ProductEdmProvider extends ShopEntityEdmProvider {
    public static final String ENTITY_SET_NAME = "Products";
    static final String ENTITY_SET_NAME_STOCKS = "Stocks";
    static final String ENTITY_SET_NAME_SUPPLIERS = "Suppliers";
    static final String ENTITY_SET_NAME_CATEGORIES = "Categories";

    private static final String ENTITY_TYPE_NAME = "Product";
    static final FullQualifiedName ENTITY_TYPE_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_TYPE_NAME);
    static final String RELATIONSHIP_PRODUCT_CATEGORY = "Product_Category_Category_Products";
    static final String RELATIONSHIP_PRODUCT_SUPPLIER = "Product_Supplier_Supplier_Products";
    static final String RELATIONSHIP_PRODUCT_STOCK = "Stock_Product_Product_Stocks";

    static final FullQualifiedName ASSOCIATION_PRODUCT_CATEGORY_FQNAME = new FullQualifiedName(NAMESPACE, RELATIONSHIP_PRODUCT_CATEGORY);
    static final FullQualifiedName ASSOCIATION_PRODUCT_SUPPLIER_FQNAME = new FullQualifiedName(NAMESPACE, RELATIONSHIP_PRODUCT_SUPPLIER);
    static final FullQualifiedName ASSOCIATION_PRODUCT_STOCK_FQNAME = new FullQualifiedName(NAMESPACE, RELATIONSHIP_PRODUCT_STOCK);

    static final String ASSOCIATION_SET_PRODUCT_CATEGORY = "Products_Categories";
    static final String ASSOCIATION_SET_PRODUCT_SUPPLIER = "Products_Suppliers";
    static final String ASSOCIATION_SET_PRODUCT_STOCK = "Products_Stocks";

    static final String ROLE_PRODUCT_CATEGORY = "Product_Category";
    static final String ROLE_CATEGORY_PRODUCTS = "Category_Products";
    static final String ROLE_PRODUCT_SUPPLIER = "Product_Supplier";
    static final String ROLE_SUPPLIER_PRODUCTS = "Supplier_Products";
    static final String ROLE_STOCK_PRODUCT = "Stock_Product";
    static final String ROLE_PRODUCT_STOCKS = "Product_Stocks";

    public static final String ENTITY_PROPERTY_ID = "Id";
    public static final String ENTITY_PROPERTY_NAME = "Name";
    public static final String ENTITY_PROPERTY_DESCRIPTION = "Description";
    public static final String ENTITY_PROPERTY_PRICE = "Price";
    public static final String ENTITY_PROPERTY_WEIGHT = "Weight";
    public static final String ENTITY_PROPERTY_CATEGORY = "Category";
    public static final String ENTITY_PROPERTY_SUPPLIER = "Supplier";

    private static final String ENTITY_TYPE_STOCK = "Stock";
    static final FullQualifiedName ENTITY_TYPE_CATEGORY_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_PROPERTY_CATEGORY);
    static final FullQualifiedName ENTITY_TYPE_SUPPLIER_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_PROPERTY_SUPPLIER);
    static final FullQualifiedName ENTITY_TYPE_STOCK_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_TYPE_STOCK);

    private static final String NAVIGATION_CATEGORY = "Category";
    private static final String NAVIGATION_SUPPLIER = "Supplier";
    private static final String NAVIGATION_STOCKS = "Stocks";
    private static final String NAVIGATION_ORDER_DETAILS = "OrderDetails";

    private static final String RELATIONSHIP_PRODUCT_ORDER_DETAIL = "OrderDetail_Product_Product_OrderDetails";

    private static final String ROLE_ORDERDETAIL_PRODUCT = "OrderDetail_Product";
    private static final String ROLE_PRODUCT_ORDERDETAILS = "Product_OrderDetails";

    EntityType getEntityType() {
        EntityType entityType = new EntityType();
        entityType.setName(ENTITY_TYPE_NAME);

        Property id = getProperty(ENTITY_PROPERTY_ID, EdmSimpleTypeKind.Int32);
        Property name = getProperty(ENTITY_PROPERTY_NAME, EdmSimpleTypeKind.String);
        Property description = getProperty(ENTITY_PROPERTY_DESCRIPTION, EdmSimpleTypeKind.String);
        Property price = getProperty(ENTITY_PROPERTY_PRICE, EdmSimpleTypeKind.Decimal);
        Property weight = getProperty(ENTITY_PROPERTY_WEIGHT, EdmSimpleTypeKind.Double);
        Property category = getProperty(ENTITY_PROPERTY_CATEGORY, EdmSimpleTypeKind.Int32);
        Property supplier = getProperty(ENTITY_PROPERTY_SUPPLIER, EdmSimpleTypeKind.Int32);

        entityType.setProperties(Arrays.asList(id, name, description, price, weight, category, supplier));

        PropertyRef ref = new PropertyRef().setName(ENTITY_PROPERTY_ID);
        entityType.setKey(new Key().setKeys(Collections.singletonList(ref)));

        entityType.setNavigationProperties(
                Arrays.asList(
                        getNavigationProperty(NAVIGATION_CATEGORY, RELATIONSHIP_PRODUCT_CATEGORY, ROLE_PRODUCT_CATEGORY, ROLE_CATEGORY_PRODUCTS),
                        getNavigationProperty(NAVIGATION_SUPPLIER, RELATIONSHIP_PRODUCT_SUPPLIER, ROLE_PRODUCT_SUPPLIER, ROLE_SUPPLIER_PRODUCTS),
                        getNavigationProperty(NAVIGATION_STOCKS, RELATIONSHIP_PRODUCT_STOCK, ROLE_STOCK_PRODUCT, ROLE_PRODUCT_STOCKS),
                        getNavigationProperty(NAVIGATION_ORDER_DETAILS, RELATIONSHIP_PRODUCT_ORDER_DETAIL, ROLE_ORDERDETAIL_PRODUCT, ROLE_PRODUCT_ORDERDETAILS)
                )
        );
        return entityType;
    }

    EntitySet getEntitySet() {
        return super.getEntitySet(ENTITY_SET_NAME, ENTITY_TYPE_FQNAME);
    }
}
