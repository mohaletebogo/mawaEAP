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
public class Payment {
    
    private String referenceNo;

    /**
     * Get the value of referenceNo
     *
     * @return the value of referenceNo
     */
    public String getReferenceNo() {
        return referenceNo;
    }

    /**
     * Set the value of referenceNo
     *
     * @param referenceNo new value of referenceNo
     */
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    private String extReceiptNo;

    /**
     * Get the value of extReceiptNo
     *
     * @return the value of extReceiptNo
     */
    public String getExtReceiptNo() {
        return extReceiptNo;
    }

    /**
     * Set the value of extReceiptNo
     *
     * @param extReceiptNo new value of extReceiptNo
     */
    public void setExtReceiptNo(String extReceiptNo) {
        this.extReceiptNo = extReceiptNo;
    }

        private String paymentDate;

    /**
     * Get the value of paymentDate
     *
     * @return the value of paymentDate
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * Set the value of paymentDate
     *
     * @param paymentDate new value of paymentDate
     */
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
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

    private String paymentType;

    /**
     * Get the value of paymentType
     *
     * @return the value of paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Set the value of paymentType
     *
     * @param paymentType new value of paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    private String processedBy;

    /**
     * Get the value of processedBy
     *
     * @return the value of processedBy
     */
    public String getProcessedBy() {
        return processedBy;
    }

    /**
     * Set the value of processedBy
     *
     * @param processedBy new value of processedBy
     */
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    private String receivedBy;

    /**
     * Get the value of receivedBy
     *
     * @return the value of receivedBy
     */
    public String getReceivedBy() {
        return receivedBy;
    }

    /**
     * Set the value of receivedBy
     *
     * @param receivedBy new value of receivedBy
     */
    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    private String amount;

    /**
     * Get the value of amount
     *
     * @return the value of amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Set the value of amount
     *
     * @param amount new value of amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

}
