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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "transaction_date")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionDate.findAll", query = "SELECT t FROM TransactionDate t"),
    @NamedQuery(name = "TransactionDate.findByTransactionNo", query = "SELECT t FROM TransactionDate t WHERE t.transactionDatePK.transactionNo = :transactionNo"),
    @NamedQuery(name = "TransactionDate.findByDateType", query = "SELECT t FROM TransactionDate t WHERE t.transactionDatePK.dateType = :dateType"),
    @NamedQuery(name = "TransactionDate.findByDateValue", query = "SELECT t FROM TransactionDate t WHERE t.dateValue = :dateValue"),
    @NamedQuery(name = "TransactionDate.findByValidFrom", query = "SELECT t FROM TransactionDate t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionDate.findByValidTo", query = "SELECT t FROM TransactionDate t WHERE t.validTo = :validTo")})
public class TransactionDate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionDatePK transactionDatePK;
    @Column(name = "date_value")
    @Temporal(TemporalType.DATE)
    private Date dateValue;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaction transaction;
    @JoinColumn(name = "date_type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigDateType configDateType;

    public TransactionDate() {
    }

    public TransactionDate(TransactionDatePK transactionDatePK) {
        this.transactionDatePK = transactionDatePK;
    }

    public TransactionDate(String transactionNo, String dateType) {
        this.transactionDatePK = new TransactionDatePK(transactionNo, dateType);
    }

    public TransactionDatePK getTransactionDatePK() {
        return transactionDatePK;
    }

    public void setTransactionDatePK(TransactionDatePK transactionDatePK) {
        this.transactionDatePK = transactionDatePK;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
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

    public ConfigDateType getConfigDateType() {
        return configDateType;
    }

    public void setConfigDateType(ConfigDateType configDateType) {
        this.configDateType = configDateType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionDatePK != null ? transactionDatePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionDate)) {
            return false;
        }
        TransactionDate other = (TransactionDate) object;
        if ((this.transactionDatePK == null && other.transactionDatePK != null) || (this.transactionDatePK != null && !this.transactionDatePK.equals(other.transactionDatePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionDate[ transactionDatePK=" + transactionDatePK + " ]";
    }
    
}
