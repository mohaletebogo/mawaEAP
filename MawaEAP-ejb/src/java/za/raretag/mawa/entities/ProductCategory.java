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
@Table(name = "product_category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductCategory.findAll", query = "SELECT p FROM ProductCategory p"),
    @NamedQuery(name = "ProductCategory.findByProductCode", query = "SELECT p FROM ProductCategory p WHERE p.productCategoryPK.productCode = :productCode"),
    @NamedQuery(name = "ProductCategory.findByProductCategory", query = "SELECT p FROM ProductCategory p WHERE p.productCategoryPK.productCategory = :productCategory"),
    @NamedQuery(name = "ProductCategory.findByValidFrom", query = "SELECT p FROM ProductCategory p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "ProductCategory.findByValidTo", query = "SELECT p FROM ProductCategory p WHERE p.validTo = :validTo")})
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductCategoryPK productCategoryPK;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    public ProductCategory() {
    }

    public ProductCategory(ProductCategoryPK productCategoryPK) {
        this.productCategoryPK = productCategoryPK;
    }

    public ProductCategory(String productCode, String productCategory) {
        this.productCategoryPK = new ProductCategoryPK(productCode, productCategory);
    }

    public ProductCategoryPK getProductCategoryPK() {
        return productCategoryPK;
    }

    public void setProductCategoryPK(ProductCategoryPK productCategoryPK) {
        this.productCategoryPK = productCategoryPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productCategoryPK != null ? productCategoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCategory)) {
            return false;
        }
        ProductCategory other = (ProductCategory) object;
        if ((this.productCategoryPK == null && other.productCategoryPK != null) || (this.productCategoryPK != null && !this.productCategoryPK.equals(other.productCategoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ProductCategory[ productCategoryPK=" + productCategoryPK + " ]";
    }
    
}
