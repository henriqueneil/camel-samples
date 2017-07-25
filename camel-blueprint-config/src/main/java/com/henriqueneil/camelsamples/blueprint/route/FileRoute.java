package com.henriqueneil.camelsamples.blueprint.route;

import org.apache.camel.builder.RouteBuilder;

import static org.apache.camel.LoggingLevel.INFO;

/**
 * Starting route for the test case.
 * 
 * @author Henrique Neil
 */
public class FileRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("file:{{file.directory}}?noop=true")
                .routeId("FileRoute")
                .log(INFO, "The file has been loaded.")
                .to("{{input.queue}}");
    }
}
