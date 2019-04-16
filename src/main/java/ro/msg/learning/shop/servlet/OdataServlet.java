package ro.msg.learning.shop.servlet;

import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.service.JpaServiceFactory;
import org.apache.olingo.odata2.core.servlet.ODataServlet;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
class ExampleServlet extends ODataServlet {
    private final transient ApplicationContext context;

    @Override
    protected ODataServiceFactory getServiceFactory(HttpServletRequest request) {
        return context.getBean(JpaServiceFactory.class);
    }
}
