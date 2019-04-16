package ro.msg.learning.shop.edm;

import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ro.msg.learning.shop.edm.OrderDetailEdmProvider.ASSOCIATION_ORDERDETAIL_ORDER_FQNAME;
import static ro.msg.learning.shop.edm.OrderDetailEdmProvider.ASSOCIATION_ORDERDETAIL_PRODUCT_FQNAME;
import static ro.msg.learning.shop.edm.OrderEdmProvider.ASSOCIATION_ORDER_CUSTOMER_FQNAME;
import static ro.msg.learning.shop.edm.OrderEdmProvider.ASSOCIATION_ORDER_LOCATION_FQNAME;
import static ro.msg.learning.shop.edm.ProductEdmProvider.*;

@RequiredArgsConstructor
@Component
public class ShopEdmProvider extends EdmProvider {
    static final String NAMESPACE = "ro.msg.learning.shop";
    private static final String ENTITY_CONTAINER = "ShopEntityContainer";

    private final OrderEdmProvider orderEdmProvider;
    private final OrderDetailEdmProvider orderDetailEdmProvider;
    private final ProductEdmProvider productEdmProvider;

    @Override
    public EntityType getEntityType(FullQualifiedName edmFQName) {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (edmFQName.getName().equals(OrderEdmProvider.ENTITY_TYPE_FQNAME.getName())) {
                return orderEdmProvider.getEntityType();
            } else if (edmFQName.getName().equals(OrderDetailEdmProvider.ENTITY_TYPE_FQNAME.getName())) {
                return orderDetailEdmProvider.getEntityType();
            } else if (edmFQName.getName().equals(ProductEdmProvider.ENTITY_TYPE_FQNAME.getName())) {
                return productEdmProvider.getEntityType();
            }
        }
        return null;
    }

    @Override
    public ComplexType getComplexType(FullQualifiedName edmFQName) {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (edmFQName.getName().equals(OrderEdmProvider.COMPLEX_TYPE_ADDRESS.getName())) {
                return orderEdmProvider.getComplexType();
            } else if (edmFQName.getName().equals(OrderDetailEdmProvider.COMPLEX_TYPE_ORDERDETAILID.getName())) {
                return orderDetailEdmProvider.getComplexType();
            }
        }
        return null;
    }

    @Override
    public Association getAssociation(FullQualifiedName edmFQName) {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            switch (edmFQName.getName()) {
                case OrderEdmProvider.RELATIONSHIP_ORDER_CUSTOMER:
                    return orderEdmProvider.getAssociation(
                            OrderEdmProvider.RELATIONSHIP_ORDER_CUSTOMER,
                            orderEdmProvider.getAssociationEnd(
                                    OrderEdmProvider.ENTITY_TYPE_FQNAME,
                                    OrderEdmProvider.ROLE_ORDER_CUSTOMER,
                                    EdmMultiplicity.MANY
                            ),
                            orderEdmProvider.getAssociationEnd(
                                    OrderEdmProvider.ENTITY_TYPE_CUSTOMER_FQNAME,
                                    OrderEdmProvider.ROLE_CUSTOMER_ORDERS,
                                    EdmMultiplicity.ONE
                            )
                    );

                case OrderEdmProvider.RELATIONSHIP_ORDER_LOCATION:
                    return orderEdmProvider.getAssociation(
                            OrderEdmProvider.RELATIONSHIP_ORDER_LOCATION,
                            orderEdmProvider.getAssociationEnd(
                                    OrderEdmProvider.ENTITY_TYPE_FQNAME,
                                    OrderEdmProvider.ROLE_ORDER_LOCATION,
                                    EdmMultiplicity.MANY
                            ),
                            orderEdmProvider.getAssociationEnd(
                                    OrderEdmProvider.ENTITY_TYPE_LOCATION_FQNAME,
                                    OrderEdmProvider.ROLE_LOCATION_ORDERS,
                                    EdmMultiplicity.ONE
                            )
                    );

                case OrderDetailEdmProvider.RELATIONSHIP_ORDERDETAIL_ORDER:
                    return orderDetailEdmProvider.getAssociation(
                            OrderDetailEdmProvider.RELATIONSHIP_ORDERDETAIL_ORDER,
                            orderDetailEdmProvider.getAssociationEnd(
                                    OrderDetailEdmProvider.ENTITY_TYPE_FQNAME,
                                    OrderDetailEdmProvider.ROLE_ORDERDETAIL_ORDER,
                                    EdmMultiplicity.MANY
                            ),
                            orderDetailEdmProvider.getAssociationEnd(
                                    OrderDetailEdmProvider.ENTITY_TYPE_ORDER_FQNAME,
                                    OrderDetailEdmProvider.ROLE_ORDER_ORDERDETAILS,
                                    EdmMultiplicity.ONE
                            )
                    );

                case OrderDetailEdmProvider.RELATIONSHIP_ORDERDETAIL_PRODUCT:
                    return orderDetailEdmProvider.getAssociation(
                            OrderDetailEdmProvider.RELATIONSHIP_ORDERDETAIL_PRODUCT,
                            orderDetailEdmProvider.getAssociationEnd(
                                    OrderDetailEdmProvider.ENTITY_TYPE_FQNAME,
                                    OrderDetailEdmProvider.ROLE_ORDERDETAIL_PRODUCT,
                                    EdmMultiplicity.MANY
                            ),
                            orderDetailEdmProvider.getAssociationEnd(
                                    OrderDetailEdmProvider.ENTITY_TYPE_PRODUCT_FQNAME,
                                    OrderDetailEdmProvider.ROLE_PRODUCT_ORDERDETAILS,
                                    EdmMultiplicity.ONE
                            )
                    );

                case ProductEdmProvider.RELATIONSHIP_PRODUCT_CATEGORY:
                    return productEdmProvider.getAssociation(
                            ProductEdmProvider.RELATIONSHIP_PRODUCT_CATEGORY,
                            productEdmProvider.getAssociationEnd(
                                    ProductEdmProvider.ENTITY_TYPE_FQNAME,
                                    ProductEdmProvider.ROLE_PRODUCT_CATEGORY,
                                    EdmMultiplicity.MANY
                            ),
                            productEdmProvider.getAssociationEnd(
                                    ProductEdmProvider.ENTITY_TYPE_CATEGORY_FQNAME,
                                    ProductEdmProvider.ROLE_CATEGORY_PRODUCTS,
                                    EdmMultiplicity.ONE
                            )
                    );

                case ProductEdmProvider.RELATIONSHIP_PRODUCT_STOCK:
                    return productEdmProvider.getAssociation(
                            ProductEdmProvider.RELATIONSHIP_PRODUCT_STOCK,
                            productEdmProvider.getAssociationEnd(
                                    ProductEdmProvider.ENTITY_TYPE_STOCK_FQNAME,
                                    ProductEdmProvider.ROLE_STOCK_PRODUCT,
                                    EdmMultiplicity.ONE
                            ),
                            productEdmProvider.getAssociationEnd(
                                    ProductEdmProvider.ENTITY_TYPE_FQNAME,
                                    ProductEdmProvider.ROLE_PRODUCT_STOCKS,
                                    EdmMultiplicity.MANY
                            )

                    );

                case ProductEdmProvider.RELATIONSHIP_PRODUCT_SUPPLIER:
                    return productEdmProvider.getAssociation(
                            ProductEdmProvider.RELATIONSHIP_PRODUCT_SUPPLIER,
                            productEdmProvider.getAssociationEnd(
                                    ProductEdmProvider.ENTITY_TYPE_FQNAME,
                                    ProductEdmProvider.ROLE_PRODUCT_SUPPLIER,
                                    EdmMultiplicity.MANY
                            ),
                            productEdmProvider.getAssociationEnd(
                                    ProductEdmProvider.ENTITY_TYPE_SUPPLIER_FQNAME,
                                    ProductEdmProvider.ROLE_SUPPLIER_PRODUCTS,
                                    EdmMultiplicity.ONE
                            )
                    );
                default:
                    return null;
            }
        }
        return null;
    }

    @Override
    public AssociationSet getAssociationSet(String entityContainer, FullQualifiedName association, String sourceEntitySetName, String sourceEntitySetRole) {
        if (ENTITY_CONTAINER.equals(entityContainer)) {
            if (ASSOCIATION_ORDER_CUSTOMER_FQNAME.equals(association)) {
                return orderEdmProvider.getAssociationSet(
                        OrderEdmProvider.ASSOCIATION_SET_ORDER_CUSTOMER,
                        ASSOCIATION_ORDER_CUSTOMER_FQNAME,
                        OrderEdmProvider.ROLE_CUSTOMER_ORDERS, OrderEdmProvider.ENTITY_SET_NAME_CUSTOMERS,
                        OrderEdmProvider.ROLE_ORDER_CUSTOMER, OrderEdmProvider.ENTITY_SET_NAME
                );
            } else if (OrderEdmProvider.ASSOCIATION_ORDER_LOCATION_FQNAME.equals(association)) {
                return orderEdmProvider.getAssociationSet(
                        OrderEdmProvider.ASSOCIATION_SET_ORDER_LOCATION,
                        OrderEdmProvider.ASSOCIATION_ORDER_LOCATION_FQNAME,
                        OrderEdmProvider.ROLE_LOCATION_ORDERS, OrderEdmProvider.ENTITY_SET_NAME_LOCATIONS,
                        OrderEdmProvider.ROLE_ORDER_LOCATION, OrderEdmProvider.ENTITY_SET_NAME
                );
            } else if (ASSOCIATION_ORDERDETAIL_ORDER_FQNAME.equals(association)) {
                return orderDetailEdmProvider.getAssociationSet(
                        OrderDetailEdmProvider.ASSOCIATION_SET_ORDERDETAILS_ORDERS,
                        ASSOCIATION_ORDERDETAIL_ORDER_FQNAME,
                        OrderDetailEdmProvider.ROLE_ORDER_ORDERDETAILS, OrderEdmProvider.ENTITY_SET_NAME,
                        OrderDetailEdmProvider.ROLE_ORDERDETAIL_ORDER, OrderDetailEdmProvider.ENTITY_SET_NAME
                );
            } else if (ASSOCIATION_ORDERDETAIL_PRODUCT_FQNAME.equals(association)) {
                return orderDetailEdmProvider.getAssociationSet(
                        OrderDetailEdmProvider.ASSOCIATION_SET_ORDERDETAILS_PRODUCTS,
                        ASSOCIATION_ORDERDETAIL_PRODUCT_FQNAME,
                        OrderDetailEdmProvider.ROLE_PRODUCT_ORDERDETAILS, ProductEdmProvider.ENTITY_SET_NAME,
                        OrderDetailEdmProvider.ROLE_ORDERDETAIL_PRODUCT, OrderDetailEdmProvider.ENTITY_SET_NAME
                );
            } else if (ASSOCIATION_PRODUCT_CATEGORY_FQNAME.equals(association)) {
                return productEdmProvider.getAssociationSet(
                        ProductEdmProvider.ASSOCIATION_SET_PRODUCT_CATEGORY,
                        ASSOCIATION_PRODUCT_CATEGORY_FQNAME,
                        ProductEdmProvider.ROLE_CATEGORY_PRODUCTS, ProductEdmProvider.ENTITY_SET_NAME_CATEGORIES,
                        ProductEdmProvider.ROLE_PRODUCT_CATEGORY, ProductEdmProvider.ENTITY_SET_NAME
                );
            } else if (ASSOCIATION_PRODUCT_STOCK_FQNAME.equals(association)) {
                return productEdmProvider.getAssociationSet(
                        ProductEdmProvider.ASSOCIATION_SET_PRODUCT_STOCK,
                        ASSOCIATION_PRODUCT_STOCK_FQNAME,
                        ProductEdmProvider.ROLE_STOCK_PRODUCT, ProductEdmProvider.ENTITY_SET_NAME_STOCKS,
                        ProductEdmProvider.ROLE_PRODUCT_STOCKS, ProductEdmProvider.ENTITY_SET_NAME
                );
            } else if (ASSOCIATION_PRODUCT_SUPPLIER_FQNAME.equals(association)) {
                return productEdmProvider.getAssociationSet(
                        ProductEdmProvider.ASSOCIATION_SET_PRODUCT_SUPPLIER,
                        ASSOCIATION_PRODUCT_SUPPLIER_FQNAME,
                        ProductEdmProvider.ROLE_SUPPLIER_PRODUCTS, ProductEdmProvider.ENTITY_SET_NAME_SUPPLIERS,
                        ProductEdmProvider.ROLE_PRODUCT_SUPPLIER, ProductEdmProvider.ENTITY_SET_NAME
                );
            }
        }
        return null;
    }

    @Override
    public EntitySet getEntitySet(String entityContainer, String name) {
        if (ENTITY_CONTAINER.equals(entityContainer)) {
            switch (name) {
                case OrderEdmProvider.ENTITY_SET_NAME:
                    return orderEdmProvider.getEntitySet();
                case OrderDetailEdmProvider.ENTITY_SET_NAME:
                    return orderDetailEdmProvider.getEntitySet();
                case ProductEdmProvider.ENTITY_SET_NAME:
                    return productEdmProvider.getEntitySet();
                default:
                    return null;
            }
        }
        return null;
    }

    @Override
    public List<Schema> getSchemas() throws ODataException {
        Schema schema = new Schema();
        schema.setNamespace(NAMESPACE);

        schema.setEntityTypes(
                Arrays.asList(
                        getEntityType(OrderEdmProvider.ENTITY_TYPE_FQNAME),
                        getEntityType(OrderDetailEdmProvider.ENTITY_TYPE_FQNAME),
                        getEntityType(ProductEdmProvider.ENTITY_TYPE_FQNAME)
                )
        );

        schema.setComplexTypes(
                Arrays.asList(
                        getComplexType(OrderEdmProvider.COMPLEX_TYPE_ADDRESS),
                        getComplexType(OrderDetailEdmProvider.COMPLEX_TYPE_ORDERDETAILID)
                )
        );

        schema.setAssociations(
                Arrays.asList(
                        getAssociation(ASSOCIATION_ORDER_CUSTOMER_FQNAME),
                        getAssociation(ASSOCIATION_ORDER_LOCATION_FQNAME),
                        getAssociation(ASSOCIATION_ORDERDETAIL_ORDER_FQNAME),
                        getAssociation(ASSOCIATION_ORDERDETAIL_PRODUCT_FQNAME),
                        getAssociation(ASSOCIATION_PRODUCT_CATEGORY_FQNAME),
                        getAssociation(ASSOCIATION_PRODUCT_STOCK_FQNAME),
                        getAssociation(ASSOCIATION_PRODUCT_SUPPLIER_FQNAME)
                )
        );

        EntityContainer entityContainer = new EntityContainer();
        entityContainer.setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);
        entityContainer.setEntitySets(
                Arrays.asList(
                        getEntitySet(ENTITY_CONTAINER, OrderEdmProvider.ENTITY_SET_NAME),
                        getEntitySet(ENTITY_CONTAINER, OrderDetailEdmProvider.ENTITY_SET_NAME),
                        getEntitySet(ENTITY_CONTAINER, ProductEdmProvider.ENTITY_SET_NAME)
                )
        );

        entityContainer.setAssociationSets(
                Arrays.asList(
                        getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_ORDER_CUSTOMER_FQNAME,
                                OrderEdmProvider.ROLE_CUSTOMER_ORDERS, OrderEdmProvider.ENTITY_SET_NAME_CUSTOMERS),
                        getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_ORDER_LOCATION_FQNAME,
                                OrderEdmProvider.ROLE_LOCATION_ORDERS, OrderEdmProvider.ENTITY_SET_NAME_LOCATIONS),
                        getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_ORDERDETAIL_ORDER_FQNAME,
                                OrderDetailEdmProvider.ROLE_ORDER_ORDERDETAILS, OrderEdmProvider.ENTITY_SET_NAME),
                        getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_ORDERDETAIL_PRODUCT_FQNAME,
                                OrderDetailEdmProvider.ROLE_PRODUCT_ORDERDETAILS, ProductEdmProvider.ENTITY_SET_NAME),
                        getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_PRODUCT_CATEGORY_FQNAME,
                                ProductEdmProvider.ROLE_CATEGORY_PRODUCTS, ProductEdmProvider.ENTITY_SET_NAME_CATEGORIES),
                        getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_PRODUCT_STOCK_FQNAME,
                                ProductEdmProvider.ROLE_STOCK_PRODUCT, ProductEdmProvider.ENTITY_SET_NAME_STOCKS),
                        getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_PRODUCT_SUPPLIER_FQNAME,
                                ProductEdmProvider.ROLE_SUPPLIER_PRODUCTS, ProductEdmProvider.ENTITY_SET_NAME_SUPPLIERS)
                )
        );

        schema.setEntityContainers(Collections.singletonList(entityContainer));
        return super.getSchemas();
    }
}
