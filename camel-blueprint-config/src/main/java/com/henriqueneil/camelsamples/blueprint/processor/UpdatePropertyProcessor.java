package com.henriqueneil.camelsamples.blueprint.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static java.lang.String.format;

/**
 * @author Henrique Neil
 */
public class UpdatePropertyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String myProperty = exchange.getProperty("myProperty", String.class);
        exchange.getIn().setHeader("FileNameText", format("The property value is [%s]", myProperty));
        exchange.getIn().setBody(myProperty);
    }
}
