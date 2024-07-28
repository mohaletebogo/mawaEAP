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
public class TransactionPartnerPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "partner_type")
    private String partnerType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "transaction_no")
    private String transactionNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "partner_no")
    private String partnerNo;

    public TransactionPartnerPK() {
    }

    public TransactionPartnerPK(String partnerType, String transactionNo, String partnerNo) {
        this.partnerType = partnerType;
        this.transactionNo = transactionNo;
        this.partnerNo = partnerNo;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partnerType != null ? partnerType.hashCode() : 0);
        hash += (transactionNo != null ? transactionNo.hashCode() : 0);
        hash += (partnerNo != null ? partnerNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionPartnerPK)) {
            return false;
        }
        TransactionPartnerPK other = (TransactionPartnerPK) object;
        if ((this.partnerType == null && other.partnerType != null) || (this.partnerType != null && !this.partnerType.equals(other.partnerType))) {
            return false;
        }
        if ((this.transactionNo == null && other.transactionNo != null) || (this.transactionNo != null && !this.transactionNo.equals(other.transactionNo))) {
            return false;
        }
        if ((this.partnerNo == null && other.partnerNo != null) || (this.partnerNo != null && !this.partnerNo.equals(other.partnerNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionPartnerPK[ partnerType=" + partnerType + ", transactionNo=" + transactionNo + ", partnerNo=" + partnerNo + " ]";
    }
    
}
