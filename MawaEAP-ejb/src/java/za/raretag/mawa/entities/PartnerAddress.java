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
@Table(name = "partner_address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartnerAddress.findAll", query = "SELECT p FROM PartnerAddress p"),
    @NamedQuery(name = "PartnerAddress.findByAddressId", query = "SELECT p FROM PartnerAddress p WHERE p.addressId = :addressId"),
    @NamedQuery(name = "PartnerAddress.findByAddressLine1", query = "SELECT p FROM PartnerAddress p WHERE p.addressLine1 = :addressLine1"),
    @NamedQuery(name = "PartnerAddress.findByAddressLine2", query = "SELECT p FROM PartnerAddress p WHERE p.addressLine2 = :addressLine2"),
    @NamedQuery(name = "PartnerAddress.findByAddressLine3", query = "SELECT p FROM PartnerAddress p WHERE p.addressLine3 = :addressLine3"),
    @NamedQuery(name = "PartnerAddress.findByPostalCode", query = "SELECT p FROM PartnerAddress p WHERE p.postalCode = :postalCode"),
    @NamedQuery(name = "PartnerAddress.findByValidFrom", query = "SELECT p FROM PartnerAddress p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "PartnerAddress.findByValidTo", query = "SELECT p FROM PartnerAddress p WHERE p.validTo = :validTo")})
public class PartnerAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "address_id")
    private Integer addressId;
    @Size(max = 45)
    @Column(name = "address_line1")
    private String addressLine1;
    @Size(max = 45)
    @Column(name = "address_line2")
    private String addressLine2;
    @Size(max = 45)
    @Column(name = "address_line3")
    private String addressLine3;
    @Size(max = 45)
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "address_type", referencedColumnName = "ID")
    @ManyToOne
    private ConfigAddressType addressType;
    @JoinColumn(name = "partner_no", referencedColumnName = "partner_no")
    @ManyToOne
    private Partner partnerNo;

    public PartnerAddress() {
    }

    public PartnerAddress(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public ConfigAddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(ConfigAddressType addressType) {
        this.addressType = addressType;
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
        hash += (addressId != null ? addressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerAddress)) {
            return false;
        }
        PartnerAddress other = (PartnerAddress) object;
        if ((this.addressId == null && other.addressId != null) || (this.addressId != null && !this.addressId.equals(other.addressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.PartnerAddress[ addressId=" + addressId + " ]";
    }
    
}
