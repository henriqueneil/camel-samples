package com.henriqueneil.camelsamples.springxml.bean;

import org.springframework.stereotype.Component;

/**
 * @author Henrique Neil
 */
@Component
public class SomeBean {
    
    public String setNewBody() {
        return "The body has been set after passing SomeBean.";
    }
}
