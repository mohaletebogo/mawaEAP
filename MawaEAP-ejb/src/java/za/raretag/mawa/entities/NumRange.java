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
@Table(name = "num_range")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NumRange.findAll", query = "SELECT n FROM NumRange n"),
    @NamedQuery(name = "NumRange.findByNumRangeId", query = "SELECT n FROM NumRange n WHERE n.numRangeId = :numRangeId"),
    @NamedQuery(name = "NumRange.findByObjectType", query = "SELECT n FROM NumRange n WHERE n.objectType = :objectType"),
    @NamedQuery(name = "NumRange.findByCurrentNumber", query = "SELECT n FROM NumRange n WHERE n.currentNumber = :currentNumber"),
    @NamedQuery(name = "NumRange.findByStartNumber", query = "SELECT n FROM NumRange n WHERE n.startNumber = :startNumber"),
    @NamedQuery(name = "NumRange.findByEndNumber", query = "SELECT n FROM NumRange n WHERE n.endNumber = :endNumber"),
    @NamedQuery(name = "NumRange.findByValidFrom", query = "SELECT n FROM NumRange n WHERE n.validFrom = :validFrom"),
    @NamedQuery(name = "NumRange.findByValidTo", query = "SELECT n FROM NumRange n WHERE n.validTo = :validTo")})
public class NumRange implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NUM_RANGE_ID")
    private Integer numRangeId;
    @Size(max = 20)
    @Column(name = "OBJECT_TYPE")
    private String objectType;
    @Size(max = 10)
    @Column(name = "CURRENT_NUMBER")
    private String currentNumber;
    @Size(max = 10)
    @Column(name = "START_NUMBER")
    private String startNumber;
    @Size(max = 10)
    @Column(name = "END_NUMBER")
    private String endNumber;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    public NumRange() {
    }

    public NumRange(Integer numRangeId) {
        this.numRangeId = numRangeId;
    }

    public Integer getNumRangeId() {
        return numRangeId;
    }

    public void setNumRangeId(Integer numRangeId) {
        this.numRangeId = numRangeId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(String startNumber) {
        this.startNumber = startNumber;
    }

    public String getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(String endNumber) {
        this.endNumber = endNumber;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numRangeId != null ? numRangeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NumRange)) {
            return false;
        }
        NumRange other = (NumRange) object;
        if ((this.numRangeId == null && other.numRangeId != null) || (this.numRangeId != null && !this.numRangeId.equals(other.numRangeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.NumRange[ numRangeId=" + numRangeId + " ]";
    }
    
}
