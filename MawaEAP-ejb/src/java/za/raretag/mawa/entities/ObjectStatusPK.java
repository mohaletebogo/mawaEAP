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
public class ObjectStatusPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "obect_type")
    private String obectType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "object_no")
    private String objectNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "status")
    private String status;

    public ObjectStatusPK() {
    }

    public ObjectStatusPK(String obectType, String objectNo, String status) {
        this.obectType = obectType;
        this.objectNo = objectNo;
        this.status = status;
    }

    public String getObectType() {
        return obectType;
    }

    public void setObectType(String obectType) {
        this.obectType = obectType;
    }

    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (obectType != null ? obectType.hashCode() : 0);
        hash += (objectNo != null ? objectNo.hashCode() : 0);
        hash += (status != null ? status.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjectStatusPK)) {
            return false;
        }
        ObjectStatusPK other = (ObjectStatusPK) object;
        if ((this.obectType == null && other.obectType != null) || (this.obectType != null && !this.obectType.equals(other.obectType))) {
            return false;
        }
        if ((this.objectNo == null && other.objectNo != null) || (this.objectNo != null && !this.objectNo.equals(other.objectNo))) {
            return false;
        }
        if ((this.status == null && other.status != null) || (this.status != null && !this.status.equals(other.status))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ObjectStatusPK[ obectType=" + obectType + ", objectNo=" + objectNo + ", status=" + status + " ]";
    }
    
}
