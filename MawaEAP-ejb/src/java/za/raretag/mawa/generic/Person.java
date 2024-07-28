/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tebogom
 */
public class Person {

    private String id;

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }

    private String fullName;

    /**
     * Get the value of fullName
     *
     * @return the value of fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set the value of fullName
     *
     * @param fullName new value of fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    private Date birthDate;

    /**
     * Get the value of birthDate
     *
     * @return the value of birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Set the value of birthDate
     *
     * @param birthDate new value of birthDate
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    private List<Contact> contacts = new ArrayList<>();

    /**
     * Get the value of contacts
     *
     * @return the value of contacts
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * Set the value of contacts
     *
     * @param contacts new value of contacts
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    private List<Identity> identity = new ArrayList<>();

    /**
     * Get the value of identity
     *
     * @return the value of identity
     */
    public List<Identity> getIdentity() {
        return identity;
    }

    /**
     * Set the value of identity
     *
     * @param identity new value of identity
     */
    public void setIdentity(List<Identity> identity) {
        this.identity = identity;
    }

    private List<String> roles = new ArrayList<>();

    /**
     * Get the value of roles
     *
     * @return the value of roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Set the value of roles
     *
     * @param roles new value of roles
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
   
    private List<Order> orders = new ArrayList<>();

    /**
     * Get the value of orders
     *
     * @return the value of orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Set the value of orders
     *
     * @param orders new value of orders
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
 
        private List<Policy> policies = new ArrayList<>();

    /**
     * Get the value of policies
     *
     * @return the value of policies
     */
    public List<Policy> getPolicies() {
        return policies;
    }

    /**
     * Set the value of policies
     *
     * @param policies new value of policies
     */
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }


}
