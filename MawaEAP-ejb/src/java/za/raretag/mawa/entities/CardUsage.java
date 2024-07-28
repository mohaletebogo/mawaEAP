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
@Table(name = "card_usage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CardUsage.findAll", query = "SELECT c FROM CardUsage c"),
    @NamedQuery(name = "CardUsage.findByCardUsageId", query = "SELECT c FROM CardUsage c WHERE c.cardUsageId = :cardUsageId"),
    @NamedQuery(name = "CardUsage.findBySwipeDate", query = "SELECT c FROM CardUsage c WHERE c.swipeDate = :swipeDate"),
    @NamedQuery(name = "CardUsage.findByCardNo", query = "SELECT c FROM CardUsage c WHERE c.cardNo = :cardNo"),
    @NamedQuery(name = "CardUsage.findBySupplier", query = "SELECT c FROM CardUsage c WHERE c.supplier = :supplier"),
    @NamedQuery(name = "CardUsage.findByAmount", query = "SELECT c FROM CardUsage c WHERE c.amount = :amount")})
public class CardUsage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "card_usage_id")
    private Integer cardUsageId;
    @Column(name = "swipe_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date swipeDate;
    @Size(max = 10)
    @Column(name = "card_no")
    private String cardNo;
    @Size(max = 45)
    @Column(name = "supplier")
    private String supplier;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private BigDecimal amount;

    public CardUsage() {
    }

    public CardUsage(Integer cardUsageId) {
        this.cardUsageId = cardUsageId;
    }

    public Integer getCardUsageId() {
        return cardUsageId;
    }

    public void setCardUsageId(Integer cardUsageId) {
        this.cardUsageId = cardUsageId;
    }

    public Date getSwipeDate() {
        return swipeDate;
    }

    public void setSwipeDate(Date swipeDate) {
        this.swipeDate = swipeDate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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
        hash += (cardUsageId != null ? cardUsageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CardUsage)) {
            return false;
        }
        CardUsage other = (CardUsage) object;
        if ((this.cardUsageId == null && other.cardUsageId != null) || (this.cardUsageId != null && !this.cardUsageId.equals(other.cardUsageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.CardUsage[ cardUsageId=" + cardUsageId + " ]";
    }
    
}
