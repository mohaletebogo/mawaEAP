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
import javax.persistence.CascadeType;
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
@Table(name = "partner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partner.findAll", query = "SELECT p FROM Partner p"),
    @NamedQuery(name = "Partner.findByPartnerNo", query = "SELECT p FROM Partner p WHERE p.partnerNo = :partnerNo"),
    @NamedQuery(name = "Partner.findByType", query = "SELECT p FROM Partner p WHERE p.type = :type"),
    @NamedQuery(name = "Partner.findByName1", query = "SELECT p FROM Partner p WHERE p.name1 = :name1"),
    @NamedQuery(name = "Partner.findByName2", query = "SELECT p FROM Partner p WHERE p.name2 = :name2"),
    @NamedQuery(name = "Partner.findByName3", query = "SELECT p FROM Partner p WHERE p.name3 = :name3"),
    @NamedQuery(name = "Partner.findByName4", query = "SELECT p FROM Partner p WHERE p.name4 = :name4"),
    @NamedQuery(name = "Partner.findByValidFrom", query = "SELECT p FROM Partner p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "Partner.findByValidTo", query = "SELECT p FROM Partner p WHERE p.validTo = :validTo"),
    @NamedQuery(name = "Partner.findByCreatedBy", query = "SELECT p FROM Partner p WHERE p.createdBy = :createdBy")})
public class Partner implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "partner_no")
    private String partnerNo;
    @Size(max = 10)
    @Column(name = "type")
    private String type;
    @Size(max = 45)
    @Column(name = "name1")
    private String name1;
    @Size(max = 45)
    @Column(name = "name2")
    private String name2;
    @Size(max = 45)
    @Column(name = "name3")
    private String name3;
    @Size(max = 45)
    @Column(name = "name4")
    private String name4;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @Size(max = 10)
    @Column(name = "created_by")
    private String createdBy;
    @OneToMany(mappedBy = "createdBy")
    private Collection<TransactionPayment> transactionPaymentCollection;
    @OneToMany(mappedBy = "receivedBy")
    private Collection<TransactionPayment> transactionPaymentCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner")
    private Collection<PartnerRelation> partnerRelationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner1")
    private Collection<PartnerRelation> partnerRelationCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner")
    private Collection<PartnerRole> partnerRoleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner")
    private Collection<TransactionPartner> transactionPartnerCollection;
    @OneToMany(mappedBy = "partnerNo")
    private Collection<PartnerAddress> partnerAddressCollection;
    @OneToMany(mappedBy = "createdBy")
    private Collection<CheckOut> checkOutCollection;
    @OneToMany(mappedBy = "receivedBy")
    private Collection<CheckOut> checkOutCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner")
    private Collection<PartnerAttribute> partnerAttributeCollection;
    @OneToMany(mappedBy = "partnerNo")
    private Collection<PartnerContact> partnerContactCollection;
    @OneToMany(mappedBy = "partnerNo")
    private Collection<PartnerIdentity> partnerIdentityCollection;

    public Partner() {
    }

    public Partner(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @XmlTransient
    public Collection<TransactionPayment> getTransactionPaymentCollection() {
        return transactionPaymentCollection;
    }

    public void setTransactionPaymentCollection(Collection<TransactionPayment> transactionPaymentCollection) {
        this.transactionPaymentCollection = transactionPaymentCollection;
    }

    @XmlTransient
    public Collection<TransactionPayment> getTransactionPaymentCollection1() {
        return transactionPaymentCollection1;
    }

    public void setTransactionPaymentCollection1(Collection<TransactionPayment> transactionPaymentCollection1) {
        this.transactionPaymentCollection1 = transactionPaymentCollection1;
    }

    @XmlTransient
    public Collection<PartnerRelation> getPartnerRelationCollection() {
        return partnerRelationCollection;
    }

    public void setPartnerRelationCollection(Collection<PartnerRelation> partnerRelationCollection) {
        this.partnerRelationCollection = partnerRelationCollection;
    }

    @XmlTransient
    public Collection<PartnerRelation> getPartnerRelationCollection1() {
        return partnerRelationCollection1;
    }

    public void setPartnerRelationCollection1(Collection<PartnerRelation> partnerRelationCollection1) {
        this.partnerRelationCollection1 = partnerRelationCollection1;
    }

    @XmlTransient
    public Collection<PartnerRole> getPartnerRoleCollection() {
        return partnerRoleCollection;
    }

    public void setPartnerRoleCollection(Collection<PartnerRole> partnerRoleCollection) {
        this.partnerRoleCollection = partnerRoleCollection;
    }

    @XmlTransient
    public Collection<TransactionPartner> getTransactionPartnerCollection() {
        return transactionPartnerCollection;
    }

    public void setTransactionPartnerCollection(Collection<TransactionPartner> transactionPartnerCollection) {
        this.transactionPartnerCollection = transactionPartnerCollection;
    }

    @XmlTransient
    public Collection<PartnerAddress> getPartnerAddressCollection() {
        return partnerAddressCollection;
    }

    public void setPartnerAddressCollection(Collection<PartnerAddress> partnerAddressCollection) {
        this.partnerAddressCollection = partnerAddressCollection;
    }

    @XmlTransient
    public Collection<CheckOut> getCheckOutCollection() {
        return checkOutCollection;
    }

    public void setCheckOutCollection(Collection<CheckOut> checkOutCollection) {
        this.checkOutCollection = checkOutCollection;
    }

    @XmlTransient
    public Collection<CheckOut> getCheckOutCollection1() {
        return checkOutCollection1;
    }

    public void setCheckOutCollection1(Collection<CheckOut> checkOutCollection1) {
        this.checkOutCollection1 = checkOutCollection1;
    }

    @XmlTransient
    public Collection<PartnerAttribute> getPartnerAttributeCollection() {
        return partnerAttributeCollection;
    }

    public void setPartnerAttributeCollection(Collection<PartnerAttribute> partnerAttributeCollection) {
        this.partnerAttributeCollection = partnerAttributeCollection;
    }

    @XmlTransient
    public Collection<PartnerContact> getPartnerContactCollection() {
        return partnerContactCollection;
    }

    public void setPartnerContactCollection(Collection<PartnerContact> partnerContactCollection) {
        this.partnerContactCollection = partnerContactCollection;
    }

    @XmlTransient
    public Collection<PartnerIdentity> getPartnerIdentityCollection() {
        return partnerIdentityCollection;
    }

    public void setPartnerIdentityCollection(Collection<PartnerIdentity> partnerIdentityCollection) {
        this.partnerIdentityCollection = partnerIdentityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerNo != null ? partnerNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partner)) {
            return false;
        }
        Partner other = (Partner) object;
        if ((this.partnerNo == null && other.partnerNo != null) || (this.partnerNo != null && !this.partnerNo.equals(other.partnerNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.Partner[ partnerNo=" + partnerNo + " ]";
    }
    
}
