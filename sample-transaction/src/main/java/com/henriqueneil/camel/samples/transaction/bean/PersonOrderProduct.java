package com.henriqueneil.camel.samples.transaction.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

/**
 * @author Henrique Neil
 */
@Embeddable
@Table(name = "PERSON_ORDER_PRODUCT")
public class PersonOrderProduct {

    @Column(name = "ORDER_ID")
    private String orderId;
    
    @Column(name = "PERSON_ID")
    private String personId;
    
    @Column(name = "PRODUCT_ID")
    private String productId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
