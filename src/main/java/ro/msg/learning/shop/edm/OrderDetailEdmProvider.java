package ro.msg.learning.shop.edm;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ro.msg.learning.shop.edm.ShopEdmProvider.NAMESPACE;

@Component
public class OrderDetailEdmProvider extends ShopEntityEdmProvider {
    private static final String ENTITY_TYPE_NAME = "OrderDetail";
    public static final String ENTITY_SET_NAME = "OrderDetails";
    static final FullQualifiedName ENTITY_TYPE_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_TYPE_NAME);

    static final String RELATIONSHIP_ORDERDETAIL_PRODUCT = "OrderDetail_Product_Product_OrderDetails";
    static final String RELATIONSHIP_ORDERDETAIL_ORDER = "OrderDetail_Order_Order_OrderDetails";

    static final FullQualifiedName ASSOCIATION_ORDERDETAIL_PRODUCT_FQNAME = new FullQualifiedName(NAMESPACE, RELATIONSHIP_ORDERDETAIL_PRODUCT);
    static final FullQualifiedName ASSOCIATION_ORDERDETAIL_ORDER_FQNAME = new FullQualifiedName(NAMESPACE, RELATIONSHIP_ORDERDETAIL_ORDER);

    static final String ASSOCIATION_SET_ORDERDETAILS_PRODUCTS = "OrderDetails_Products";
    static final String ASSOCIATION_SET_ORDERDETAILS_ORDERS = "OrderDetails_Orders";

    static final String ROLE_ORDERDETAIL_PRODUCT = "OrderDetail_Product";
    static final String ROLE_PRODUCT_ORDERDETAILS = "Product_OrderDetails";
    static final String ROLE_ORDERDETAIL_ORDER = "OrderDetail_Order";
    static final String ROLE_ORDER_ORDERDETAILS = "Order_OrderDetails";

    public static final String ENTITY_PROPERTY_ID = "OrderDetailId";
    public static final String ENTITY_PROPERTY_QUANTITY = "Quantity";

    private static final String NAVIGATION_PRODUCT = "Product";
    private static final String NAVIGATION_ORDER = "Order";

    static final FullQualifiedName COMPLEX_TYPE_ORDERDETAILID = new FullQualifiedName(NAMESPACE, ENTITY_PROPERTY_ID);
    public static final String ID_PROPERTY_ORDER_ID = "OrderId";
    public static final String ID_PROPERTY_PRODUCT_ID = "ProductId";

    private static final String ENTITY_TYPE_PRODUCT = "Product";
    static final FullQualifiedName ENTITY_TYPE_PRODUCT_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_TYPE_PRODUCT);
    private static final String ENTITY_TYPE_ORDER = "Order";
    static final FullQualifiedName ENTITY_TYPE_ORDER_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_TYPE_ORDER);

    EntityType getEntityType() {
        EntityType entityType = new EntityType();
        entityType.setName(ENTITY_TYPE_NAME);

        Property quantity = getProperty(ENTITY_PROPERTY_QUANTITY, EdmSimpleTypeKind.Int32);
        Property id = new ComplexProperty()
                .setName(ENTITY_PROPERTY_ID)
                .setType(new FullQualifiedName(NAMESPACE, ENTITY_PROPERTY_ID));

        entityType.setProperties(Arrays.asList(id, quantity));

        PropertyRef ref = new PropertyRef().setName(ENTITY_PROPERTY_ID);
        entityType.setKey(new Key().setKeys(Collections.singletonList(ref)));

        entityType.setNavigationProperties(
                Arrays.asList(
                        getNavigationProperty(NAVIGATION_PRODUCT, RELATIONSHIP_ORDERDETAIL_PRODUCT, ROLE_ORDERDETAIL_PRODUCT, ROLE_PRODUCT_ORDERDETAILS),
                        getNavigationProperty(NAVIGATION_ORDER, RELATIONSHIP_ORDERDETAIL_ORDER, ROLE_ORDERDETAIL_ORDER, ROLE_ORDER_ORDERDETAILS)
                )
        );
        return entityType;
    }

    ComplexType getComplexType() {
        return super.getComplexType(ENTITY_PROPERTY_ID, getComplexProperties());
    }

    EntitySet getEntitySet() {
        return super.getEntitySet(ENTITY_SET_NAME, ENTITY_TYPE_FQNAME);
    }

    private List<Property> getComplexProperties() {
        Property orderId = getProperty(ID_PROPERTY_ORDER_ID, EdmSimpleTypeKind.Int32);
        Property productId = getProperty(ID_PROPERTY_PRODUCT_ID, EdmSimpleTypeKind.Int32);

        return Arrays.asList(
                orderId, productId);
    }
}
