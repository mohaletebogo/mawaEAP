/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author tebogom
 */
public class Deposit {

    public Deposit(JSONObject object) {
        try {
            this.reference = (object.getString("reference"));
            this.depositDate = (Conversion.stringToDate(object.getString("date")));
            this.amount = (Conversion.stringToBigDecimal(object.getString("amount")));
        } catch (JSONException ex) {
            Logger.getLogger(Deposit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Date depositDate;

    /**
     * Get the value of depositDate
     *
     * @return the value of depositDate
     */
    public Date getDepositDate() {
        return depositDate;
    }

    /**
     * Set the value of depositDate
     *
     * @param depositDate new value of depositDate
     */
    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    private String reference;

    /**
     * Get the value of reference
     *
     * @return the value of reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Set the value of reference
     *
     * @param reference new value of reference
     */
    public void setReference(String reference) {
        this.reference = reference;
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
