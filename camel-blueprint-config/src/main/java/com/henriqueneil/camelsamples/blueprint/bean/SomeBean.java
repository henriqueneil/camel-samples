package com.henriqueneil.camelsamples.blueprint.bean;

/**
 * Bean to be injected in the route.
 * 
 * @author Henrique Neil
 */
public class SomeBean {
    
    public String setNewBody() {
        return "The body has been set after passing SomeBean.";
    }
}
