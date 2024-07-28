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
public class PartnerAttributePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "partner_no")
    private String partnerNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "attribute")
    private String attribute;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "value")
    private String value;

    public PartnerAttributePK() {
    }

    public PartnerAttributePK(String partnerNo, String attribute, String value) {
        this.partnerNo = partnerNo;
        this.attribute = attribute;
        this.value = value;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerNo != null ? partnerNo.hashCode() : 0);
        hash += (attribute != null ? attribute.hashCode() : 0);
        hash += (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerAttributePK)) {
            return false;
        }
        PartnerAttributePK other = (PartnerAttributePK) object;
        if ((this.partnerNo == null && other.partnerNo != null) || (this.partnerNo != null && !this.partnerNo.equals(other.partnerNo))) {
            return false;
        }
        if ((this.attribute == null && other.attribute != null) || (this.attribute != null && !this.attribute.equals(other.attribute))) {
            return false;
        }
        if ((this.value == null && other.value != null) || (this.value != null && !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerAttributePK[ partnerNo=" + partnerNo + ", attribute=" + attribute + ", value=" + value + " ]";
    }
    
}
