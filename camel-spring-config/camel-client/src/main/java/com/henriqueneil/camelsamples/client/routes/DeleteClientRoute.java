package com.henriqueneil.camelsamples.client.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Henrique Neil
 */
@Component
public class DeleteClientRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:delete-client")
                .routeId("deleteClientRoute")
                .log("This is the delete client route...")
                .to("mock:delete-client");
    }
}
