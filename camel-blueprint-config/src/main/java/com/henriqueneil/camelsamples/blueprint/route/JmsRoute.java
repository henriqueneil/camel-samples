package com.henriqueneil.camelsamples.blueprint.route;

import com.henriqueneil.camelsamples.blueprint.bean.FileHandlerBean;
import com.henriqueneil.camelsamples.blueprint.processor.UpdatePropertyProcessor;
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
    private FileHandlerBean fileHandlerBean;
    
    @BeanInject
    private UpdatePropertyProcessor processor;
    
    @Override
    public void configure() throws Exception {
        
        from("{{input.queue}}")
                .routeId("InputQueueRoute")
                .log(INFO, "The message has been received in the Input Queue")
                .setProperty("myProperty", constant("The body has been set after passing SomeBean."))
                .process(processor)
                .bean(fileHandlerBean, "setBodyToFileName(${body})")
                .to("mock:final.destination");
    }
}
