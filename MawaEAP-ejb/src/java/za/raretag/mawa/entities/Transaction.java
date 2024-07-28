/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
    @NamedQuery(name = "Transaction.findByTransactionNo", query = "SELECT t FROM Transaction t WHERE t.transactionNo = :transactionNo"),
    @NamedQuery(name = "Transaction.findByDescription", query = "SELECT t FROM Transaction t WHERE t.description = :description"),
    @NamedQuery(name = "Transaction.findByStatusDate", query = "SELECT t FROM Transaction t WHERE t.statusDate = :statusDate"),
    @NamedQuery(name = "Transaction.findByValidFrom", query = "SELECT t FROM Transaction t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "Transaction.findByValidTo", query = "SELECT t FROM Transaction t WHERE t.validTo = :validTo")})
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "transaction_no")
    private String transactionNo;
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    @Column(name = "status_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDate;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @OneToMany(mappedBy = "transactionNo")
    private Collection<TransactionItem> transactionItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private Collection<TransactionRelation> transactionRelationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction1")
    private Collection<TransactionRelation> transactionRelationCollection1;
    @OneToMany(mappedBy = "transactionNo")
    private Collection<TransactionPayment> transactionPaymentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private Collection<TransactionDate> transactionDateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionNo")
    private Collection<TransactionBank> transactionBankCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private Collection<TransactionPartner> transactionPartnerCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transaction")
    private TransactionDocument transactionDocument;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private Collection<TransactionStatus> transactionStatusCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private Collection<TransactionAmount> transactionAmountCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private Collection<TransactionLocation> transactionLocationCollection;
    @JoinColumn(name = "status", referencedColumnName = "ID")
    @ManyToOne
    private ConfigStatusType status;
    @JoinColumn(name = "transaction_type", referencedColumnName = "ID")
    @ManyToOne
    private ConfigTransactionType transactionType;

    public Transaction() {
    }

    public Transaction(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @XmlTransient
    public Collection<TransactionItem> getTransactionItemCollection() {
        return transactionItemCollection;
    }

    public void setTransactionItemCollection(Collection<TransactionItem> transactionItemCollection) {
        this.transactionItemCollection = transactionItemCollection;
    }

    @XmlTransient
    public Collection<TransactionRelation> getTransactionRelationCollection() {
        return transactionRelationCollection;
    }

    public void setTransactionRelationCollection(Collection<TransactionRelation> transactionRelationCollection) {
        this.transactionRelationCollection = transactionRelationCollection;
    }

    @XmlTransient
    public Collection<TransactionRelation> getTransactionRelationCollection1() {
        return transactionRelationCollection1;
    }

    public void setTransactionRelationCollection1(Collection<TransactionRelation> transactionRelationCollection1) {
        this.transactionRelationCollection1 = transactionRelationCollection1;
    }

    @XmlTransient
    public Collection<TransactionPayment> getTransactionPaymentCollection() {
        return transactionPaymentCollection;
    }

    public void setTransactionPaymentCollection(Collection<TransactionPayment> transactionPaymentCollection) {
        this.transactionPaymentCollection = transactionPaymentCollection;
    }

    @XmlTransient
    public Collection<TransactionDate> getTransactionDateCollection() {
        return transactionDateCollection;
    }

    public void setTransactionDateCollection(Collection<TransactionDate> transactionDateCollection) {
        this.transactionDateCollection = transactionDateCollection;
    }

    @XmlTransient
    public Collection<TransactionBank> getTransactionBankCollection() {
        return transactionBankCollection;
    }

    public void setTransactionBankCollection(Collection<TransactionBank> transactionBankCollection) {
        this.transactionBankCollection = transactionBankCollection;
    }

    @XmlTransient
    public Collection<TransactionPartner> getTransactionPartnerCollection() {
        return transactionPartnerCollection;
    }

    public void setTransactionPartnerCollection(Collection<TransactionPartner> transactionPartnerCollection) {
        this.transactionPartnerCollection = transactionPartnerCollection;
    }

    public TransactionDocument getTransactionDocument() {
        return transactionDocument;
    }

    public void setTransactionDocument(TransactionDocument transactionDocument) {
        this.transactionDocument = transactionDocument;
    }

    @XmlTransient
    public Collection<TransactionStatus> getTransactionStatusCollection() {
        return transactionStatusCollection;
    }

    public void setTransactionStatusCollection(Collection<TransactionStatus> transactionStatusCollection) {
        this.transactionStatusCollection = transactionStatusCollection;
    }

    @XmlTransient
    public Collection<TransactionAmount> getTransactionAmountCollection() {
        return transactionAmountCollection;
    }

    public void setTransactionAmountCollection(Collection<TransactionAmount> transactionAmountCollection) {
        this.transactionAmountCollection = transactionAmountCollection;
    }

    @XmlTransient
    public Collection<TransactionLocation> getTransactionLocationCollection() {
        return transactionLocationCollection;
    }

    public void setTransactionLocationCollection(Collection<TransactionLocation> transactionLocationCollection) {
        this.transactionLocationCollection = transactionLocationCollection;
    }

    public ConfigStatusType getStatus() {
        return status;
    }

    public void setStatus(ConfigStatusType status) {
        this.status = status;
    }

    public ConfigTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(ConfigTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionNo != null ? transactionNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.transactionNo == null && other.transactionNo != null) || (this.transactionNo != null && !this.transactionNo.equals(other.transactionNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.Transaction[ transactionNo=" + transactionNo + " ]";
    }
    
}
