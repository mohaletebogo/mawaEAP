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
public class TransactionStatusPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "transaction_no")
    private String transactionNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status")
    private String status;

    public TransactionStatusPK() {
    }

    public TransactionStatusPK(String transactionNo, String status) {
        this.transactionNo = transactionNo;
        this.status = status;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionNo != null ? transactionNo.hashCode() : 0);
        hash += (status != null ? status.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionStatusPK)) {
            return false;
        }
        TransactionStatusPK other = (TransactionStatusPK) object;
        if ((this.transactionNo == null && other.transactionNo != null) || (this.transactionNo != null && !this.transactionNo.equals(other.transactionNo))) {
            return false;
        }
        if ((this.status == null && other.status != null) || (this.status != null && !this.status.equals(other.status))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionStatusPK[ transactionNo=" + transactionNo + ", status=" + status + " ]";
    }
    
}
