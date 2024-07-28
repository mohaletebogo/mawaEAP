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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "check_out")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CheckOut.findAll", query = "SELECT c FROM CheckOut c"),
    @NamedQuery(name = "CheckOut.findByCheckingId", query = "SELECT c FROM CheckOut c WHERE c.checkingId = :checkingId"),
    @NamedQuery(name = "CheckOut.findByTerminalId", query = "SELECT c FROM CheckOut c WHERE c.terminalId = :terminalId"),
    @NamedQuery(name = "CheckOut.findByCreatedAt", query = "SELECT c FROM CheckOut c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "CheckOut.findByReceiptFrom", query = "SELECT c FROM CheckOut c WHERE c.receiptFrom = :receiptFrom"),
    @NamedQuery(name = "CheckOut.findByReceiptTo", query = "SELECT c FROM CheckOut c WHERE c.receiptTo = :receiptTo"),
    @NamedQuery(name = "CheckOut.findByCashAmount", query = "SELECT c FROM CheckOut c WHERE c.cashAmount = :cashAmount"),
    @NamedQuery(name = "CheckOut.findByDepositAmount", query = "SELECT c FROM CheckOut c WHERE c.depositAmount = :depositAmount")})
public class CheckOut implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "checking_id")
    private String checkingId;
    @Size(max = 10)
    @Column(name = "terminal_id")
    private String terminalId;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Size(max = 10)
    @Column(name = "receipt_from")
    private String receiptFrom;
    @Size(max = 10)
    @Column(name = "receipt_to")
    private String receiptTo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cash_amount")
    private BigDecimal cashAmount;
    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;
    @JoinColumn(name = "created_by", referencedColumnName = "partner_no")
    @ManyToOne
    private Partner createdBy;
    @JoinColumn(name = "received_by", referencedColumnName = "partner_no")
    @ManyToOne
    private Partner receivedBy;
    @JoinColumn(name = "sales_area", referencedColumnName = "ID")
    @ManyToOne
    private ConfigSalesArea salesArea;

    public CheckOut() {
    }

    public CheckOut(String checkingId) {
        this.checkingId = checkingId;
    }

    public String getCheckingId() {
        return checkingId;
    }

    public void setCheckingId(String checkingId) {
        this.checkingId = checkingId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getReceiptFrom() {
        return receiptFrom;
    }

    public void setReceiptFrom(String receiptFrom) {
        this.receiptFrom = receiptFrom;
    }

    public String getReceiptTo() {
        return receiptTo;
    }

    public void setReceiptTo(String receiptTo) {
        this.receiptTo = receiptTo;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Partner getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Partner createdBy) {
        this.createdBy = createdBy;
    }

    public Partner getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Partner receivedBy) {
        this.receivedBy = receivedBy;
    }

    public ConfigSalesArea getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(ConfigSalesArea salesArea) {
        this.salesArea = salesArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (checkingId != null ? checkingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CheckOut)) {
            return false;
        }
        CheckOut other = (CheckOut) object;
        if ((this.checkingId == null && other.checkingId != null) || (this.checkingId != null && !this.checkingId.equals(other.checkingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.CheckOut[ checkingId=" + checkingId + " ]";
    }
    
}
