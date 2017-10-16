package com.henriqueneil.camel.samples.transaction.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Henrique Neil
 */
@Entity
@Table(name = "PURCHASE_ODER")
public class PurchaseOrder {
    
    @Id
    @Column(name = "ORDER_ID")
    private String id;
    
    @Column(name = "ORDER_TOTAL")
    private BigDecimal orderTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }
}
