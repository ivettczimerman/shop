package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
@RequiredArgsConstructor
public class JpaServiceFactory extends ODataJPAServiceFactory {
    private static final String PUNIT_NAME = "local";
    private final LocalContainerEntityManagerFactoryBean factory;

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {

        ODataJPAContext context = this.getODataJPAContext();
        context.setEntityManagerFactory(factory.getObject());
        context.setPersistenceUnitName("local");
        return null;
    }
}
