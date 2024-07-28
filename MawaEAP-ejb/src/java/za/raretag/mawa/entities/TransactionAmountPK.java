/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tebogom
 */
@Embeddable
public class TransactionAmountPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "amount_type")
    private String amountType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "transaction_no")
    private String transactionNo;

    public TransactionAmountPK() {
    }

    public TransactionAmountPK(String amountType, String transactionNo) {
        this.amountType = amountType;
        this.transactionNo = transactionNo;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amountType != null ? amountType.hashCode() : 0);
        hash += (transactionNo != null ? transactionNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionAmountPK)) {
            return false;
        }
        TransactionAmountPK other = (TransactionAmountPK) object;
        if ((this.amountType == null && other.amountType != null) || (this.amountType != null && !this.amountType.equals(other.amountType))) {
            return false;
        }
        if ((this.transactionNo == null && other.transactionNo != null) || (this.transactionNo != null && !this.transactionNo.equals(other.transactionNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionAmountPK[ amountType=" + amountType + ", transactionNo=" + transactionNo + " ]";
    }
    
}
