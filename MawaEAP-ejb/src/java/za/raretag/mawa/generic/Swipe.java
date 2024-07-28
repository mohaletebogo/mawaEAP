/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.math.BigDecimal;

/**
 *
 * @author tebogom
 */
public class Swipe {
    
    private String cardNo;

    /**
     * Get the value of cardNo
     *
     * @return the value of cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * Set the value of cardNo
     *
     * @param cardNo new value of cardNo
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private String supplier;

    /**
     * Get the value of supplier
     *
     * @return the value of supplier
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * Set the value of supplier
     *
     * @param supplier new value of supplier
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    private BigDecimal amount;

    /**
     * Get the value of amount
     *
     * @return the value of amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Set the value of amount
     *
     * @param amount new value of amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
