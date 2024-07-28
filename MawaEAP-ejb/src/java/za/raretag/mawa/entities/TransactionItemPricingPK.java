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
public class TransactionItemPricingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "item_id")
    private int itemId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pricing_type")
    private String pricingType;

    public TransactionItemPricingPK() {
    }

    public TransactionItemPricingPK(int itemId, String pricingType) {
        this.itemId = itemId;
        this.pricingType = pricingType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getPricingType() {
        return pricingType;
    }

    public void setPricingType(String pricingType) {
        this.pricingType = pricingType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) itemId;
        hash += (pricingType != null ? pricingType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionItemPricingPK)) {
            return false;
        }
        TransactionItemPricingPK other = (TransactionItemPricingPK) object;
        if (this.itemId != other.itemId) {
            return false;
        }
        if ((this.pricingType == null && other.pricingType != null) || (this.pricingType != null && !this.pricingType.equals(other.pricingType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionItemPricingPK[ itemId=" + itemId + ", pricingType=" + pricingType + " ]";
    }
    
}
