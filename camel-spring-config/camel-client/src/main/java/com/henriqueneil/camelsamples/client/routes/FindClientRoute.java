package com.henriqueneil.camelsamples.client.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Henrique Neil
 */
@Component
public class FindClientRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:find-client")
                .routeId("findClientRoute")
                .log("This is the find client route...")
                .to("mock:find-client");
    }
}
