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
public class PartnerIdentityPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_type")
    private String idType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id_number")
    private String idNumber;

    public PartnerIdentityPK() {
    }

    public PartnerIdentityPK(String idType, String idNumber) {
        this.idType = idType;
        this.idNumber = idNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idType != null ? idType.hashCode() : 0);
        hash += (idNumber != null ? idNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerIdentityPK)) {
            return false;
        }
        PartnerIdentityPK other = (PartnerIdentityPK) object;
        if ((this.idType == null && other.idType != null) || (this.idType != null && !this.idType.equals(other.idType))) {
            return false;
        }
        if ((this.idNumber == null && other.idNumber != null) || (this.idNumber != null && !this.idNumber.equals(other.idNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerIdentityPK[ idType=" + idType + ", idNumber=" + idNumber + " ]";
    }
    
}
