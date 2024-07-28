/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import za.raretag.mawa.entities.CheckOut;

/**
 *
 * @author tebogom
 */
public class Cashup {

    public Cashup(JSONObject object) {
        try {
        //    this.terminalID = (object.getString("terminalID"));
            this.salesArea = (object.getString("salesArea"));
            this.employeeResponsible = (object.getString("employeeResponsible"));
            this.receiptFrom = (object.getString("receiptFrom"));
            this.receiptTo = (object.getString("receiptTo"));
            this.cashAmount = (object.getString("cashAmount"));
            this.depositAmount = ("0");
            JSONArray jsonArray = object.getJSONArray("deposits");
            for(int i=0;i<jsonArray.length();i++){
              JSONObject depositObject =  jsonArray.getJSONObject(i);
              Deposit deposit = new Deposit(depositObject);   
              this.deposits.add(deposit);
            }
            
        } catch (JSONException ex) {
            Logger.getLogger(Cashup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cashup(CheckOut checkout) {
        this.checkingId = checkout.getCheckingId();
        this.salesArea = checkout.getSalesArea().getDescription();
        this.employeeResponsible = Conversion.getPartnerName(checkout.getReceivedBy());
        this.receiptFrom = checkout.getReceiptFrom();
        this.receiptTo = checkout.getReceiptTo();
        this.cashAmount = checkout.getCashAmount().toString();
        this.createdBy =  Conversion.getPartnerName(checkout.getCreatedBy());
        this.createdOn = Conversion.dateToString(checkout.getCreatedAt());
    }
    
    private String checkingId;

    /**
     * Get the value of checkingId
     *
     * @return the value of checkingId
     */
    public String getCheckingId() {
        return checkingId;
    }

    /**
     * Set the value of checkingId
     *
     * @param checkingId new value of checkingId
     */
    public void setCheckingId(String checkingId) {
        this.checkingId = checkingId;
    }

    
    private String terminalID;

    /**
     * Get the value of terminalID
     *
     * @return the value of terminalID
     */
    public String getTerminalID() {
        return terminalID;
    }

    /**
     * Set the value of terminalID
     *
     * @param terminalID new value of terminalID
     */
    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    private String salesArea;

    /**
     * Get the value of salesArea
     *
     * @return the value of salesArea
     */
    public String getSalesArea() {
        return salesArea;
    }

    /**
     * Set the value of salesArea
     *
     * @param salesArea new value of salesArea
     */
    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    private String employeeResponsible;

    /**
     * Get the value of employeeResponsible
     *
     * @return the value of employeeResponsible
     */
    public String getEmployeeResponsible() {
        return employeeResponsible;
    }

    /**
     * Set the value of employeeResponsible
     *
     * @param employeeResponsible new value of employeeResponsible
     */
    public void setEmployeeResponsible(String employeeResponsible) {
        this.employeeResponsible = employeeResponsible;
    }

    private String receiptFrom;

    /**
     * Get the value of receiptFrom
     *
     * @return the value of receiptFrom
     */
    public String getReceiptFrom() {
        return receiptFrom;
    }

    /**
     * Set the value of receiptFrom
     *
     * @param receiptFrom new value of receiptFrom
     */
    public void setReceiptFrom(String receiptFrom) {
        this.receiptFrom = receiptFrom;
    }

    private String receiptTo;

    /**
     * Get the value of receiptTo
     *
     * @return the value of receiptTo
     */
    public String getReceiptTo() {
        return receiptTo;
    }

    /**
     * Set the value of receiptTo
     *
     * @param receiptTo new value of receiptTo
     */
    public void setReceiptTo(String receiptTo) {
        this.receiptTo = receiptTo;
    }

    private String cashAmount;

    /**
     * Get the value of cashAmount
     *
     * @return the value of cashAmount
     */
    public String getCashAmount() {
        return cashAmount;
    }

    /**
     * Set the value of cashAmount
     *
     * @param cashAmount new value of cashAmount
     */
    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    private String depositAmount;

    /**
     * Get the value of depositAmount
     *
     * @return the value of depositAmount
     */
    public String getDepositAmount() {
        return depositAmount;
    }

    /**
     * Set the value of depositAmount
     *
     * @param depositAmount new value of depositAmount
     */
    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

      private List<Deposit> deposits = new ArrayList<>();

    /**
     * Get the value of deposits
     *
     * @return the value of deposits
     */
    public List<Deposit> getDeposits() {
        return deposits;
    }

    /**
     * Set the value of deposits
     *
     * @param deposits new value of deposits
     */
    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }
  
        private String createdBy;

    /**
     * Get the value of createdBy
     *
     * @return the value of createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set the value of createdBy
     *
     * @param createdBy new value of createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private String createdOn;

    /**
     * Get the value of createdOn
     *
     * @return the value of createdOn
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     * Set the value of createdOn
     *
     * @param createdOn new value of createdOn
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
