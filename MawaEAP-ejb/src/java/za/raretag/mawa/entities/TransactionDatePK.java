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
public class TransactionDatePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "transaction_no")
    private String transactionNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "date_type")
    private String dateType;

    public TransactionDatePK() {
    }

    public TransactionDatePK(String transactionNo, String dateType) {
        this.transactionNo = transactionNo;
        this.dateType = dateType;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionNo != null ? transactionNo.hashCode() : 0);
        hash += (dateType != null ? dateType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionDatePK)) {
            return false;
        }
        TransactionDatePK other = (TransactionDatePK) object;
        if ((this.transactionNo == null && other.transactionNo != null) || (this.transactionNo != null && !this.transactionNo.equals(other.transactionNo))) {
            return false;
        }
        if ((this.dateType == null && other.dateType != null) || (this.dateType != null && !this.dateType.equals(other.dateType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionDatePK[ transactionNo=" + transactionNo + ", dateType=" + dateType + " ]";
    }
    
}
