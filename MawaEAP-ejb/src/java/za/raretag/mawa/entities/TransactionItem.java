/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "transaction_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionItem.findAll", query = "SELECT t FROM TransactionItem t"),
    @NamedQuery(name = "TransactionItem.findByItemId", query = "SELECT t FROM TransactionItem t WHERE t.itemId = :itemId"),
    @NamedQuery(name = "TransactionItem.findByQuantity", query = "SELECT t FROM TransactionItem t WHERE t.quantity = :quantity"),
    @NamedQuery(name = "TransactionItem.findByUnitPrice", query = "SELECT t FROM TransactionItem t WHERE t.unitPrice = :unitPrice"),
    @NamedQuery(name = "TransactionItem.findByValidFrom", query = "SELECT t FROM TransactionItem t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionItem.findByValidTo", query = "SELECT t FROM TransactionItem t WHERE t.validTo = :validTo")})
public class TransactionItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "item_id")
    private Integer itemId;
    @Column(name = "quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "item_category", referencedColumnName = "ID")
    @ManyToOne
    private ConfigProductCategory itemCategory;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no")
    @ManyToOne
    private Transaction transactionNo;
    @JoinColumn(name = "product_id", referencedColumnName = "product_code")
    @ManyToOne
    private Product productId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionItem")
    private Collection<TransactionItemPricing> transactionItemPricingCollection;

    public TransactionItem() {
    }

    public TransactionItem(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public ConfigProductCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ConfigProductCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Transaction getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Transaction transactionNo) {
        this.transactionNo = transactionNo;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @XmlTransient
    public Collection<TransactionItemPricing> getTransactionItemPricingCollection() {
        return transactionItemPricingCollection;
    }

    public void setTransactionItemPricingCollection(Collection<TransactionItemPricing> transactionItemPricingCollection) {
        this.transactionItemPricingCollection = transactionItemPricingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionItem)) {
            return false;
        }
        TransactionItem other = (TransactionItem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionItem[ itemId=" + itemId + " ]";
    }
    
}
