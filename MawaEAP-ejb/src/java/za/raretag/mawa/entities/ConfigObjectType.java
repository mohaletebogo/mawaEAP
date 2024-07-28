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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "_config_object_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigObjectType.findAll", query = "SELECT c FROM ConfigObjectType c"),
    @NamedQuery(name = "ConfigObjectType.findByObjectId", query = "SELECT c FROM ConfigObjectType c WHERE c.objectId = :objectId"),
    @NamedQuery(name = "ConfigObjectType.findByDescription", query = "SELECT c FROM ConfigObjectType c WHERE c.description = :description"),
    @NamedQuery(name = "ConfigObjectType.findByValidFrom", query = "SELECT c FROM ConfigObjectType c WHERE c.validFrom = :validFrom"),
    @NamedQuery(name = "ConfigObjectType.findByValidTo", query = "SELECT c FROM ConfigObjectType c WHERE c.validTo = :validTo")})
public class ConfigObjectType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "OBJECT_ID")
    private String objectId;
    @Size(max = 30)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    public ConfigObjectType() {
    }

    public ConfigObjectType(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objectId != null ? objectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigObjectType)) {
            return false;
        }
        ConfigObjectType other = (ConfigObjectType) object;
        if ((this.objectId == null && other.objectId != null) || (this.objectId != null && !this.objectId.equals(other.objectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ConfigObjectType[ objectId=" + objectId + " ]";
    }
    
}
