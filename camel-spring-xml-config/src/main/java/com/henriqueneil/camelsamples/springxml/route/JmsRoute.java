package com.henriqueneil.camelsamples.springxml.route;

import com.henriqueneil.camelsamples.springxml.bean.SomeBean;
import org.apache.camel.BeanInject;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.INFO;

/**
 * @author Henrique Neil
 */
@Component
public class JmsRoute extends SpringRouteBuilder {

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
