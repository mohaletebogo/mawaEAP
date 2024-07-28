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
@Table(name = "mail_list")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MailList.findAll", query = "SELECT m FROM MailList m"),
    @NamedQuery(name = "MailList.findById", query = "SELECT m FROM MailList m WHERE m.id = :id"),
    @NamedQuery(name = "MailList.findByListId", query = "SELECT m FROM MailList m WHERE m.listId = :listId"),
    @NamedQuery(name = "MailList.findByEmailAddress", query = "SELECT m FROM MailList m WHERE m.emailAddress = :emailAddress"),
    @NamedQuery(name = "MailList.findByValidFrom", query = "SELECT m FROM MailList m WHERE m.validFrom = :validFrom"),
    @NamedQuery(name = "MailList.findByValidTo", query = "SELECT m FROM MailList m WHERE m.validTo = :validTo")})
public class MailList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 10)
    @Column(name = "LIST_ID")
    private String listId;
    @Size(max = 60)
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    public MailList() {
    }

    public MailList(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MailList)) {
            return false;
        }
        MailList other = (MailList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.MailList[ id=" + id + " ]";
    }
    
}
