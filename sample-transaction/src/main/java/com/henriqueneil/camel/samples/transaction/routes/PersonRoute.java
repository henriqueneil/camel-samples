package com.henriqueneil.camel.samples.transaction.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriqueneil.camel.samples.transaction.bean.Person;
import com.henriqueneil.camel.samples.transaction.persistence.PersonPersistence;
import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;

/**
 * @author Henrique Neil
 */
@Component
public class PersonRoute extends RouteBuilder {
    
    @BeanInject
    private PersonPersistence personPersistence;
    
    private JacksonDataFormat json = new JacksonDataFormat(new ObjectMapper(), Person.class);
    
    @Override
    public void configure() throws Exception {
        
        onException(Exception.class)
                .log(ERROR, "An exception during has happened and the message will be placed in the DLQ.\n${exception}")
                .to("{{camel.sample.transactional.route.dlq.queue}}")
                .markRollbackOnly();
        
        from("{{camel.sample.transactional.route.input.queue}}")
                .routeId("TransactionRoute")
                .transacted()
                .log(INFO, "Starting the transactional route with body [${body}].")
                .unmarshal(json)
                .choice()
                    .when(header("action").isEqualTo("Create"))
                        .log(INFO, "It will create a Person with id [${body.id}]")
                        .bean(personPersistence, "createPerson")
                    .when(header("action").isEqualTo("Create"))
                        .log(INFO, "It will update a Person with id [${body.id}]")
                        .bean(personPersistence, "updatePerson")
                    .when(header("action").isEqualTo("Delete"))
                        .log(INFO, "It will delete a Person with id [${body.id}]")
                        .bean(personPersistence, "deletePerson")
                    .otherwise()
                        .log(INFO, "The action [${headers.action}] is not defined, nothing can be done here.")
                        .throwException(new Exception("Action [${headers.action}] not defined."))
                    .end()
                .end()
                .log(INFO, "Message with action [${headers.action}] was successfully processed.")
                .marshal(json)
                .to("{{camel.sample.transactional.route.output.queue}}");
    }
}
