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
import javax.persistence.Lob;
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
@Table(name = "usr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usr.findAll", query = "SELECT u FROM Usr u"),
    @NamedQuery(name = "Usr.findByUserId", query = "SELECT u FROM Usr u WHERE u.userId = :userId"),
    @NamedQuery(name = "Usr.findByPartnerNo", query = "SELECT u FROM Usr u WHERE u.partnerNo = :partnerNo"),
    @NamedQuery(name = "Usr.findByStatus", query = "SELECT u FROM Usr u WHERE u.status = :status"),
    @NamedQuery(name = "Usr.findByValidFrom", query = "SELECT u FROM Usr u WHERE u.validFrom = :validFrom"),
    @NamedQuery(name = "Usr.findByValidTo", query = "SELECT u FROM Usr u WHERE u.validTo = :validTo")})
public class Usr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "partner_no")
    private String partnerNo;
    @Lob
    @Column(name = "password")
    private byte[] password;
    @Lob
    @Column(name = "temp_password")
    private byte[] tempPassword;
    @Size(max = 10)
    @Column(name = "status")
    private String status;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    public Usr() {
    }

    public Usr(String userId) {
        this.userId = userId;
    }

    public Usr(String userId, String partnerNo) {
        this.userId = userId;
        this.partnerNo = partnerNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(byte[] tempPassword) {
        this.tempPassword = tempPassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usr)) {
            return false;
        }
        Usr other = (Usr) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.Usr[ userId=" + userId + " ]";
    }
    
}
