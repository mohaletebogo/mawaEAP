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
@Table(name = "change_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChangeLog.findAll", query = "SELECT c FROM ChangeLog c"),
    @NamedQuery(name = "ChangeLog.findByChangeId", query = "SELECT c FROM ChangeLog c WHERE c.changeId = :changeId"),
    @NamedQuery(name = "ChangeLog.findByObjectName", query = "SELECT c FROM ChangeLog c WHERE c.objectName = :objectName"),
    @NamedQuery(name = "ChangeLog.findByObjectId", query = "SELECT c FROM ChangeLog c WHERE c.objectId = :objectId"),
    @NamedQuery(name = "ChangeLog.findByChangeType", query = "SELECT c FROM ChangeLog c WHERE c.changeType = :changeType"),
    @NamedQuery(name = "ChangeLog.findByOldValue", query = "SELECT c FROM ChangeLog c WHERE c.oldValue = :oldValue"),
    @NamedQuery(name = "ChangeLog.findByNewValue", query = "SELECT c FROM ChangeLog c WHERE c.newValue = :newValue"),
    @NamedQuery(name = "ChangeLog.findByChangedBy", query = "SELECT c FROM ChangeLog c WHERE c.changedBy = :changedBy"),
    @NamedQuery(name = "ChangeLog.findByChangeAt", query = "SELECT c FROM ChangeLog c WHERE c.changeAt = :changeAt")})
public class ChangeLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "change_id")
    private Integer changeId;
    @Size(max = 10)
    @Column(name = "object_name")
    private String objectName;
    @Size(max = 10)
    @Column(name = "object_id")
    private String objectId;
    @Size(max = 10)
    @Column(name = "change_type")
    private String changeType;
    @Size(max = 45)
    @Column(name = "old_value")
    private String oldValue;
    @Size(max = 45)
    @Column(name = "new_value")
    private String newValue;
    @Size(max = 10)
    @Column(name = "changed_by")
    private String changedBy;
    @Column(name = "change_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeAt;

    public ChangeLog() {
    }

    public ChangeLog(Integer changeId) {
        this.changeId = changeId;
    }

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public Date getChangeAt() {
        return changeAt;
    }

    public void setChangeAt(Date changeAt) {
        this.changeAt = changeAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (changeId != null ? changeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChangeLog)) {
            return false;
        }
        ChangeLog other = (ChangeLog) object;
        if ((this.changeId == null && other.changeId != null) || (this.changeId != null && !this.changeId.equals(other.changeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ChangeLog[ changeId=" + changeId + " ]";
    }
    
}
