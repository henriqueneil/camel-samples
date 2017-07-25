package com.henriqueneil.camelsamples.springxml.config;

import com.henriqueneil.camelsamples.springxml.bean.SomeBean;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Henrique Neil
 */
@Component
public class Configurations {
    
    @Bean(name = "amq")
    public JmsComponent activeMqEmbedded() {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(new ActiveMQConnectionFactory("vm://127.0.0.1:61616"));
        return jmsComponent;
    }
    
    @Bean
    public SomeBean someBean() {
        return new SomeBean();
    }
}
