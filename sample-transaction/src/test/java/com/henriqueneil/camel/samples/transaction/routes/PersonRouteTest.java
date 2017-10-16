package com.henriqueneil.camel.samples.transaction.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriqueneil.camel.samples.transaction.bean.Person;
import com.henriqueneil.camel.samples.transaction.config.CamelContextConfig;
import org.apache.camel.BeanInject;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Henrique Neil
 */
@ContextConfiguration(classes = {CamelContextConfig.class},
        loader = CamelSpringDelegatingTestContextLoader.class)
public class PersonRouteTest extends CamelSpringTestSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRouteTest.class);
    
    @EndpointInject(uri = "amq:input.queue")
    private Endpoint inputQueue;
    
    @BeanInject
    private DataSource dataSource;
    
    @BeanInject
    private JdbcTemplate jdbcTemplate;
    
    @Before
    public void doBefore() {
        LOGGER.info("Cleaning the database before testing.");
        jdbcTemplate.batchUpdate(
                "delete from person",
                "delete from product",
                "delete from purchase_order",
                "delete from person_order_product"
        );
        LOGGER.info("Database cleaning successfully done.");
    }
    
    @Test
    @DirtiesContext
    public void givenPerson_whenCreate_shouldCreateSuccessfully() throws Exception {

        Person person = createPerson("First Name", "Last Name", 35);
        String message = new ObjectMapper().writeValueAsString(person);
        
        NotifyBuilder notifyBuilder = new NotifyBuilder(context()).whenCompleted(1).create();
        template.sendBodyAndHeader(inputQueue, message, "action", "Create");
        
        notifyBuilder.matches(5, SECONDS);
        testRecordsCreated(person, 1);
    }

    @Test
    @DirtiesContext
    public void givenPersonWithoutLastName_whenCreate_shouldCreateSuccessfully() throws Exception {

        Person person = createPerson("First Name", "", 33);
        String message = new ObjectMapper().writeValueAsString(person);

        NotifyBuilder notifyBuilder = new NotifyBuilder(context()).whenCompleted(1).create();
        template.sendBodyAndHeader(inputQueue, message, "action", "Create");

        notifyBuilder.matches(5, SECONDS);
        testRecordsCreated(person, 1);
    }

    @Test
    @DirtiesContext
    public void givenPersonWithoutAge_whenCreate_shouldCreateSuccessfully() throws Exception {

        Person person = createPerson("First Name", "Last Name", 0);
        String message = new ObjectMapper().writeValueAsString(person);

        NotifyBuilder notifyBuilder = new NotifyBuilder(context()).whenCompleted(1).create();
        template.sendBodyAndHeader(inputQueue, message, "action", "Create");

        notifyBuilder.matches(5, SECONDS);
        testRecordsCreated(person, 1);
    }

    @Test
    @DirtiesContext
    public void givenPersonWithNameOnly_whenCreate_shouldCreateSuccessfully() throws Exception {

        Person person = createPerson("First Name", "", 0);
        String message = new ObjectMapper().writeValueAsString(person);

        NotifyBuilder notifyBuilder = new NotifyBuilder(context()).whenCompleted(1).create();
        template.sendBodyAndHeader(inputQueue, message, "action", "Create");

        notifyBuilder.matches(5, SECONDS);
        testRecordsCreated(person, 1);
    }

    private void testRecordsCreated(Person person, Integer expectedNumRecords) throws Exception {
        
        String sql = "select count(person_id) as num_records, first_name, last_name, age from person where person_id = ?";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, person.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    
                    Integer numRecords = resultSet.getInt("num_records");
                    Integer age = resultSet.getInt("age");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    
                    assertEquals(expectedNumRecords, numRecords);
                    assertEquals(person.getAge(), age);
                    assertEquals(person.getFirstName(), firstName);
                    assertEquals(person.getLastName(), lastName);
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.error("An error happened while validating the result.", sqlException);
            throw new Exception(sqlException);
        }
    }
    
    private Person createPerson(String firstName, String lastName, Integer age) {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);
        return person;
    }
    
    protected AbstractApplicationContext createApplicationContext() {
        return new AnnotationConfigApplicationContext(CamelContextConfig.class);
    }
}