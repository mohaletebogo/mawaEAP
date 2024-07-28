/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

/**
 *
 * @author tebogom
 */
public class OrderItem {
    
    private String code;

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String itemDescription;

    /**
     * Get the value of itemDescription
     *
     * @return the value of itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Set the value of itemDescription
     *
     * @param itemDescription new value of itemDescription
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    private String quantity;

    /**
     * Get the value of quantity
     *
     * @return the value of quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Set the value of quantity
     *
     * @param quantity new value of quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
