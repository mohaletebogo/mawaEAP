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
@Table(name = "_config_setting")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigSetting.findAll", query = "SELECT c FROM ConfigSetting c"),
    @NamedQuery(name = "ConfigSetting.findBySetting", query = "SELECT c FROM ConfigSetting c WHERE c.configSettingPK.setting = :setting"),
    @NamedQuery(name = "ConfigSetting.findByAttribute", query = "SELECT c FROM ConfigSetting c WHERE c.configSettingPK.attribute = :attribute"),
    @NamedQuery(name = "ConfigSetting.findByValue", query = "SELECT c FROM ConfigSetting c WHERE c.value = :value")})
public class ConfigSetting implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConfigSettingPK configSettingPK;
    @Size(max = 60)
    @Column(name = "VALUE")
    private String value;

    public ConfigSetting() {
    }

    public ConfigSetting(ConfigSettingPK configSettingPK) {
        this.configSettingPK = configSettingPK;
    }

    public ConfigSetting(String setting, String attribute) {
        this.configSettingPK = new ConfigSettingPK(setting, attribute);
    }

    public ConfigSettingPK getConfigSettingPK() {
        return configSettingPK;
    }

    public void setConfigSettingPK(ConfigSettingPK configSettingPK) {
        this.configSettingPK = configSettingPK;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configSettingPK != null ? configSettingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigSetting)) {
            return false;
        }
        ConfigSetting other = (ConfigSetting) object;
        if ((this.configSettingPK == null && other.configSettingPK != null) || (this.configSettingPK != null && !this.configSettingPK.equals(other.configSettingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.ConfigSetting[ configSettingPK=" + configSettingPK + " ]";
    }
    
}
