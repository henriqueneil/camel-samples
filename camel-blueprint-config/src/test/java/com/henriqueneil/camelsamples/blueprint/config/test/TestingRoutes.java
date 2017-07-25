package com.henriqueneil.camelsamples.blueprint.config.test;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Properties;

import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;

/**
 * @author Henrique Neil
 */
public class TestingRoutes extends CamelBlueprintTestSupport {
    
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    
    @EndpointInject(uri = "amq:input.queue")
    private Endpoint amqEndpoint;
    @EndpointInject(uri = "mock:final.destination")
    private MockEndpoint finalDestination;
    
    private File inputDirectory;

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/blueprint.xml";
    }

    @Override
    protected Properties useOverridePropertiesWithPropertiesComponent() {
        try {
            inputDirectory = temporaryFolder.newFolder("input-folder");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Properties properties = new Properties();
        properties.put("file.directory", inputDirectory.getPath());
        properties.put("input.queue", "amq:input.queue");
        
        return properties;
    }
    
    @Test
    public void test() throws Exception {
        
        write(get(inputDirectory.getPath(), "test_file.txt"), 
                asList("This is the Content", "Second Line"), Charset.forName("UTF-8"));
        finalDestination.expectedMessageCount(1);
        
        assertMockEndpointsSatisfied();
        String response = finalDestination.getExchanges().get(0).getIn().getBody(String.class);
        Assert.assertEquals("The body has been set after passing SomeBean.", response);
    }
}
