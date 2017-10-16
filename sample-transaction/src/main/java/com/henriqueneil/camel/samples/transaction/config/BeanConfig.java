package com.henriqueneil.camel.samples.transaction.config;

import com.henriqueneil.camel.samples.transaction.bean.Person;
import com.henriqueneil.camel.samples.transaction.persistence.PersonPersistence;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apacheextras.camel.component.hibernate.HibernateComponent;
import org.apacheextras.camel.component.hibernate.SpringTransactionStrategy;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * @author Henrique Neil
 */
@Configuration
public class BeanConfig {
    
    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .addScript("/sql/create-tables.sql")
                .setType(H2).build();
    }
    
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(applicationContext.getBean(DataSource.class));
    }

    @Bean(name = "amq")
    public ActiveMQComponent amq() {
        ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false&broker.useJmx=false");

        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(factory);
        return activeMQComponent;
    }
    
    @Bean(name = "properties")
    public PropertiesComponent propertiesComponent() throws Exception {
        
        Properties properties = new Properties();
        PropertiesComponent propertiesComponent = new PropertiesComponent();
        
        properties.load(new ClassPathResource("sample.transaction.properties").getInputStream());
        propertiesComponent.setOverrideProperties(properties);
        
        return propertiesComponent;
    }

    @Bean(name = "hibernate")
    public HibernateComponent hibernateComponent() throws Exception {

        HibernateComponent hibernateComponent = new HibernateComponent();
        hibernateComponent.setSessionFactory(applicationContext.getBean(SessionFactory.class));
        hibernateComponent.setTransactionStrategy(applicationContext.getBean(SpringTransactionStrategy.class));

        return hibernateComponent;
    }
    
    @Bean(name = "transactionRequired")
    public SpringTransactionStrategy transactionRequired() {
        return new SpringTransactionStrategy(applicationContext.getBean(SessionFactory.class), 
                applicationContext.getBean(TransactionTemplate.class));
    }
    
    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(applicationContext.getBean(HibernateTransactionManager.class));
    }
    
    @Bean(name = "hibernateTransactionManager")
    public HibernateTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(applicationContext.getBean(SessionFactory.class));
        return transactionManager;
    }
    
    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() throws Exception {
        Properties properties = new Properties();
        properties.load(new ClassPathResource("hibernate-session-factory.properties").getInputStream());
        DataSource dataSource = applicationContext.getBean(DataSource.class);

        return new LocalSessionFactoryBuilder(dataSource)
                .scanPackages(Person.class.getPackage().getName())
                .addProperties(properties)
                .buildSessionFactory();
    }
    
    @Bean(name = "personPersistence")
    public PersonPersistence personPersistence() {
        return new PersonPersistence();
    }
}
