package ro.msg.learning.shop.edm;

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;

import java.util.List;

import static ro.msg.learning.shop.edm.ShopEdmProvider.NAMESPACE;

class ShopEntityEdmProvider {

    Property getProperty(String name, EdmSimpleTypeKind type) {
        return new SimpleProperty().setName(name).setType(type);
    }

    NavigationProperty getNavigationProperty(String name, String relationship, String fromRole, String toRole) {
        return new NavigationProperty()
                .setName(name)
                .setRelationship(new FullQualifiedName(NAMESPACE, relationship))
                .setFromRole(fromRole)
                .setToRole(toRole);
    }

    ComplexType getComplexType(String name, List<Property> properties) {
        return new ComplexType()
                .setName(name)
                .setProperties(properties);
    }

    Association getAssociation(String name, AssociationEnd end1, AssociationEnd end2) {
        return new Association()
                .setName(name)
                .setEnd1(end1)
                .setEnd2(end2);
    }

    AssociationEnd getAssociationEnd(FullQualifiedName type, String role, EdmMultiplicity multiplicity) {
        return new AssociationEnd()
                .setType(type)
                .setRole(role)
                .setMultiplicity(multiplicity);
    }

    EntitySet getEntitySet(String name, FullQualifiedName entityType) {
        return new EntitySet().setName(name).setEntityType(entityType);
    }

    AssociationSet getAssociationSet(String name, FullQualifiedName association, String role1,
                                     String entitySet1, String role2, String entitySet2) {
        return new AssociationSet()
                .setName(name)
                .setAssociation(association)
                .setEnd1(getAssociationSetEnd(role1, entitySet1))
                .setEnd2(getAssociationSetEnd(role2, entitySet2));
    }

    private AssociationSetEnd getAssociationSetEnd(String role, String entitySet) {
        return new AssociationSetEnd()
                .setRole(role)
                .setEntitySet(entitySet);
    }
}
