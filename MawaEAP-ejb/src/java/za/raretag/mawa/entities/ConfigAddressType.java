/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "_config_address_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigAddressType.findAll", query = "SELECT c FROM ConfigAddressType c"),
    @NamedQuery(name = "ConfigAddressType.findById", query = "SELECT c FROM ConfigAddressType c WHERE c.id = :id"),
    @NamedQuery(name = "ConfigAddressType.findByDescription", query = "SELECT c FROM ConfigAddressType c WHERE c.description = :description"),
    @NamedQuery(name = "ConfigAddressType.findByValidFrom", query = "SELECT c FROM ConfigAddressType c WHERE c.validFrom = :validFrom"),
    @NamedQuery(name = "ConfigAddressType.findByValidTo", query = "SELECT c FROM ConfigAddressType c WHERE c.validTo = :validTo")})
public class ConfigAddressType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ID")
    private String id;
    @Size(max = 45)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @OneToMany(mappedBy = "addressType")
    private Collection<PartnerAddress> partnerAddressCollection;

    public ConfigAddressType() {
    }

    public ConfigAddressType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @XmlTransient
    public Collection<PartnerAddress> getPartnerAddressCollection() {
        return partnerAddressCollection;
    }

    public void setPartnerAddressCollection(Collection<PartnerAddress> partnerAddressCollection) {
        this.partnerAddressCollection = partnerAddressCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigAddressType)) {
            return false;
        }
        ConfigAddressType other = (ConfigAddressType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ConfigAddressType[ id=" + id + " ]";
    }
    
}
