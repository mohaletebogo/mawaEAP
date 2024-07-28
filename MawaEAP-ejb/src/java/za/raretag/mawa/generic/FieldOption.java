/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.raretag.mawa.generic;

import java.util.List;

/**
 *
 * @author tebogom
 */
public class FieldOption {
  
    private String fieldName;

    /**
     * Get the value of fieldName
     *
     * @return the value of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Set the value of fieldName
     *
     * @param fieldName new value of fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    private String code;

    /**
     * Get the value of Id
     *
     * @return the value of Id
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of Id
     *
     * @param code new value of Id
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String description;

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public FieldOption(String fieldName,String code, String description) {
        this.fieldName = fieldName;
        this.code = code;
        this.description = description;
       // this.status = status;
    }

 
    private String status;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }


}
