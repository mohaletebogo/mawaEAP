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
public class ConfigSettingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SETTING")
    private String setting;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ATTRIBUTE")
    private String attribute;

    public ConfigSettingPK() {
    }

    public ConfigSettingPK(String setting, String attribute) {
        this.setting = setting;
        this.attribute = attribute;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (setting != null ? setting.hashCode() : 0);
        hash += (attribute != null ? attribute.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigSettingPK)) {
            return false;
        }
        ConfigSettingPK other = (ConfigSettingPK) object;
        if ((this.setting == null && other.setting != null) || (this.setting != null && !this.setting.equals(other.setting))) {
            return false;
        }
        if ((this.attribute == null && other.attribute != null) || (this.attribute != null && !this.attribute.equals(other.attribute))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ConfigSettingPK[ setting=" + setting + ", attribute=" + attribute + " ]";
    }
    
}
