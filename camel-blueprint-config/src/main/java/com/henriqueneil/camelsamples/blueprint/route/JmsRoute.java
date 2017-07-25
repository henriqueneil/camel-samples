package com.henriqueneil.camelsamples.blueprint.route;

import com.henriqueneil.camelsamples.blueprint.bean.SomeBean;
import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;

import static org.apache.camel.LoggingLevel.INFO;

/**
 * Route that will receive a message then will use the injected bean to transform the response.
 * 
 * @author Henrique Neil
 */
public class JmsRoute extends RouteBuilder {

    /**
     * This bean must be injected by the bean instantiated in blueprint.xml file
     */
    @BeanInject
    private SomeBean someBean;
    
    @Override
    public void configure() throws Exception {
        
        from("{{input.queue}}")
                .routeId("InputQueueRoute")
                .log(INFO, "The message has been received in the Input Queue")
                .bean(someBean, "setNewBody")
                .to("mock:final.destination");
    }
}
