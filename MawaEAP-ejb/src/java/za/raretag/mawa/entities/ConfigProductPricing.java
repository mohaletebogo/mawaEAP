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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "_config_product_pricing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigProductPricing.findAll", query = "SELECT c FROM ConfigProductPricing c"),
    @NamedQuery(name = "ConfigProductPricing.findById", query = "SELECT c FROM ConfigProductPricing c WHERE c.id = :id"),
    @NamedQuery(name = "ConfigProductPricing.findByDescription", query = "SELECT c FROM ConfigProductPricing c WHERE c.description = :description"),
    @NamedQuery(name = "ConfigProductPricing.findByValidFrom", query = "SELECT c FROM ConfigProductPricing c WHERE c.validFrom = :validFrom"),
    @NamedQuery(name = "ConfigProductPricing.findByValidTo", query = "SELECT c FROM ConfigProductPricing c WHERE c.validTo = :validTo")})
public class ConfigProductPricing implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 45)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configProductPricing")
    private Collection<TransactionItemPricing> transactionItemPricingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configProductPricing")
    private Collection<ProductPricing> productPricingCollection;

    public ConfigProductPricing() {
    }

    public ConfigProductPricing(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public Collection<TransactionItemPricing> getTransactionItemPricingCollection() {
        return transactionItemPricingCollection;
    }

    public void setTransactionItemPricingCollection(Collection<TransactionItemPricing> transactionItemPricingCollection) {
        this.transactionItemPricingCollection = transactionItemPricingCollection;
    }

    @XmlTransient
    public Collection<ProductPricing> getProductPricingCollection() {
        return productPricingCollection;
    }

    public void setProductPricingCollection(Collection<ProductPricing> productPricingCollection) {
        this.productPricingCollection = productPricingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigProductPricing)) {
            return false;
        }
        ConfigProductPricing other = (ConfigProductPricing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ConfigProductPricing[ id=" + id + " ]";
    }
    
}
