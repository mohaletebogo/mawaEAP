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
@Table(name = "transaction_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionStatus.findAll", query = "SELECT t FROM TransactionStatus t"),
    @NamedQuery(name = "TransactionStatus.findByTransactionNo", query = "SELECT t FROM TransactionStatus t WHERE t.transactionStatusPK.transactionNo = :transactionNo"),
    @NamedQuery(name = "TransactionStatus.findByStatus", query = "SELECT t FROM TransactionStatus t WHERE t.transactionStatusPK.status = :status"),
    @NamedQuery(name = "TransactionStatus.findByStatusDate", query = "SELECT t FROM TransactionStatus t WHERE t.statusDate = :statusDate"),
    @NamedQuery(name = "TransactionStatus.findByActive", query = "SELECT t FROM TransactionStatus t WHERE t.active = :active"),
    @NamedQuery(name = "TransactionStatus.findByCreatedBy", query = "SELECT t FROM TransactionStatus t WHERE t.createdBy = :createdBy")})
public class TransactionStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionStatusPK transactionStatusPK;
    @Column(name = "status_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDate;
    @Size(max = 1)
    @Column(name = "active")
    private String active;
    @Size(max = 10)
    @Column(name = "created_by")
    private String createdBy;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaction transaction;
    @JoinColumn(name = "status", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigStatusType configStatusType;

    public TransactionStatus() {
    }

    public TransactionStatus(TransactionStatusPK transactionStatusPK) {
        this.transactionStatusPK = transactionStatusPK;
    }

    public TransactionStatus(String transactionNo, String status) {
        this.transactionStatusPK = new TransactionStatusPK(transactionNo, status);
    }

    public TransactionStatusPK getTransactionStatusPK() {
        return transactionStatusPK;
    }

    public void setTransactionStatusPK(TransactionStatusPK transactionStatusPK) {
        this.transactionStatusPK = transactionStatusPK;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ConfigStatusType getConfigStatusType() {
        return configStatusType;
    }

    public void setConfigStatusType(ConfigStatusType configStatusType) {
        this.configStatusType = configStatusType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionStatusPK != null ? transactionStatusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionStatus)) {
            return false;
        }
        TransactionStatus other = (TransactionStatus) object;
        if ((this.transactionStatusPK == null && other.transactionStatusPK != null) || (this.transactionStatusPK != null && !this.transactionStatusPK.equals(other.transactionStatusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionStatus[ transactionStatusPK=" + transactionStatusPK + " ]";
    }
    
}
