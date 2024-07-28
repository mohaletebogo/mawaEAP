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
@Table(name = "transaction_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionLocation.findAll", query = "SELECT t FROM TransactionLocation t"),
    @NamedQuery(name = "TransactionLocation.findByLocationType", query = "SELECT t FROM TransactionLocation t WHERE t.transactionLocationPK.locationType = :locationType"),
    @NamedQuery(name = "TransactionLocation.findByTransactionNo", query = "SELECT t FROM TransactionLocation t WHERE t.transactionLocationPK.transactionNo = :transactionNo"),
    @NamedQuery(name = "TransactionLocation.findByLocationId", query = "SELECT t FROM TransactionLocation t WHERE t.transactionLocationPK.locationId = :locationId"),
    @NamedQuery(name = "TransactionLocation.findByValidFrom", query = "SELECT t FROM TransactionLocation t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionLocation.findByValidTo", query = "SELECT t FROM TransactionLocation t WHERE t.validTo = :validTo")})
public class TransactionLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionLocationPK transactionLocationPK;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "location_id", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigSalesArea configSalesArea;
    @JoinColumn(name = "location_type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigLocationType configLocationType;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaction transaction;

    public TransactionLocation() {
    }

    public TransactionLocation(TransactionLocationPK transactionLocationPK) {
        this.transactionLocationPK = transactionLocationPK;
    }

    public TransactionLocation(String locationType, String transactionNo, String locationId) {
        this.transactionLocationPK = new TransactionLocationPK(locationType, transactionNo, locationId);
    }

    public TransactionLocationPK getTransactionLocationPK() {
        return transactionLocationPK;
    }

    public void setTransactionLocationPK(TransactionLocationPK transactionLocationPK) {
        this.transactionLocationPK = transactionLocationPK;
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

    public ConfigSalesArea getConfigSalesArea() {
        return configSalesArea;
    }

    public void setConfigSalesArea(ConfigSalesArea configSalesArea) {
        this.configSalesArea = configSalesArea;
    }

    public ConfigLocationType getConfigLocationType() {
        return configLocationType;
    }

    public void setConfigLocationType(ConfigLocationType configLocationType) {
        this.configLocationType = configLocationType;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionLocationPK != null ? transactionLocationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionLocation)) {
            return false;
        }
        TransactionLocation other = (TransactionLocation) object;
        if ((this.transactionLocationPK == null && other.transactionLocationPK != null) || (this.transactionLocationPK != null && !this.transactionLocationPK.equals(other.transactionLocationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionLocation[ transactionLocationPK=" + transactionLocationPK + " ]";
    }
    
}
