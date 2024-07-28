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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "partner_contact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartnerContact.findAll", query = "SELECT p FROM PartnerContact p"),
    @NamedQuery(name = "PartnerContact.findByContactId", query = "SELECT p FROM PartnerContact p WHERE p.contactId = :contactId"),
    @NamedQuery(name = "PartnerContact.findByValue", query = "SELECT p FROM PartnerContact p WHERE p.value = :value"),
    @NamedQuery(name = "PartnerContact.findByValidFrom", query = "SELECT p FROM PartnerContact p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "PartnerContact.findByValidTo", query = "SELECT p FROM PartnerContact p WHERE p.validTo = :validTo")})
public class PartnerContact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contact_id")
    private Integer contactId;
    @Size(max = 45)
    @Column(name = "value")
    private String value;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "type", referencedColumnName = "ID")
    @ManyToOne
    private ConfigContactType type;
    @JoinColumn(name = "partner_no", referencedColumnName = "partner_no")
    @ManyToOne
    private Partner partnerNo;

    public PartnerContact() {
    }

    public PartnerContact(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public ConfigContactType getType() {
        return type;
    }

    public void setType(ConfigContactType type) {
        this.type = type;
    }

    public Partner getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(Partner partnerNo) {
        this.partnerNo = partnerNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contactId != null ? contactId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerContact)) {
            return false;
        }
        PartnerContact other = (PartnerContact) object;
        if ((this.contactId == null && other.contactId != null) || (this.contactId != null && !this.contactId.equals(other.contactId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerContact[ contactId=" + contactId + " ]";
    }
    
}
