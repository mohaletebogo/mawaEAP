/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import za.raretag.mawa.entities.*;

/**
 *
 * @author tebogom
 */
public final class Dependent extends Customer implements Serializable{

    public Dependent() {
    }
    
    public Dependent(TransactionPartner partner) {
        this.setPolicyNo(partner.getTransaction().getTransactionNo());
        this.setId(partner.getPartner().getPartnerNo());
        this.setLastName(partner.getPartner().getName1());
        this.setMiddleName(partner.getPartner().getName3());
        this.setFirstName(partner.getPartner().getName2());
        this.setDateAdded(Conversion.dateToString(partner.getDateAdded()));
        this.setDateEffective(Conversion.dateToString(partner.getDateEffective()));
        this.setStatusDate(Conversion.dateToString(partner.getStatusDate()));
        this.setStatus(partner.getStatus());        
        this.setStatusReason(partner.getStatusReason());       
        for (PartnerIdentity ident: partner.getPartner().getPartnerIdentityCollection()){
            this.setIdNumber(ident.getPartnerIdentityPK().getIdNumber());
            break;
        }

    }

    private String policyNo  = "Not Set";

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

    private String dateAdded  = "Not Set";

    /**
     * Get the value of dateAdded
     *
     * @return the value of dateAdded
     */
    public String getDateAdded() {
        return dateAdded;
    }

    /**
     * Set the value of dateAdded
     *
     * @param dateAdded new value of dateAdded
     */
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    private String dateEffective  = "Not Set";

    /**
     * Get the value of dateEffective
     *
     * @return the value of dateEffective
     */
    public String getDateEffective() {
        return dateEffective;
    }

    /**
     * Set the value of dateEffective
     *
     * @param dateEffective new value of dateEffective
     */
    public void setDateEffective(String dateEffective) {
        this.dateEffective = dateEffective;
    }

    private String status  = "Not Set";

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

    private String creationDate  = "Not Set";

    /**
     * Get the value of creationDate
     *
     * @return the value of creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Set the value of creationDate
     *
     * @param creationDate new value of creationDate
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    private String statusDate  = "Not Set";

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

    private String tombRecipient  = "Not Set";

    /**
     * Get the value of tombRecipient
     *
     * @return the value of tombRecipient
     */
    public String getTombRecipient() {
        return tombRecipient;
    }

    /**
     * Set the value of tombRecipient
     *
     * @param tombRecipient new value of tombRecipient
     */
    public void setTombRecipient(String tombRecipient) {
        this.tombRecipient = tombRecipient;
    }

    private String statusReason  = "Not Set";

    /**
     * Get the value of statusReason
     *
     * @return the value of statusReason
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * Set the value of statusReason
     *
     * @param statusReason new value of statusReason
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

}
