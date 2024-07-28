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
public class TransactionRelationPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "transaction_no1")
    private String transactionNo1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "transaction_no2")
    private String transactionNo2;

    public TransactionRelationPK() {
    }

    public TransactionRelationPK(String type, String transactionNo1, String transactionNo2) {
        this.type = type;
        this.transactionNo1 = transactionNo1;
        this.transactionNo2 = transactionNo2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionNo1() {
        return transactionNo1;
    }

    public void setTransactionNo1(String transactionNo1) {
        this.transactionNo1 = transactionNo1;
    }

    public String getTransactionNo2() {
        return transactionNo2;
    }

    public void setTransactionNo2(String transactionNo2) {
        this.transactionNo2 = transactionNo2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (type != null ? type.hashCode() : 0);
        hash += (transactionNo1 != null ? transactionNo1.hashCode() : 0);
        hash += (transactionNo2 != null ? transactionNo2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionRelationPK)) {
            return false;
        }
        TransactionRelationPK other = (TransactionRelationPK) object;
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        if ((this.transactionNo1 == null && other.transactionNo1 != null) || (this.transactionNo1 != null && !this.transactionNo1.equals(other.transactionNo1))) {
            return false;
        }
        if ((this.transactionNo2 == null && other.transactionNo2 != null) || (this.transactionNo2 != null && !this.transactionNo2.equals(other.transactionNo2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionRelationPK[ type=" + type + ", transactionNo1=" + transactionNo1 + ", transactionNo2=" + transactionNo2 + " ]";
    }
    
}
