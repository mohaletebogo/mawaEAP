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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "product_attribute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductAttribute.findAll", query = "SELECT p FROM ProductAttribute p"),
    @NamedQuery(name = "ProductAttribute.findByAttibuteId", query = "SELECT p FROM ProductAttribute p WHERE p.attibuteId = :attibuteId"),
    @NamedQuery(name = "ProductAttribute.findByAttributeValue", query = "SELECT p FROM ProductAttribute p WHERE p.attributeValue = :attributeValue"),
    @NamedQuery(name = "ProductAttribute.findByValidFrom", query = "SELECT p FROM ProductAttribute p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "ProductAttribute.findByValidTo", query = "SELECT p FROM ProductAttribute p WHERE p.validTo = :validTo")})
public class ProductAttribute implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATTIBUTE_ID")
    private Integer attibuteId;
    @Size(max = 60)
    @Column(name = "ATTRIBUTE_VALUE")
    private String attributeValue;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "PRODUCT_CODE", referencedColumnName = "product_code")
    @ManyToOne
    private Product productCode;
    @JoinColumn(name = "ATTRIBUTE_TYPE", referencedColumnName = "ID")
    @ManyToOne
    private ConfigProductAttribute attributeType;

    public ProductAttribute() {
    }

    public ProductAttribute(Integer attibuteId) {
        this.attibuteId = attibuteId;
    }

    public Integer getAttibuteId() {
        return attibuteId;
    }

    public void setAttibuteId(Integer attibuteId) {
        this.attibuteId = attibuteId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
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

    public Product getProductCode() {
        return productCode;
    }

    public void setProductCode(Product productCode) {
        this.productCode = productCode;
    }

    public ConfigProductAttribute getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(ConfigProductAttribute attributeType) {
        this.attributeType = attributeType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attibuteId != null ? attibuteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductAttribute)) {
            return false;
        }
        ProductAttribute other = (ProductAttribute) object;
        if ((this.attibuteId == null && other.attibuteId != null) || (this.attibuteId != null && !this.attibuteId.equals(other.attibuteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ProductAttribute[ attibuteId=" + attibuteId + " ]";
    }
    
}
