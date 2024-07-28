/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "direct_deposit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DirectDeposit.findAll", query = "SELECT d FROM DirectDeposit d"),
    @NamedQuery(name = "DirectDeposit.findById", query = "SELECT d FROM DirectDeposit d WHERE d.id = :id"),
    @NamedQuery(name = "DirectDeposit.findByCheckingId", query = "SELECT d FROM DirectDeposit d WHERE d.checkingId = :checkingId"),
    @NamedQuery(name = "DirectDeposit.findByDepositDate", query = "SELECT d FROM DirectDeposit d WHERE d.depositDate = :depositDate"),
    @NamedQuery(name = "DirectDeposit.findByDepositReference", query = "SELECT d FROM DirectDeposit d WHERE d.depositReference = :depositReference"),
    @NamedQuery(name = "DirectDeposit.findByAmount", query = "SELECT d FROM DirectDeposit d WHERE d.amount = :amount")})
public class DirectDeposit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 10)
    @Column(name = "CHECKING_ID")
    private String checkingId;
    @Column(name = "DEPOSIT_DATE")
    @Temporal(TemporalType.DATE)
    private Date depositDate;
    @Size(max = 45)
    @Column(name = "DEPOSIT_REFERENCE")
    private String depositReference;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    public DirectDeposit() {
    }

    public DirectDeposit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckingId() {
        return checkingId;
    }

    public void setCheckingId(String checkingId) {
        this.checkingId = checkingId;
    }

    public Date getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    public String getDepositReference() {
        return depositReference;
    }

    public void setDepositReference(String depositReference) {
        this.depositReference = depositReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        if (!(object instanceof DirectDeposit)) {
            return false;
        }
        DirectDeposit other = (DirectDeposit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.DirectDeposit[ id=" + id + " ]";
    }
    
}
