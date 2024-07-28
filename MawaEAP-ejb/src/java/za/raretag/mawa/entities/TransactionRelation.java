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
@Table(name = "transaction_relation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionRelation.findAll", query = "SELECT t FROM TransactionRelation t"),
    @NamedQuery(name = "TransactionRelation.findByType", query = "SELECT t FROM TransactionRelation t WHERE t.transactionRelationPK.type = :type"),
    @NamedQuery(name = "TransactionRelation.findByTransactionNo1", query = "SELECT t FROM TransactionRelation t WHERE t.transactionRelationPK.transactionNo1 = :transactionNo1"),
    @NamedQuery(name = "TransactionRelation.findByTransactionNo2", query = "SELECT t FROM TransactionRelation t WHERE t.transactionRelationPK.transactionNo2 = :transactionNo2"),
    @NamedQuery(name = "TransactionRelation.findByValidFrom", query = "SELECT t FROM TransactionRelation t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionRelation.findByValidTo", query = "SELECT t FROM TransactionRelation t WHERE t.validTo = :validTo")})
public class TransactionRelation implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionRelationPK transactionRelationPK;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigTransactionType configTransactionType;
    @JoinColumn(name = "transaction_no1", referencedColumnName = "transaction_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaction transaction;
    @JoinColumn(name = "transaction_no2", referencedColumnName = "transaction_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaction transaction1;

    public TransactionRelation() {
    }

    public TransactionRelation(TransactionRelationPK transactionRelationPK) {
        this.transactionRelationPK = transactionRelationPK;
    }

    public TransactionRelation(String type, String transactionNo1, String transactionNo2) {
        this.transactionRelationPK = new TransactionRelationPK(type, transactionNo1, transactionNo2);
    }

    public TransactionRelationPK getTransactionRelationPK() {
        return transactionRelationPK;
    }

    public void setTransactionRelationPK(TransactionRelationPK transactionRelationPK) {
        this.transactionRelationPK = transactionRelationPK;
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

    public ConfigTransactionType getConfigTransactionType() {
        return configTransactionType;
    }

    public void setConfigTransactionType(ConfigTransactionType configTransactionType) {
        this.configTransactionType = configTransactionType;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction1() {
        return transaction1;
    }

    public void setTransaction1(Transaction transaction1) {
        this.transaction1 = transaction1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionRelationPK != null ? transactionRelationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionRelation)) {
            return false;
        }
        TransactionRelation other = (TransactionRelation) object;
        if ((this.transactionRelationPK == null && other.transactionRelationPK != null) || (this.transactionRelationPK != null && !this.transactionRelationPK.equals(other.transactionRelationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionRelation[ transactionRelationPK=" + transactionRelationPK + " ]";
    }
    
}
