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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "transaction_trip")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionTrip.findAll", query = "SELECT t FROM TransactionTrip t"),
    @NamedQuery(name = "TransactionTrip.findByTripId", query = "SELECT t FROM TransactionTrip t WHERE t.tripId = :tripId"),
    @NamedQuery(name = "TransactionTrip.findByDriver", query = "SELECT t FROM TransactionTrip t WHERE t.driver = :driver"),
    @NamedQuery(name = "TransactionTrip.findByVehicleId", query = "SELECT t FROM TransactionTrip t WHERE t.vehicleId = :vehicleId"),
    @NamedQuery(name = "TransactionTrip.findByTransactionNo", query = "SELECT t FROM TransactionTrip t WHERE t.transactionNo = :transactionNo"),
    @NamedQuery(name = "TransactionTrip.findByStartPoint", query = "SELECT t FROM TransactionTrip t WHERE t.startPoint = :startPoint"),
    @NamedQuery(name = "TransactionTrip.findByEndPoint", query = "SELECT t FROM TransactionTrip t WHERE t.endPoint = :endPoint"),
    @NamedQuery(name = "TransactionTrip.findByDistance", query = "SELECT t FROM TransactionTrip t WHERE t.distance = :distance"),
    @NamedQuery(name = "TransactionTrip.findByValidFrom", query = "SELECT t FROM TransactionTrip t WHERE t.validFrom = :validFrom"),
    @NamedQuery(name = "TransactionTrip.findByValidTo", query = "SELECT t FROM TransactionTrip t WHERE t.validTo = :validTo")})
public class TransactionTrip implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "trip_id")
    private Integer tripId;
    @Size(max = 10)
    @Column(name = "driver")
    private String driver;
    @Size(max = 10)
    @Column(name = "vehicle_id")
    private String vehicleId;
    @Size(max = 10)
    @Column(name = "transaction_no")
    private String transactionNo;
    @Size(max = 10)
    @Column(name = "start_point")
    private String startPoint;
    @Size(max = 10)
    @Column(name = "end_point")
    private String endPoint;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distance")
    private BigDecimal distance;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    public TransactionTrip() {
    }

    public TransactionTrip(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
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
        hash += (tripId != null ? tripId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionTrip)) {
            return false;
        }
        TransactionTrip other = (TransactionTrip) object;
        if ((this.tripId == null && other.tripId != null) || (this.tripId != null && !this.tripId.equals(other.tripId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.TransactionTrip[ tripId=" + tripId + " ]";
    }
    
}
