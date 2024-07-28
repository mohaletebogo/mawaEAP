/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "transaction_document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionDocument.findAll", query = "SELECT t FROM TransactionDocument t"),
    @NamedQuery(name = "TransactionDocument.findByTransactionNo", query = "SELECT t FROM TransactionDocument t WHERE t.transactionNo = :transactionNo"),
    @NamedQuery(name = "TransactionDocument.findByLocation", query = "SELECT t FROM TransactionDocument t WHERE t.location = :location"),
    @NamedQuery(name = "TransactionDocument.findByCreatedBy", query = "SELECT t FROM TransactionDocument t WHERE t.createdBy = :createdBy"),
    @NamedQuery(name = "TransactionDocument.findByCreatedAt", query = "SELECT t FROM TransactionDocument t WHERE t.createdAt = :createdAt")})
public class TransactionDocument implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "transaction_no")
    private String transactionNo;
    @Size(max = 100)
    @Column(name = "location")
    private String location;
    @Size(max = 10)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Transaction transaction;
    @JoinColumn(name = "document_type", referencedColumnName = "ID")
    @ManyToOne
    private ConfigDocumentType documentType;

    public TransactionDocument() {
    }

    public TransactionDocument(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public ConfigDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(ConfigDocumentType documentType) {
        this.documentType = documentType;
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
        if (!(object instanceof TransactionDocument)) {
            return false;
        }
        TransactionDocument other = (TransactionDocument) object;
        if ((this.transactionNo == null && other.transactionNo != null) || (this.transactionNo != null && !this.transactionNo.equals(other.transactionNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionDocument[ transactionNo=" + transactionNo + " ]";
    }
    
}
