/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.util.Date;

/**
 *
 * @author tebogom
 */
public class OrderDate {
    
    private String type;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(String type) {
        this.type = type;
    }

    private String typeDescription;

    /**
     * Get the value of typeDescription
     *
     * @return the value of typeDescription
     */
    public String getTypeDescription() {
        return typeDescription;
    }

    /**
     * Set the value of typeDescription
     *
     * @param typeDescription new value of typeDescription
     */
    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    private Date value;

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public Date getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(Date value) {
        this.value = value;
    }

    private String orderNo;

    /**
     * Get the value of orderNo
     *
     * @return the value of orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Set the value of orderNo
     *
     * @param orderNo new value of orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
