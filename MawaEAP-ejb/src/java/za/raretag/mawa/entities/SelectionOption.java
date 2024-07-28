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
@Table(name = "selection_option")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SelectionOption.findAll", query = "SELECT s FROM SelectionOption s"),
    @NamedQuery(name = "SelectionOption.findByOptionId", query = "SELECT s FROM SelectionOption s WHERE s.optionId = :optionId"),
    @NamedQuery(name = "SelectionOption.findByFieldName", query = "SELECT s FROM SelectionOption s WHERE s.fieldName = :fieldName"),
    @NamedQuery(name = "SelectionOption.findByOptionCode", query = "SELECT s FROM SelectionOption s WHERE s.optionCode = :optionCode"),
    @NamedQuery(name = "SelectionOption.findByDescription", query = "SELECT s FROM SelectionOption s WHERE s.description = :description"),
    @NamedQuery(name = "SelectionOption.findByValidFrom", query = "SELECT s FROM SelectionOption s WHERE s.validFrom = :validFrom"),
    @NamedQuery(name = "SelectionOption.findByValidTo", query = "SELECT s FROM SelectionOption s WHERE s.validTo = :validTo")})
public class SelectionOption implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "option_id")
    private Integer optionId;
    @Size(max = 10)
    @Column(name = "field_name")
    private String fieldName;
    @Size(max = 10)
    @Column(name = "option_code")
    private String optionCode;
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    public SelectionOption() {
    }

    public SelectionOption(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOptionCode() {
        return optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (optionId != null ? optionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SelectionOption)) {
            return false;
        }
        SelectionOption other = (SelectionOption) object;
        if ((this.optionId == null && other.optionId != null) || (this.optionId != null && !this.optionId.equals(other.optionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.raretag.mawa.entities.SelectionOption[ optionId=" + optionId + " ]";
    }
    
}
