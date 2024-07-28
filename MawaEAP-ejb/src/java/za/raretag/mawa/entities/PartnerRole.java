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
@Table(name = "partner_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartnerRole.findAll", query = "SELECT p FROM PartnerRole p"),
    @NamedQuery(name = "PartnerRole.findByRoleId", query = "SELECT p FROM PartnerRole p WHERE p.partnerRolePK.roleId = :roleId"),
    @NamedQuery(name = "PartnerRole.findByPartnerNo", query = "SELECT p FROM PartnerRole p WHERE p.partnerRolePK.partnerNo = :partnerNo"),
    @NamedQuery(name = "PartnerRole.findByValidFrom", query = "SELECT p FROM PartnerRole p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "PartnerRole.findByValidTo", query = "SELECT p FROM PartnerRole p WHERE p.validTo = :validTo")})
public class PartnerRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PartnerRolePK partnerRolePK;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "role_id", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigRoleType configRoleType;
    @JoinColumn(name = "partner_no", referencedColumnName = "partner_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partner partner;

    public PartnerRole() {
    }

    public PartnerRole(PartnerRolePK partnerRolePK) {
        this.partnerRolePK = partnerRolePK;
    }

    public PartnerRole(String roleId, String partnerNo) {
        this.partnerRolePK = new PartnerRolePK(roleId, partnerNo);
    }

    public PartnerRolePK getPartnerRolePK() {
        return partnerRolePK;
    }

    public void setPartnerRolePK(PartnerRolePK partnerRolePK) {
        this.partnerRolePK = partnerRolePK;
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

    public ConfigRoleType getConfigRoleType() {
        return configRoleType;
    }

    public void setConfigRoleType(ConfigRoleType configRoleType) {
        this.configRoleType = configRoleType;
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
        hash += (partnerRolePK != null ? partnerRolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerRole)) {
            return false;
        }
        PartnerRole other = (PartnerRole) object;
        if ((this.partnerRolePK == null && other.partnerRolePK != null) || (this.partnerRolePK != null && !this.partnerRolePK.equals(other.partnerRolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerRole[ partnerRolePK=" + partnerRolePK + " ]";
    }
    
}
