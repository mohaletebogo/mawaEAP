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
@Table(name = "transaction_item_pricing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionItemPricing.findAll", query = "SELECT t FROM TransactionItemPricing t"),
    @NamedQuery(name = "TransactionItemPricing.findByItemId", query = "SELECT t FROM TransactionItemPricing t WHERE t.transactionItemPricingPK.itemId = :itemId"),
    @NamedQuery(name = "TransactionItemPricing.findByPricingType", query = "SELECT t FROM TransactionItemPricing t WHERE t.transactionItemPricingPK.pricingType = :pricingType"),
    @NamedQuery(name = "TransactionItemPricing.findByValue", query = "SELECT t FROM TransactionItemPricing t WHERE t.value = :value"),
    @NamedQuery(name = "TransactionItemPricing.findByValidFrom", query = "SELECT t FROM TransactionItemPricing t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionItemPricing.findByValidTo", query = "SELECT t FROM TransactionItemPricing t WHERE t.validTo = :validTo")})
public class TransactionItemPricing implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionItemPricingPK transactionItemPricingPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TransactionItem transactionItem;
    @JoinColumn(name = "pricing_type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigProductPricing configProductPricing;

    public TransactionItemPricing() {
    }

    public TransactionItemPricing(TransactionItemPricingPK transactionItemPricingPK) {
        this.transactionItemPricingPK = transactionItemPricingPK;
    }

    public TransactionItemPricing(int itemId, String pricingType) {
        this.transactionItemPricingPK = new TransactionItemPricingPK(itemId, pricingType);
    }

    public TransactionItemPricingPK getTransactionItemPricingPK() {
        return transactionItemPricingPK;
    }

    public void setTransactionItemPricingPK(TransactionItemPricingPK transactionItemPricingPK) {
        this.transactionItemPricingPK = transactionItemPricingPK;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
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

    public TransactionItem getTransactionItem() {
        return transactionItem;
    }

    public void setTransactionItem(TransactionItem transactionItem) {
        this.transactionItem = transactionItem;
    }

    public ConfigProductPricing getConfigProductPricing() {
        return configProductPricing;
    }

    public void setConfigProductPricing(ConfigProductPricing configProductPricing) {
        this.configProductPricing = configProductPricing;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionItemPricingPK != null ? transactionItemPricingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionItemPricing)) {
            return false;
        }
        TransactionItemPricing other = (TransactionItemPricing) object;
        if ((this.transactionItemPricingPK == null && other.transactionItemPricingPK != null) || (this.transactionItemPricingPK != null && !this.transactionItemPricingPK.equals(other.transactionItemPricingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionItemPricing[ transactionItemPricingPK=" + transactionItemPricingPK + " ]";
    }
    
}
