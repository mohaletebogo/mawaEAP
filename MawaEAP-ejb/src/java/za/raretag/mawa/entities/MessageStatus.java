/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "message_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageStatus.findAll", query = "SELECT m FROM MessageStatus m"),
    @NamedQuery(name = "MessageStatus.findByMessageId", query = "SELECT m FROM MessageStatus m WHERE m.messageStatusPK.messageId = :messageId"),
    @NamedQuery(name = "MessageStatus.findByStatus", query = "SELECT m FROM MessageStatus m WHERE m.messageStatusPK.status = :status"),
    @NamedQuery(name = "MessageStatus.findBySet", query = "SELECT m FROM MessageStatus m WHERE m.set = :set"),
    @NamedQuery(name = "MessageStatus.findBySetAt", query = "SELECT m FROM MessageStatus m WHERE m.setAt = :setAt")})
public class MessageStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MessageStatusPK messageStatusPK;
    @Column(name = "SET")
    private Integer set;
    @Column(name = "SET_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date setAt;

    public MessageStatus() {
    }

    public MessageStatus(MessageStatusPK messageStatusPK) {
        this.messageStatusPK = messageStatusPK;
    }

    public MessageStatus(int messageId, String status) {
        this.messageStatusPK = new MessageStatusPK(messageId, status);
    }

    public MessageStatusPK getMessageStatusPK() {
        return messageStatusPK;
    }

    public void setMessageStatusPK(MessageStatusPK messageStatusPK) {
        this.messageStatusPK = messageStatusPK;
    }

    public Integer getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

    public Date getSetAt() {
        return setAt;
    }

    public void setSetAt(Date setAt) {
        this.setAt = setAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageStatusPK != null ? messageStatusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageStatus)) {
            return false;
        }
        MessageStatus other = (MessageStatus) object;
        if ((this.messageStatusPK == null && other.messageStatusPK != null) || (this.messageStatusPK != null && !this.messageStatusPK.equals(other.messageStatusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.MessageStatus[ messageStatusPK=" + messageStatusPK + " ]";
    }
    
}
