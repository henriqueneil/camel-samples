package com.henriqueneil.camelsamples.client.config;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Henrique Neil
 */
@Configuration
@ComponentScan(basePackages = "com.henriqueneil.camelsamples.client.routes")
public class RoutesConfig extends CamelConfiguration {

}
