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
public class ConfigRoleWorkcenterPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ROLE_TYPE")
    private String roleType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "WORKCENTER")
    private String workcenter;

    public ConfigRoleWorkcenterPK() {
    }

    public ConfigRoleWorkcenterPK(String roleType, String workcenter) {
        this.roleType = roleType;
        this.workcenter = workcenter;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getWorkcenter() {
        return workcenter;
    }

    public void setWorkcenter(String workcenter) {
        this.workcenter = workcenter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleType != null ? roleType.hashCode() : 0);
        hash += (workcenter != null ? workcenter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigRoleWorkcenterPK)) {
            return false;
        }
        ConfigRoleWorkcenterPK other = (ConfigRoleWorkcenterPK) object;
        if ((this.roleType == null && other.roleType != null) || (this.roleType != null && !this.roleType.equals(other.roleType))) {
            return false;
        }
        if ((this.workcenter == null && other.workcenter != null) || (this.workcenter != null && !this.workcenter.equals(other.workcenter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ConfigRoleWorkcenterPK[ roleType=" + roleType + ", workcenter=" + workcenter + " ]";
    }
    
}
