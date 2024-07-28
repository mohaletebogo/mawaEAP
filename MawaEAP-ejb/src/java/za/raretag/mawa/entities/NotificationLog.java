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
@Table(name = "notification_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificationLog.findAll", query = "SELECT n FROM NotificationLog n"),
    @NamedQuery(name = "NotificationLog.findByLogId", query = "SELECT n FROM NotificationLog n WHERE n.logId = :logId"),
    @NamedQuery(name = "NotificationLog.findByNotificationId", query = "SELECT n FROM NotificationLog n WHERE n.notificationId = :notificationId"),
    @NamedQuery(name = "NotificationLog.findByAction", query = "SELECT n FROM NotificationLog n WHERE n.action = :action"),
    @NamedQuery(name = "NotificationLog.findByResult", query = "SELECT n FROM NotificationLog n WHERE n.result = :result"),
    @NamedQuery(name = "NotificationLog.findByExecutionTime", query = "SELECT n FROM NotificationLog n WHERE n.executionTime = :executionTime")})
public class NotificationLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LOG_ID")
    private Integer logId;
    @Column(name = "NOTIFICATION_ID")
    private Integer notificationId;
    @Size(max = 10)
    @Column(name = "ACTION")
    private String action;
    @Size(max = 10)
    @Column(name = "RESULT")
    private String result;
    @Column(name = "EXECUTION_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executionTime;

    public NotificationLog() {
    }

    public NotificationLog(Integer logId) {
        this.logId = logId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logId != null ? logId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationLog)) {
            return false;
        }
        NotificationLog other = (NotificationLog) object;
        if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.NotificationLog[ logId=" + logId + " ]";
    }
    
}
