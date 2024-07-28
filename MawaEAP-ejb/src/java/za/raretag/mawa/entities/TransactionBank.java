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
@Table(name = "transaction_bank")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionBank.findAll", query = "SELECT t FROM TransactionBank t"),
    @NamedQuery(name = "TransactionBank.findByTransactionBankId", query = "SELECT t FROM TransactionBank t WHERE t.transactionBankId = :transactionBankId"),
    @NamedQuery(name = "TransactionBank.findByAccountNo", query = "SELECT t FROM TransactionBank t WHERE t.accountNo = :accountNo"),
    @NamedQuery(name = "TransactionBank.findByAccountIdNumber", query = "SELECT t FROM TransactionBank t WHERE t.accountIdNumber = :accountIdNumber"),
    @NamedQuery(name = "TransactionBank.findByAccountHolder", query = "SELECT t FROM TransactionBank t WHERE t.accountHolder = :accountHolder"),
    @NamedQuery(name = "TransactionBank.findByBranchCode", query = "SELECT t FROM TransactionBank t WHERE t.branchCode = :branchCode"),
    @NamedQuery(name = "TransactionBank.findByBranchName", query = "SELECT t FROM TransactionBank t WHERE t.branchName = :branchName"),
    @NamedQuery(name = "TransactionBank.findByValidFrom", query = "SELECT t FROM TransactionBank t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionBank.findByValidTo", query = "SELECT t FROM TransactionBank t WHERE t.validTo = :validTo")})
public class TransactionBank implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaction_bank_id")
    private Integer transactionBankId;
    @Size(max = 20)
    @Column(name = "account_no")
    private String accountNo;
    @Size(max = 20)
    @Column(name = "account_id_number")
    private String accountIdNumber;
    @Size(max = 60)
    @Column(name = "account_holder")
    private String accountHolder;
    @Size(max = 10)
    @Column(name = "branch_code")
    private String branchCode;
    @Size(max = 45)
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @JoinColumn(name = "transaction_no", referencedColumnName = "transaction_no")
    @ManyToOne(optional = false)
    private Transaction transactionNo;
    @JoinColumn(name = "bank_name", referencedColumnName = "ID")
    @ManyToOne
    private ConfigBankName bankName;
    @JoinColumn(name = "usage_type", referencedColumnName = "ID")
    @ManyToOne
    private ConfigBankAccountUsage usageType;
    @JoinColumn(name = "account_type", referencedColumnName = "ID")
    @ManyToOne
    private ConfigBankAccountType accountType;

    public TransactionBank() {
    }

    public TransactionBank(Integer transactionBankId) {
        this.transactionBankId = transactionBankId;
    }

    public Integer getTransactionBankId() {
        return transactionBankId;
    }

    public void setTransactionBankId(Integer transactionBankId) {
        this.transactionBankId = transactionBankId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountIdNumber() {
        return accountIdNumber;
    }

    public void setAccountIdNumber(String accountIdNumber) {
        this.accountIdNumber = accountIdNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public Transaction getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Transaction transactionNo) {
        this.transactionNo = transactionNo;
    }

    public ConfigBankName getBankName() {
        return bankName;
    }

    public void setBankName(ConfigBankName bankName) {
        this.bankName = bankName;
    }

    public ConfigBankAccountUsage getUsageType() {
        return usageType;
    }

    public void setUsageType(ConfigBankAccountUsage usageType) {
        this.usageType = usageType;
    }

    public ConfigBankAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(ConfigBankAccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionBankId != null ? transactionBankId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionBank)) {
            return false;
        }
        TransactionBank other = (TransactionBank) object;
        if ((this.transactionBankId == null && other.transactionBankId != null) || (this.transactionBankId != null && !this.transactionBankId.equals(other.transactionBankId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionBank[ transactionBankId=" + transactionBankId + " ]";
    }
    
}
