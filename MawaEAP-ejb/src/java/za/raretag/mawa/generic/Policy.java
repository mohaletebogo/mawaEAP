/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionDate;
import za.raretag.mawa.entities.TransactionItem;
import za.raretag.mawa.entities.TransactionLocation;
import za.raretag.mawa.entities.TransactionPartner;
import za.raretag.mawa.entities.TransactionPayment;
import za.raretag.mawa.entities.TransactionRelation;

/**
 *
 * @author tebogom
 */
public class Policy {

    public Policy() {

    }

    public Policy(Transaction transaction) {
        this.policyNo = transaction.getTransactionNo();
        this.status = transaction.getStatus().getDescription();
        this.statusDate = Conversion.dateToString(transaction.getStatusDate());
        this.dependents = new ArrayList<>();
        for (TransactionPartner trxPartner : transaction.getTransactionPartnerCollection()) {
            if (trxPartner.getConfigPartnerFunction().getId().equals("MAINMEMBER")) {
                this.customer = Conversion.getPartnerName(trxPartner.getPartner());
            }
            if (trxPartner.getConfigPartnerFunction().getId().equals("SALESREPRESENTATIVE")) {
                this.salesRepresentative = Conversion.getPartnerName(trxPartner.getPartner());
            }
            if (trxPartner.getConfigPartnerFunction().getId().equals("DEPENDENT")) {
                Dependent dependent = new Dependent(trxPartner);
                this.dependents.add(dependent);
            }
        }
        for (TransactionPayment trxPayment : transaction.getTransactionPaymentCollection()) {
            Payment payment = new Payment();
            payment.setPaymentDate(Conversion.dateToString(trxPayment.getPaymentDate()));
            payment.setExtReceiptNo(trxPayment.getExternalReceiptNo());
            payment.setProcessedBy(Conversion.getPartnerName(trxPayment.getCreatedBy()));
            payment.setReceivedBy(Conversion.getPartnerName(trxPayment.getReceivedBy()));
            if (trxPayment.getPaymentType() != null) {
                payment.setPaymentType(trxPayment.getPaymentType().getDescription());
            }

            payment.setAmount(trxPayment.getAmount().toString());
            this.payments.add(payment);
        }

        for (TransactionLocation trxLocation : transaction.getTransactionLocationCollection()) {
            if (trxLocation.getConfigLocationType().getId().equals("SALESAREA")) {
                this.salesArea = trxLocation.getConfigSalesArea().getDescription();
            }
        }

        for (TransactionItem trxItem : transaction.getTransactionItemCollection()) {
            if (trxItem.getValidTo().after(new Date())) {
                this.productCode = trxItem.getProductId().getProductCode();
                this.productDescription = trxItem.getProductId().getDescription();
                if (trxItem.getUnitPrice() != null) {
                    this.premium = trxItem.getUnitPrice().toString();
                }
                break;
            }
        }
        
          for (TransactionRelation trxRelation : transaction.getTransactionRelationCollection()) {
            if ("CLAIM".equals(trxRelation.getTransaction1().getTransactionType().getId())) {
               Claim claim = new Claim();
               claim.setClaimNo(trxRelation.getTransaction1().getTransactionNo());
               
               this.claims.add(claim);
            }
        }

        for (TransactionDate trxDate : transaction.getTransactionDateCollection()) {
            if (trxDate.getConfigDateType().getId().equals("DATEJOINED")) {
                this.dateJoined = Conversion.dateToString(trxDate.getDateValue());

            }

            if (trxDate.getConfigDateType().getId().equals("DATEEFFECTIVE")) {
                this.dateEffective = Conversion.dateToString(trxDate.getDateValue());

            }
        }

    }

    private String policyNo = "Not Set";

    /**
     * Get the value of policyNo
     *
     * @return the value of policyNo
     */
    public String getPolicyNo() {
        return policyNo;
    }

    /**
     * Set the value of policyNo
     *
     * @param policyNo new value of policyNo
     */
    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    private String customer = "Not Set";

    /**
     * Get the value of customer
     *
     * @return the value of customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Set the value of customerNo
     *
     * @param customer new value of customer
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    private String salesRepresentative = "Not Set";

    /**
     * Get the value of salesRepresentative
     *
     * @return the value of salesRepresentative
     */
    public String getSalesRepresentative() {
        return salesRepresentative;
    }

    /**
     * Set the value of salesRepresentative
     *
     * @param salesRepresentative new value of salesRepresentative
     */
    public void setSalesRepresentative(String salesRepresentative) {
        this.salesRepresentative = salesRepresentative;
    }

    private String salesArea = "Not Set";

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

    private String productCode = "Not Set";

    /**
     * Get the value of productCode
     *
     * @return the value of productCode
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Set the value of productCode
     *
     * @param productCode new value of productCode
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    private String productDescription;

    /**
     * Get the value of productDescription
     *
     * @return the value of productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Set the value of productDescription
     *
     * @param productDescription new value of productDescription
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    private String premium;

    /**
     * Get the value of premium
     *
     * @return the value of premium
     */
    public String getPremium() {
        return premium;
    }

    /**
     * Set the value of premium
     *
     * @param premium new value of premium
     */
    public void setPremium(String premium) {
        this.premium = premium;
    }

    private String dateJoined = "Not Set";

    /**
     * Get the value of DateJoined
     *
     * @return the value of DateJoined
     */
    public String getDateJoined() {
        return dateJoined;
    }

    /**
     * Set the value of DateJoined
     *
     * @param dateJoined new value of dateJoined
     */
    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    private String dateEffective = "Not Set";

    /**
     * Get the value of DateEffective
     *
     * @return the value of DateEffective
     */
    public String getDateEffective() {
        return dateEffective;
    }

    /**
     * Set the value of DateEffective
     *
     * @param dateEffective new value of dateEffective
     */
    public void setDateEffective(String dateEffective) {
        this.dateEffective = dateEffective;
    }

    private String status = "Not Set";

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    private String statusDate = "Not Set";

    /**
     * Get the value of statusDate
     *
     * @return the value of statusDate
     */
    public String getStatusDate() {
        return statusDate;
    }

    /**
     * Set the value of statusDate
     *
     * @param statusDate new value of statusDate
     */
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    private List<Dependent> dependents = new ArrayList<>();

    /**
     * Get the value of dependents
     *
     * @return the value of dependents
     */
    public List<Dependent> getDependents() {
        return dependents;
    }

    /**
     * Set the value of dependents
     *
     * @param dependents new value of dependents
     */
    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
    }

    private List<Payment> payments = new ArrayList<>();

    /**
     * Get the value of payments
     *
     * @return the value of payments
     */
    public List<Payment> getPayments() {
        return payments;
    }

    /**
     * Set the value of payments
     *
     * @param payments new value of payments
     */
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    private List<Claim> claims;

    /**
     * Get the value of claims
     *
     * @return the value of claims
     */
    public List<Claim> getClaims() {
        return claims;
    }

    /**
     * Set the value of claims
     *
     * @param claims new value of claims
     */
    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }

}
