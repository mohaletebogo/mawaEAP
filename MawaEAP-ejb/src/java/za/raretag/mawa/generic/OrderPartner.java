/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.util.Iterator;
import za.raretag.mawa.entities.PartnerIdentity;
import za.raretag.mawa.entities.TransactionPartner;

/**
 *
 * @author tebogom
 */
public class OrderPartner {

    public OrderPartner() {
    }

    public OrderPartner(TransactionPartner partner) {
        this.type = partner.getConfigPartnerFunction().getId();
        this.typeDescription = partner.getConfigPartnerFunction().getDescription();
        this.partnerNo = partner.getPartner().getPartnerNo();
        this.lastName = partner.getPartner().getName1();
        this.firstName = partner.getPartner().getName2();
        this.middleName = partner.getPartner().getName3();
        this.fullname = Conversion.getPartnerName(partner.getPartner());
        
        Iterator<PartnerIdentity> identList = partner.getPartner().getPartnerIdentityCollection().iterator();
        PartnerIdentity ident = identList.next();
        if (ident != null){
            this.idNumber = ident.getPartnerIdentityPK().getIdNumber();
        }
    }

    private String orderNo;

    /**
     * Get the value of orderNo
     *
     * @return the value of orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Set the value of orderNo
     *
     * @param orderNo new value of orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    private String partnerNo;

    /**
     * Get the value of partnerNo
     *
     * @return the value of partnerNo
     */
    public String getPartnerNo() {
        return partnerNo;
    }

    /**
     * Set the value of partnerNo
     *
     * @param partnerNo new value of partnerNo
     */
    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    private String idNumber;

    /**
     * Get the value of idNumber
     *
     * @return the value of idNumber
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Set the value of idNumber
     *
     * @param idNumber new value of idNumber
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    private String fullname;

    /**
     * Get the value of fullname
     *
     * @return the value of fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set the value of fullname
     *
     * @param fullname new value of fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    private String type;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(String type) {
        this.type = type;
    }

    private String typeDescription;

    /**
     * Get the value of typeDescription
     *
     * @return the value of typeDescription
     */
    public String getTypeDescription() {
        return typeDescription;
    }

    /**
     * Set the value of typeDescription
     *
     * @param typeDescription new value of typeDescription
     */
    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    private String lastName;

    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of surname
     *
     * @param lastName new value of surname
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String firstName;

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String middleName;

    /**
     * Get the value of middleName
     *
     * @return the value of middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Set the value of middleName
     *
     * @param middleName new value of middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    private String cellNumber;

    /**
     * Get the value of cellNumber
     *
     * @return the value of cellNumber
     */
    public String getCellNumber() {
        return cellNumber;
    }

    /**
     * Set the value of contactNumber
     *
     * @param cellNumber new value of cellNumber
     */
    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    private String email;

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
