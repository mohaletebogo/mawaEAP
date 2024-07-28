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
@Table(name = "partner_relation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartnerRelation.findAll", query = "SELECT p FROM PartnerRelation p"),
    @NamedQuery(name = "PartnerRelation.findByRelationsType", query = "SELECT p FROM PartnerRelation p WHERE p.partnerRelationPK.relationsType = :relationsType"),
    @NamedQuery(name = "PartnerRelation.findByPartnerNo1", query = "SELECT p FROM PartnerRelation p WHERE p.partnerRelationPK.partnerNo1 = :partnerNo1"),
    @NamedQuery(name = "PartnerRelation.findByPartnerNo2", query = "SELECT p FROM PartnerRelation p WHERE p.partnerRelationPK.partnerNo2 = :partnerNo2"),
    @NamedQuery(name = "PartnerRelation.findByValidFrom", query = "SELECT p FROM PartnerRelation p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "PartnerRelation.findByValidTo", query = "SELECT p FROM PartnerRelation p WHERE p.validTo = :validTo")})
public class PartnerRelation implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PartnerRelationPK partnerRelationPK;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "partner_no_1", referencedColumnName = "partner_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partner partner;
    @JoinColumn(name = "partner_no_2", referencedColumnName = "partner_no", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partner partner1;
    @JoinColumn(name = "relations_type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigRelationType configRelationType;

    public PartnerRelation() {
    }

    public PartnerRelation(PartnerRelationPK partnerRelationPK) {
        this.partnerRelationPK = partnerRelationPK;
    }

    public PartnerRelation(String relationsType, String partnerNo1, String partnerNo2) {
        this.partnerRelationPK = new PartnerRelationPK(relationsType, partnerNo1, partnerNo2);
    }

    public PartnerRelationPK getPartnerRelationPK() {
        return partnerRelationPK;
    }

    public void setPartnerRelationPK(PartnerRelationPK partnerRelationPK) {
        this.partnerRelationPK = partnerRelationPK;
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

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Partner getPartner1() {
        return partner1;
    }

    public void setPartner1(Partner partner1) {
        this.partner1 = partner1;
    }

    public ConfigRelationType getConfigRelationType() {
        return configRelationType;
    }

    public void setConfigRelationType(ConfigRelationType configRelationType) {
        this.configRelationType = configRelationType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerRelationPK != null ? partnerRelationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerRelation)) {
            return false;
        }
        PartnerRelation other = (PartnerRelation) object;
        if ((this.partnerRelationPK == null && other.partnerRelationPK != null) || (this.partnerRelationPK != null && !this.partnerRelationPK.equals(other.partnerRelationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerRelation[ partnerRelationPK=" + partnerRelationPK + " ]";
    }
    
}
