package com.henriqueneil.camel.samples.transaction.config;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.camel.spring.javaconfig.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Henrique Neil
 */
@Import({BeanConfig.class})
@Configuration
@ComponentScan(basePackages = {"com.henriqueneil.camel.samples.transaction.routes"})
public class CamelContextConfig extends CamelConfiguration {
    
    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.setConfigClass(CamelConfiguration.class);
        main.run();
    }
}
