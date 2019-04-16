package ro.msg.learning.shop.edm;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ro.msg.learning.shop.edm.ShopEdmProvider.NAMESPACE;

public class OrderEdmProvider extends ShopEntityEdmProvider {
    private static final String ENTITY_TYPE_NAME = "Order";

    static final FullQualifiedName ENTITY_TYPE_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_TYPE_NAME);
    public static final String ENTITY_SET_NAME = "Orders";
    static final String ENTITY_SET_NAME_CUSTOMERS = "Customers";
    static final String ENTITY_SET_NAME_LOCATIONS = "Locations";

    static final String RELATIONSHIP_ORDER_LOCATION = "Order_Location_Location_Orders";
    static final String RELATIONSHIP_ORDER_CUSTOMER = "Order_Customer_Customer_Orders";
    static final FullQualifiedName ASSOCIATION_ORDER_LOCATION_FQNAME = new FullQualifiedName(NAMESPACE, RELATIONSHIP_ORDER_LOCATION);
    static final FullQualifiedName ASSOCIATION_ORDER_CUSTOMER_FQNAME = new FullQualifiedName(NAMESPACE, RELATIONSHIP_ORDER_CUSTOMER);

    static final String ASSOCIATION_SET_ORDER_LOCATION = "Orders_Locations";
    static final String ASSOCIATION_SET_ORDER_CUSTOMER = "Orders_Customers";

    static final String ROLE_ORDER_LOCATION = "Order_Location";
    static final String ROLE_LOCATION_ORDERS = "Location_Orders";
    static final String ROLE_ORDER_CUSTOMER = "Order_Customer";
    static final String ROLE_CUSTOMER_ORDERS = "Customer_Orders";

    public static final String ENTITY_PROPERTY_ID = "Id";
    public static final String ENTITY_PROPERTY_CREATED_ON = "CreatedOn";
    public static final String ENTITY_PROPERTY_ADDRESS = "Address";
    public static final String ENTITY_PROPERTY_CUSTOMER = "Customer";
    public static final String ENTITY_PROPERTY_SHIPPED_FROM = "ShippedFrom";

    private static final String NAVIGATION_LOCATION = "Location";
    private static final String NAVIGATION_CUSTOMER = "Customer";
    private static final String NAVIGATION_ORDER_DETAILS = "OrderDetails";

    private static final String RELATIONSHIP_ORDER_ORDERDETAILS = "OrderDetail_Order_Order_OrderDetails";
    private static final String ROLE_ORDERDETAIL_ORDER = "OrderDetail_Order";
    private static final String ROLE_ORDER_ORDERDETAILS = "Order_OrderDetails";

    static final FullQualifiedName COMPLEX_TYPE_ADDRESS = new FullQualifiedName(NAMESPACE, ENTITY_PROPERTY_ADDRESS);
    public static final String ADDRESS_PROPERTY_CITY = "City";
    public static final String ADDRESS_PROPERTY_COUNTY = "County";
    public static final String ADDRESS_PROPERTY_COUNTRY = "Country";
    public static final String ADDRESS_PROPERTY_STREET_ADDRESS = "StreetAddress";

    static final FullQualifiedName ENTITY_TYPE_CUSTOMER_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_PROPERTY_CUSTOMER);
    private static final String ENTITY_TYPE_LOCATION = "Location";
    static final FullQualifiedName ENTITY_TYPE_LOCATION_FQNAME = new FullQualifiedName(NAMESPACE, ENTITY_TYPE_LOCATION);

    EntityType getEntityType() {
        EntityType entityType = new EntityType();
        entityType.setName(ENTITY_TYPE_NAME);

        Property id = getProperty(ENTITY_PROPERTY_ID, EdmSimpleTypeKind.Int32);
        Property createdOn = getProperty(ENTITY_PROPERTY_CREATED_ON, EdmSimpleTypeKind.DateTimeOffset);
        Property customer = getProperty(ENTITY_PROPERTY_CUSTOMER, EdmSimpleTypeKind.Int32);
        Property location = getProperty(ENTITY_PROPERTY_SHIPPED_FROM, EdmSimpleTypeKind.Int32);

        Property address = new ComplexProperty()
                .setName(ENTITY_PROPERTY_ADDRESS)
                .setType(new FullQualifiedName(NAMESPACE, ENTITY_PROPERTY_ADDRESS));

        entityType.setProperties(Arrays.asList(id, createdOn, customer, location, address));

        PropertyRef ref = new PropertyRef().setName(ENTITY_PROPERTY_ID);
        entityType.setKey(new Key().setKeys(Collections.singletonList(ref)));

        entityType.setNavigationProperties(
                Arrays.asList(
                        getNavigationProperty(NAVIGATION_LOCATION, RELATIONSHIP_ORDER_LOCATION, ROLE_ORDER_LOCATION, ROLE_LOCATION_ORDERS),
                        getNavigationProperty(NAVIGATION_CUSTOMER, RELATIONSHIP_ORDER_CUSTOMER, ROLE_ORDER_CUSTOMER, ROLE_CUSTOMER_ORDERS),
                        getNavigationProperty(NAVIGATION_ORDER_DETAILS, RELATIONSHIP_ORDER_ORDERDETAILS, ROLE_ORDERDETAIL_ORDER, ROLE_ORDER_ORDERDETAILS)
                )
        );
        return entityType;
    }

    ComplexType getComplexType() {
        return super.getComplexType(ENTITY_PROPERTY_ADDRESS, getComplexProperties());
    }

    EntitySet getEntitySet() {
        return super.getEntitySet(ENTITY_SET_NAME, ENTITY_TYPE_FQNAME);
    }

    private List<Property> getComplexProperties() {
        Property city = getProperty(ADDRESS_PROPERTY_CITY, EdmSimpleTypeKind.String);
        Property county = getProperty(ADDRESS_PROPERTY_COUNTY, EdmSimpleTypeKind.String);
        Property country = getProperty(ADDRESS_PROPERTY_COUNTRY, EdmSimpleTypeKind.String);
        Property streetAddress = getProperty(ADDRESS_PROPERTY_STREET_ADDRESS, EdmSimpleTypeKind.String);

        return Arrays.asList(city, county, country, streetAddress);
    }
}
