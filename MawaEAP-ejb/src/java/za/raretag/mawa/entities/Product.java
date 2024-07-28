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
@Table(name = "product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductCode", query = "SELECT p FROM Product p WHERE p.productCode = :productCode"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByValidFrom", query = "SELECT p FROM Product p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "Product.findByValidTo", query = "SELECT p FROM Product p WHERE p.validTo = :validTo")})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "product_code")
    private String productCode;
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @OneToMany(mappedBy = "productCode")
    private Collection<ProductAttribute> productAttributeCollection;
    @OneToMany(mappedBy = "productId")
    private Collection<TransactionItem> transactionItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<ProductPricing> productPricingCollection;

    public Product() {
    }

    public Product(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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
    public Collection<ProductAttribute> getProductAttributeCollection() {
        return productAttributeCollection;
    }

    public void setProductAttributeCollection(Collection<ProductAttribute> productAttributeCollection) {
        this.productAttributeCollection = productAttributeCollection;
    }

    @XmlTransient
    public Collection<TransactionItem> getTransactionItemCollection() {
        return transactionItemCollection;
    }

    public void setTransactionItemCollection(Collection<TransactionItem> transactionItemCollection) {
        this.transactionItemCollection = transactionItemCollection;
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
        hash += (productCode != null ? productCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productCode == null && other.productCode != null) || (this.productCode != null && !this.productCode.equals(other.productCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.Product[ productCode=" + productCode + " ]";
    }
    
}
