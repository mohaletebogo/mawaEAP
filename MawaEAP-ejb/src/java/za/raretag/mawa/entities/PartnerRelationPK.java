/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tebogom
 */
@Embeddable
public class PartnerRelationPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "relations_type")
    private String relationsType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "partner_no_1")
    private String partnerNo1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "partner_no_2")
    private String partnerNo2;

    public PartnerRelationPK() {
    }

    public PartnerRelationPK(String relationsType, String partnerNo1, String partnerNo2) {
        this.relationsType = relationsType;
        this.partnerNo1 = partnerNo1;
        this.partnerNo2 = partnerNo2;
    }

    public String getRelationsType() {
        return relationsType;
    }

    public void setRelationsType(String relationsType) {
        this.relationsType = relationsType;
    }

    public String getPartnerNo1() {
        return partnerNo1;
    }

    public void setPartnerNo1(String partnerNo1) {
        this.partnerNo1 = partnerNo1;
    }

    public String getPartnerNo2() {
        return partnerNo2;
    }

    public void setPartnerNo2(String partnerNo2) {
        this.partnerNo2 = partnerNo2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relationsType != null ? relationsType.hashCode() : 0);
        hash += (partnerNo1 != null ? partnerNo1.hashCode() : 0);
        hash += (partnerNo2 != null ? partnerNo2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerRelationPK)) {
            return false;
        }
        PartnerRelationPK other = (PartnerRelationPK) object;
        if ((this.relationsType == null && other.relationsType != null) || (this.relationsType != null && !this.relationsType.equals(other.relationsType))) {
            return false;
        }
        if ((this.partnerNo1 == null && other.partnerNo1 != null) || (this.partnerNo1 != null && !this.partnerNo1.equals(other.partnerNo1))) {
            return false;
        }
        if ((this.partnerNo2 == null && other.partnerNo2 != null) || (this.partnerNo2 != null && !this.partnerNo2.equals(other.partnerNo2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerRelationPK[ relationsType=" + relationsType + ", partnerNo1=" + partnerNo1 + ", partnerNo2=" + partnerNo2 + " ]";
    }
    
}
