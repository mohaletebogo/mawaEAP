/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "transaction_amount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionAmount.findAll", query = "SELECT t FROM TransactionAmount t"),
    @NamedQuery(name = "TransactionAmount.findByAmountType", query = "SELECT t FROM TransactionAmount t WHERE t.transactionAmountPK.amountType = :amountType"),
    @NamedQuery(name = "TransactionAmount.findByTransactionNo", query = "SELECT t FROM TransactionAmount t WHERE t.transactionAmountPK.transactionNo = :transactionNo"),
    @NamedQuery(name = "TransactionAmount.findByAmount", query = "SELECT t FROM TransactionAmount t WHERE t.amount = :amount"),
    @NamedQuery(name = "TransactionAmount.findByValidFrom", query = "SELECT t FROM TransactionAmount t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionAmount.findByValidTo", query = "SELECT t FROM TransactionAmount t WHERE t.validTo = :validTo")})
public class TransactionAmount implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionAmountPK transactionAmountPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaction transaction;
    @JoinColumn(name = "amount_type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigAmountType configAmountType;

    public TransactionAmount() {
    }

    public TransactionAmount(TransactionAmountPK transactionAmountPK) {
        this.transactionAmountPK = transactionAmountPK;
    }

    public TransactionAmount(String amountType, String transactionNo) {
        this.transactionAmountPK = new TransactionAmountPK(amountType, transactionNo);
    }

    public TransactionAmountPK getTransactionAmountPK() {
        return transactionAmountPK;
    }

    public void setTransactionAmountPK(TransactionAmountPK transactionAmountPK) {
        this.transactionAmountPK = transactionAmountPK;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ConfigAmountType getConfigAmountType() {
        return configAmountType;
    }

    public void setConfigAmountType(ConfigAmountType configAmountType) {
        this.configAmountType = configAmountType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionAmountPK != null ? transactionAmountPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionAmount)) {
            return false;
        }
        TransactionAmount other = (TransactionAmount) object;
        if ((this.transactionAmountPK == null && other.transactionAmountPK != null) || (this.transactionAmountPK != null && !this.transactionAmountPK.equals(other.transactionAmountPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionAmount[ transactionAmountPK=" + transactionAmountPK + " ]";
    }
    
}
