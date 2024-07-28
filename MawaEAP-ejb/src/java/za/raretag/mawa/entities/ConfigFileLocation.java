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
@Table(name = "_config_file_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigFileLocation.findAll", query = "SELECT c FROM ConfigFileLocation c"),
    @NamedQuery(name = "ConfigFileLocation.findByLocationName", query = "SELECT c FROM ConfigFileLocation c WHERE c.locationName = :locationName"),
    @NamedQuery(name = "ConfigFileLocation.findByFilePath", query = "SELECT c FROM ConfigFileLocation c WHERE c.filePath = :filePath"),
    @NamedQuery(name = "ConfigFileLocation.findByValidFrom", query = "SELECT c FROM ConfigFileLocation c WHERE c.validFrom = :validFrom"),
    @NamedQuery(name = "ConfigFileLocation.findByValidTo", query = "SELECT c FROM ConfigFileLocation c WHERE c.validTo = :validTo")})
public class ConfigFileLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LOCATION_NAME")
    private String locationName;
    @Size(max = 200)
    @Column(name = "FILE_PATH")
    private String filePath;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    public ConfigFileLocation() {
    }

    public ConfigFileLocation(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
        hash += (locationName != null ? locationName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigFileLocation)) {
            return false;
        }
        ConfigFileLocation other = (ConfigFileLocation) object;
        if ((this.locationName == null && other.locationName != null) || (this.locationName != null && !this.locationName.equals(other.locationName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ConfigFileLocation[ locationName=" + locationName + " ]";
    }
    
}
