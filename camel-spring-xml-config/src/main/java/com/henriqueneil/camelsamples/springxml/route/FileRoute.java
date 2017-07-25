package com.henriqueneil.camelsamples.springxml.route;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.INFO;

/**
 * @author Henrique Neil
 */
@Component
public class FileRoute extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("file:{{file.directory}}?noop=true")
                .routeId("FileRoute")
                .log(INFO, "The file has been loaded.")
                .to("{{input.queue}}");
    }
}
