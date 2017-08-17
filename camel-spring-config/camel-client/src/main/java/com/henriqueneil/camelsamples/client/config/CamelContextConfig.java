package com.henriqueneil.camelsamples.client.config;

import org.ops4j.pax.cdi.api.OsgiService;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Henrique Neil
 */
@Configuration
public class CamelContextConfig {

    @OsgiService(filter = "jdbc/Service")
    private DataSource dataSource;
}
