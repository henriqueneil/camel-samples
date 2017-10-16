package com.henriqueneil.camel.samples.transaction.persistence;

import com.henriqueneil.camel.samples.transaction.bean.Person;
import org.apache.camel.BeanInject;
import org.hibernate.SessionFactory;

/**
 * @author Henrique Neil
 */
public class PersonPersistence {
    
    @BeanInject
    private SessionFactory sessionFactory;
    
    public Person createPerson(Person person) throws Exception {
        sessionFactory.getCurrentSession().persist(person);
        return person;
    }
    
    public Person updatePerson(Person person) throws Exception {
        return (Person) sessionFactory.getCurrentSession().merge(person);
    }
    
    public Person deletePerson(Person person) throws Exception {
        sessionFactory.getCurrentSession().delete(person);
        return person;
    }
}
