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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "object_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObjectStatus.findAll", query = "SELECT o FROM ObjectStatus o"),
    @NamedQuery(name = "ObjectStatus.findByObectType", query = "SELECT o FROM ObjectStatus o WHERE o.objectStatusPK.obectType = :obectType"),
    @NamedQuery(name = "ObjectStatus.findByObjectNo", query = "SELECT o FROM ObjectStatus o WHERE o.objectStatusPK.objectNo = :objectNo"),
    @NamedQuery(name = "ObjectStatus.findByStatus", query = "SELECT o FROM ObjectStatus o WHERE o.objectStatusPK.status = :status"),
    @NamedQuery(name = "ObjectStatus.findBySetTime", query = "SELECT o FROM ObjectStatus o WHERE o.setTime = :setTime"),
    @NamedQuery(name = "ObjectStatus.findByActive", query = "SELECT o FROM ObjectStatus o WHERE o.active = :active")})
public class ObjectStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ObjectStatusPK objectStatusPK;
    @Column(name = "set_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date setTime;
    @Size(max = 1)
    @Column(name = "active")
    private String active;

    public ObjectStatus() {
    }

    public ObjectStatus(ObjectStatusPK objectStatusPK) {
        this.objectStatusPK = objectStatusPK;
    }

    public ObjectStatus(String obectType, String objectNo, String status) {
        this.objectStatusPK = new ObjectStatusPK(obectType, objectNo, status);
    }

    public ObjectStatusPK getObjectStatusPK() {
        return objectStatusPK;
    }

    public void setObjectStatusPK(ObjectStatusPK objectStatusPK) {
        this.objectStatusPK = objectStatusPK;
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objectStatusPK != null ? objectStatusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjectStatus)) {
            return false;
        }
        ObjectStatus other = (ObjectStatus) object;
        if ((this.objectStatusPK == null && other.objectStatusPK != null) || (this.objectStatusPK != null && !this.objectStatusPK.equals(other.objectStatusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ObjectStatus[ objectStatusPK=" + objectStatusPK + " ]";
    }
    
}
