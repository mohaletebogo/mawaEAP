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
@Table(name = "partner_attribute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartnerAttribute.findAll", query = "SELECT p FROM PartnerAttribute p"),
    @NamedQuery(name = "PartnerAttribute.findByPartnerNo", query = "SELECT p FROM PartnerAttribute p WHERE p.partnerAttributePK.partnerNo = :partnerNo"),
    @NamedQuery(name = "PartnerAttribute.findByAttribute", query = "SELECT p FROM PartnerAttribute p WHERE p.partnerAttributePK.attribute = :attribute"),
    @NamedQuery(name = "PartnerAttribute.findByValue", query = "SELECT p FROM PartnerAttribute p WHERE p.partnerAttributePK.value = :value"),
    @NamedQuery(name = "PartnerAttribute.findByValidFrom", query = "SELECT p FROM PartnerAttribute p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "PartnerAttribute.findByValidTo", query = "SELECT p FROM PartnerAttribute p WHERE p.validTo = :validTo")})
public class PartnerAttribute implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PartnerAttributePK partnerAttributePK;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "attribute", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigPartnerAttributes configPartnerAttributes;
    @JoinColumn(name = "partner_no", referencedColumnName = "partner_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partner partner;

    public PartnerAttribute() {
    }

    public PartnerAttribute(PartnerAttributePK partnerAttributePK) {
        this.partnerAttributePK = partnerAttributePK;
    }

    public PartnerAttribute(String partnerNo, String attribute, String value) {
        this.partnerAttributePK = new PartnerAttributePK(partnerNo, attribute, value);
    }

    public PartnerAttributePK getPartnerAttributePK() {
        return partnerAttributePK;
    }

    public void setPartnerAttributePK(PartnerAttributePK partnerAttributePK) {
        this.partnerAttributePK = partnerAttributePK;
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

    public ConfigPartnerAttributes getConfigPartnerAttributes() {
        return configPartnerAttributes;
    }

    public void setConfigPartnerAttributes(ConfigPartnerAttributes configPartnerAttributes) {
        this.configPartnerAttributes = configPartnerAttributes;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerAttributePK != null ? partnerAttributePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerAttribute)) {
            return false;
        }
        PartnerAttribute other = (PartnerAttribute) object;
        if ((this.partnerAttributePK == null && other.partnerAttributePK != null) || (this.partnerAttributePK != null && !this.partnerAttributePK.equals(other.partnerAttributePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerAttribute[ partnerAttributePK=" + partnerAttributePK + " ]";
    }
    
}
