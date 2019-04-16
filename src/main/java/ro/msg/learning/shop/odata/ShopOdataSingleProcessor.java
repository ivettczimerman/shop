package ro.msg.learning.shop.odata;

import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.edm.OrderDetailEdmProvider;
import ro.msg.learning.shop.edm.OrderEdmProvider;
import ro.msg.learning.shop.edm.ProductEdmProvider;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Order;

import java.io.InputStream;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ShopOdataSingleProcessor extends ODataSingleProcessor {

    private final DataStore dataStore;

    @Override
    public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {
        if (uriInfo.getNavigationSegments().isEmpty()) {
            EdmEntitySet entitySet = uriInfo.getStartEntitySet();

            if (OrderEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
                int id = getKeyValue(uriInfo.getKeyPredicates().get(0));
                Map<String, Object> data = dataStore.getOrder(id);

                if (data != null) {
                    URI serviceRoot = getContext().getPathInfo().getServiceRoot();
                    EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder propertiesBuilder =
                            EntityProviderWriteProperties.serviceRoot(serviceRoot);

                    return EntityProvider.writeEntry(contentType, entitySet, data, propertiesBuilder.build());
                }
            } else if (ProductEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
                int id = getKeyValue(uriInfo.getKeyPredicates().get(0));
                Map<String, Object> data = dataStore.getProduct(id);

                if (data != null) {
                    URI serviceRoot = getContext().getPathInfo().getServiceRoot();
                    EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder propertiesBuilder =
                            EntityProviderWriteProperties.serviceRoot(serviceRoot);

                    return EntityProvider.writeEntry(contentType, entitySet, data, propertiesBuilder.build());
                }
            } else if (OrderDetailEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
                //TODO: Check if it works
                int orderId = getKeyValue(uriInfo.getKeyPredicates().get(0));
                int productId = getKeyValue(uriInfo.getKeyPredicates().get(1));

                Map<String, Object> id = dataStore.createOrderDetailId(orderId, productId);
                Map<String, Object> data = dataStore.getOrderDetail(id);

                if (data != null) {
                    URI serviceRoot = getContext().getPathInfo().getServiceRoot();
                    EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder propertiesBuilder =
                            EntityProviderWriteProperties.serviceRoot(serviceRoot);

                    return EntityProvider.writeEntry(contentType, entitySet, data, propertiesBuilder.build());
                }
            }
            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
        } else if (uriInfo.getNavigationSegments().size() == 1) {
            EdmEntitySet entitySet = uriInfo.getTargetEntitySet();
            List<Map<String, Object>> data = null;

            if (OrderEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
                int orderKey = getKeyValue(uriInfo.getKeyPredicates().get(0));
                data = dataStore.getOrderDetailsForOrder(orderKey);
            } else if (ProductEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
                int productKey = getKeyValue(uriInfo.getKeyPredicates().get(0));
                data = dataStore.getOrderDetailsForProduct(productKey);
            }
            if (data != null) {
                return EntityProvider.writeFeed(contentType, uriInfo.getTargetEntitySet(), data,
                        EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            }
            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
        }
        throw new ODataNotImplementedException();
    }

    @Override
    public ODataResponse readEntitySet(GetEntitySetUriInfo uriInfo, String contentType) throws ODataException {
        EdmEntitySet entitySet;

        if (uriInfo.getNavigationSegments().isEmpty()) {
            entitySet = uriInfo.getStartEntitySet();

            if (OrderEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
                return EntityProvider.writeFeed(contentType, entitySet, dataStore.getOrders(),
                        EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            } else if (ProductEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
                return EntityProvider.writeFeed(contentType, entitySet, dataStore.getProducts(),
                        EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            } else if (OrderDetailEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
                return EntityProvider.writeFeed(contentType, entitySet, dataStore.getOrderDetails(),
                        EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            }

            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
        }
        throw new ODataNotImplementedException();
    }

    @Override
    public ODataResponse createEntity(PostUriInfo uriInfo, InputStream content, String requestContentType, String contentType) throws ODataException {
        EdmEntitySet entitySet = uriInfo.getStartEntitySet();
        if (OrderEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {
            EntityProviderReadProperties properties = EntityProviderReadProperties
                    .init()
                    .mergeSemantic(false)
                    .build();
            ODataEntry entry = EntityProvider.readEntry(
                    requestContentType, uriInfo.getTargetEntitySet(), content, properties);
            Map<String, Object> data = entry.getProperties();
            Map<String, Object> orderAddress = (Map<String, Object>) data.get(OrderEdmProvider.ENTITY_PROPERTY_ADDRESS);
            Order order = new Order();
            Address address = new Address();
            address.setCountry((String) orderAddress.get(OrderEdmProvider.ADDRESS_PROPERTY_COUNTRY));
            address.setCity((String) orderAddress.get(OrderEdmProvider.ADDRESS_PROPERTY_CITY));
            address.setCounty((String) orderAddress.get(OrderEdmProvider.ADDRESS_PROPERTY_COUNTY));
            address.setStreetAddress((String) orderAddress.get(OrderEdmProvider.ADDRESS_PROPERTY_STREET_ADDRESS));
            order.setAddress(address);
            order.setCreatedOn((Timestamp) data.get(OrderEdmProvider.ENTITY_PROPERTY_CREATED_ON));

        } else if (ProductEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {

        } else if (OrderDetailEdmProvider.ENTITY_SET_NAME.equals(entitySet.getName())) {

        }

        return super.createEntity(uriInfo, content, requestContentType, contentType);
    }

    private int getKeyValue(KeyPredicate key) throws ODataException {
        EdmProperty property = key.getProperty();
        EdmSimpleType type = (EdmSimpleType) property.getType();
        return type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), Integer.class);
    }

}
