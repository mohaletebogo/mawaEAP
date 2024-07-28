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
@Table(name = "transaction_payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionPayment.findAll", query = "SELECT t FROM TransactionPayment t"),
    @NamedQuery(name = "TransactionPayment.findByPaymentNo", query = "SELECT t FROM TransactionPayment t WHERE t.paymentNo = :paymentNo"),
    @NamedQuery(name = "TransactionPayment.findByPaymentDate", query = "SELECT t FROM TransactionPayment t WHERE t.paymentDate = :paymentDate"),
    @NamedQuery(name = "TransactionPayment.findByAmount", query = "SELECT t FROM TransactionPayment t WHERE t.amount = :amount"),
    @NamedQuery(name = "TransactionPayment.findByCreatedAt", query = "SELECT t FROM TransactionPayment t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "TransactionPayment.findByExternalReceiptNo", query = "SELECT t FROM TransactionPayment t WHERE t.externalReceiptNo = :externalReceiptNo"),
    @NamedQuery(name = "TransactionPayment.findByTerminalId", query = "SELECT t FROM TransactionPayment t WHERE t.terminalId = :terminalId"),
    @NamedQuery(name = "TransactionPayment.findByCheckingId", query = "SELECT t FROM TransactionPayment t WHERE t.checkingId = :checkingId")})
public class TransactionPayment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "payment_no")
    private Integer paymentNo;
    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Size(max = 10)
    @Column(name = "external_receipt_no")
    private String externalReceiptNo;
    @Size(max = 10)
    @Column(name = "terminal_id")
    private String terminalId;
    @Size(max = 10)
    @Column(name = "checking_id")
    private String checkingId;
    @JoinColumn(name = "created_by", referencedColumnName = "partner_no")
    @ManyToOne
    private Partner createdBy;
    @JoinColumn(name = "received_by", referencedColumnName = "partner_no")
    @ManyToOne
    private Partner receivedBy;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no")
    @ManyToOne
    private Transaction transactionNo;
    @JoinColumn(name = "payment_type", referencedColumnName = "ID")
    @ManyToOne
    private ConfigPaymentType paymentType;

    public TransactionPayment() {
    }

    public TransactionPayment(Integer paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Integer getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(Integer paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getExternalReceiptNo() {
        return externalReceiptNo;
    }

    public void setExternalReceiptNo(String externalReceiptNo) {
        this.externalReceiptNo = externalReceiptNo;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getCheckingId() {
        return checkingId;
    }

    public void setCheckingId(String checkingId) {
        this.checkingId = checkingId;
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

    public Transaction getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Transaction transactionNo) {
        this.transactionNo = transactionNo;
    }

    public ConfigPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(ConfigPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentNo != null ? paymentNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionPayment)) {
            return false;
        }
        TransactionPayment other = (TransactionPayment) object;
        if ((this.paymentNo == null && other.paymentNo != null) || (this.paymentNo != null && !this.paymentNo.equals(other.paymentNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionPayment[ paymentNo=" + paymentNo + " ]";
    }
    
}
