/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tebogom
 */
@Entity
@Table(name = "_config_role_workcenter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigRoleWorkcenter.findAll", query = "SELECT c FROM ConfigRoleWorkcenter c"),
    @NamedQuery(name = "ConfigRoleWorkcenter.findByRoleType", query = "SELECT c FROM ConfigRoleWorkcenter c WHERE c.configRoleWorkcenterPK.roleType = :roleType"),
    @NamedQuery(name = "ConfigRoleWorkcenter.findByWorkcenter", query = "SELECT c FROM ConfigRoleWorkcenter c WHERE c.configRoleWorkcenterPK.workcenter = :workcenter"),
    @NamedQuery(name = "ConfigRoleWorkcenter.findBySequence", query = "SELECT c FROM ConfigRoleWorkcenter c WHERE c.sequence = :sequence"),
    @NamedQuery(name = "ConfigRoleWorkcenter.findByActive", query = "SELECT c FROM ConfigRoleWorkcenter c WHERE c.active = :active")})
public class ConfigRoleWorkcenter implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConfigRoleWorkcenterPK configRoleWorkcenterPK;
    @Column(name = "SEQUENCE")
    private Integer sequence;
    @Size(max = 1)
    @Column(name = "ACTIVE")
    private String active;
    @JoinColumn(name = "ROLE_TYPE", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigRoleType configRoleType;
    @JoinColumn(name = "WORKCENTER", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigWorkcenter configWorkcenter;

    public ConfigRoleWorkcenter() {
    }

    public ConfigRoleWorkcenter(ConfigRoleWorkcenterPK configRoleWorkcenterPK) {
        this.configRoleWorkcenterPK = configRoleWorkcenterPK;
    }

    public ConfigRoleWorkcenter(String roleType, String workcenter) {
        this.configRoleWorkcenterPK = new ConfigRoleWorkcenterPK(roleType, workcenter);
    }

    public ConfigRoleWorkcenterPK getConfigRoleWorkcenterPK() {
        return configRoleWorkcenterPK;
    }

    public void setConfigRoleWorkcenterPK(ConfigRoleWorkcenterPK configRoleWorkcenterPK) {
        this.configRoleWorkcenterPK = configRoleWorkcenterPK;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public ConfigRoleType getConfigRoleType() {
        return configRoleType;
    }

    public void setConfigRoleType(ConfigRoleType configRoleType) {
        this.configRoleType = configRoleType;
    }

    public ConfigWorkcenter getConfigWorkcenter() {
        return configWorkcenter;
    }

    public void setConfigWorkcenter(ConfigWorkcenter configWorkcenter) {
        this.configWorkcenter = configWorkcenter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configRoleWorkcenterPK != null ? configRoleWorkcenterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigRoleWorkcenter)) {
            return false;
        }
        ConfigRoleWorkcenter other = (ConfigRoleWorkcenter) object;
        if ((this.configRoleWorkcenterPK == null && other.configRoleWorkcenterPK != null) || (this.configRoleWorkcenterPK != null && !this.configRoleWorkcenterPK.equals(other.configRoleWorkcenterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ConfigRoleWorkcenter[ configRoleWorkcenterPK=" + configRoleWorkcenterPK + " ]";
    }
    
}
