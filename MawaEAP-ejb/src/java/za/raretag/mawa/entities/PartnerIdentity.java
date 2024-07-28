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
@Table(name = "partner_identity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartnerIdentity.findAll", query = "SELECT p FROM PartnerIdentity p"),
    @NamedQuery(name = "PartnerIdentity.findByIdType", query = "SELECT p FROM PartnerIdentity p WHERE p.partnerIdentityPK.idType = :idType"),
    @NamedQuery(name = "PartnerIdentity.findByIdNumber", query = "SELECT p FROM PartnerIdentity p WHERE p.partnerIdentityPK.idNumber = :idNumber"),
    @NamedQuery(name = "PartnerIdentity.findByValidFrom", query = "SELECT p FROM PartnerIdentity p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "PartnerIdentity.findByValidTo", query = "SELECT p FROM PartnerIdentity p WHERE p.validTo = :validTo")})
public class PartnerIdentity implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PartnerIdentityPK partnerIdentityPK;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "id_type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigIdentityType configIdentityType;
    @JoinColumn(name = "partner_no", referencedColumnName = "partner_no")
    @ManyToOne
    private Partner partnerNo;

    public PartnerIdentity() {
    }

    public PartnerIdentity(PartnerIdentityPK partnerIdentityPK) {
        this.partnerIdentityPK = partnerIdentityPK;
    }

    public PartnerIdentity(String idType, String idNumber) {
        this.partnerIdentityPK = new PartnerIdentityPK(idType, idNumber);
    }

    public PartnerIdentityPK getPartnerIdentityPK() {
        return partnerIdentityPK;
    }

    public void setPartnerIdentityPK(PartnerIdentityPK partnerIdentityPK) {
        this.partnerIdentityPK = partnerIdentityPK;
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

    public ConfigIdentityType getConfigIdentityType() {
        return configIdentityType;
    }

    public void setConfigIdentityType(ConfigIdentityType configIdentityType) {
        this.configIdentityType = configIdentityType;
    }

    public Partner getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(Partner partnerNo) {
        this.partnerNo = partnerNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerIdentityPK != null ? partnerIdentityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerIdentity)) {
            return false;
        }
        PartnerIdentity other = (PartnerIdentity) object;
        if ((this.partnerIdentityPK == null && other.partnerIdentityPK != null) || (this.partnerIdentityPK != null && !this.partnerIdentityPK.equals(other.partnerIdentityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerIdentity[ partnerIdentityPK=" + partnerIdentityPK + " ]";
    }
    
}
