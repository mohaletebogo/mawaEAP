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
@Table(name = "product_pricing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductPricing.findAll", query = "SELECT p FROM ProductPricing p"),
    @NamedQuery(name = "ProductPricing.findByProductCode", query = "SELECT p FROM ProductPricing p WHERE p.productPricingPK.productCode = :productCode"),
    @NamedQuery(name = "ProductPricing.findByPricingType", query = "SELECT p FROM ProductPricing p WHERE p.productPricingPK.pricingType = :pricingType"),
    @NamedQuery(name = "ProductPricing.findByValue", query = "SELECT p FROM ProductPricing p WHERE p.value = :value"),
    @NamedQuery(name = "ProductPricing.findByValidFrom", query = "SELECT p FROM ProductPricing p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "ProductPricing.findByValidTo", query = "SELECT p FROM ProductPricing p WHERE p.validTo = :validTo")})
public class ProductPricing implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductPricingPK productPricingPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private BigDecimal value;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "product_code", referencedColumnName = "product_code", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "pricing_type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigProductPricing configProductPricing;

    public ProductPricing() {
    }

    public ProductPricing(ProductPricingPK productPricingPK) {
        this.productPricingPK = productPricingPK;
    }

    public ProductPricing(String productCode, String pricingType) {
        this.productPricingPK = new ProductPricingPK(productCode, pricingType);
    }

    public ProductPricingPK getProductPricingPK() {
        return productPricingPK;
    }

    public void setProductPricingPK(ProductPricingPK productPricingPK) {
        this.productPricingPK = productPricingPK;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        hash += (productPricingPK != null ? productPricingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductPricing)) {
            return false;
        }
        ProductPricing other = (ProductPricing) object;
        if ((this.productPricingPK == null && other.productPricingPK != null) || (this.productPricingPK != null && !this.productPricingPK.equals(other.productPricingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ProductPricing[ productPricingPK=" + productPricingPK + " ]";
    }
    
}
