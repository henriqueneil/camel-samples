package com.henriqueneil.camelsamples.client.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Henrique Neil
 */
@Component
public class UpdateClientRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:update-client")
                .routeId("updateClientRoute")
                .log("This is the update client route...")
                .to("mock:update-client");
    }
}
