package com.henriqueneil.camelsamples.client.routes;

import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author Henrique Neil
 */
@Component
public class CreateClientRoute extends RouteBuilder {
    
    @BeanInject
    private DataSource dataSource;

    @Override
    public void configure() throws Exception {

        from("direct:create-client")
                .routeId("createClientRoute")
                .log("This is the create client route...")
                .to("mock:create-client");
    }
}
