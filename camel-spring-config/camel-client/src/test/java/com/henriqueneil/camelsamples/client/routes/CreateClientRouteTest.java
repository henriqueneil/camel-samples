package com.henriqueneil.camelsamples.client.routes;

import com.henriqueneil.camelsamples.client.config.RoutesConfig;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Henrique Neil
 */
@ContextConfiguration(classes = { RoutesConfig.class },
        loader = CamelSpringDelegatingTestContextLoader.class)
public class CreateClientRouteTest extends CamelSpringTestSupport {

    @EndpointInject (uri = "mock:create-client")
    private MockEndpoint createMock;

    @Test
    @DirtiesContext
    public void test_whenCreateClient_thenShouldReturnSuccess() throws Exception {

        createMock.expectedMessageCount(1);
        template.sendBody("direct:create-client", "Please, create a client for me.");

        createMock.assertIsSatisfied();
        assert "Please, create a client for me."
                .equals(createMock.getExchanges().get(0).getIn().getBody(String.class));
    }

    protected AbstractApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(RoutesConfig.class);
    }
}
