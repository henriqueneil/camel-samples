package com.henriqueneil.camelsamples.client.config;

import org.apache.aries.blueprint.annotation.Blueprint;
import org.ops4j.pax.cdi.api.OsgiService;

import javax.sql.DataSource;

/**
 * @author Henrique Neil
 */
@Blueprint
public class CamelContextConfig {

    @OsgiService(filter = "jdbc/Service")
    private DataSource dataSource;
}
