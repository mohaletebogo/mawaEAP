/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "transaction_partner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionPartner.findAll", query = "SELECT t FROM TransactionPartner t"),
    @NamedQuery(name = "TransactionPartner.findByPartnerType", query = "SELECT t FROM TransactionPartner t WHERE t.transactionPartnerPK.partnerType = :partnerType"),
    @NamedQuery(name = "TransactionPartner.findByTransactionNo", query = "SELECT t FROM TransactionPartner t WHERE t.transactionPartnerPK.transactionNo = :transactionNo"),
    @NamedQuery(name = "TransactionPartner.findByPartnerNo", query = "SELECT t FROM TransactionPartner t WHERE t.transactionPartnerPK.partnerNo = :partnerNo"),
    @NamedQuery(name = "TransactionPartner.findByDateAdded", query = "SELECT t FROM TransactionPartner t WHERE t.dateAdded = :dateAdded"),
    @NamedQuery(name = "TransactionPartner.findByDateEffective", query = "SELECT t FROM TransactionPartner t WHERE t.dateEffective = :dateEffective"),
    @NamedQuery(name = "TransactionPartner.findByStatus", query = "SELECT t FROM TransactionPartner t WHERE t.status = :status"),
    @NamedQuery(name = "TransactionPartner.findByStatusReason", query = "SELECT t FROM TransactionPartner t WHERE t.statusReason = :statusReason"),
    @NamedQuery(name = "TransactionPartner.findByStatusDate", query = "SELECT t FROM TransactionPartner t WHERE t.statusDate = :statusDate"),
    @NamedQuery(name = "TransactionPartner.findByValidFrom", query = "SELECT t FROM TransactionPartner t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionPartner.findByValidTo", query = "SELECT t FROM TransactionPartner t WHERE t.validTo = :validTo"),
    @NamedQuery(name = "TransactionPartner.findByCreatedBy", query = "SELECT t FROM TransactionPartner t WHERE t.createdBy = :createdBy")})
public class TransactionPartner implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionPartnerPK transactionPartnerPK;
    @Column(name = "date_added")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;
    @Column(name = "date_effective")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEffective;
    @Size(max = 20)
    @Column(name = "status")
    private String status;
    @Size(max = 20)
    @Column(name = "status_reason")
    private String statusReason;
    @Column(name = "status_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDate;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @Size(max = 10)
    @Column(name = "created_by")
    private String createdBy;
    @JoinColumn(name = "partner_type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigPartnerFunction configPartnerFunction;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaction transaction;
    @JoinColumn(name = "partner_no", referencedColumnName = "partner_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partner partner;

    public TransactionPartner() {
    }

    public TransactionPartner(TransactionPartnerPK transactionPartnerPK) {
        this.transactionPartnerPK = transactionPartnerPK;
    }

    public TransactionPartner(String partnerType, String transactionNo, String partnerNo) {
        this.transactionPartnerPK = new TransactionPartnerPK(partnerType, transactionNo, partnerNo);
    }

    public TransactionPartnerPK getTransactionPartnerPK() {
        return transactionPartnerPK;
    }

    public void setTransactionPartnerPK(TransactionPartnerPK transactionPartnerPK) {
        this.transactionPartnerPK = transactionPartnerPK;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateEffective() {
        return dateEffective;
    }

    public void setDateEffective(Date dateEffective) {
        this.dateEffective = dateEffective;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ConfigPartnerFunction getConfigPartnerFunction() {
        return configPartnerFunction;
    }

    public void setConfigPartnerFunction(ConfigPartnerFunction configPartnerFunction) {
        this.configPartnerFunction = configPartnerFunction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionPartnerPK != null ? transactionPartnerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionPartner)) {
            return false;
        }
        TransactionPartner other = (TransactionPartner) object;
        if ((this.transactionPartnerPK == null && other.transactionPartnerPK != null) || (this.transactionPartnerPK != null && !this.transactionPartnerPK.equals(other.transactionPartnerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionPartner[ transactionPartnerPK=" + transactionPartnerPK + " ]";
    }
    
}
