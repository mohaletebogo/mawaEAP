/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

/**
 *
 * @author tebogom
 */
public class User {
    
    private String userID;

    /**
     * Get the value of userID
     *
     * @return the value of userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Set the value of userID
     *
     * @param userID new value of userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    private boolean authenticated;

    /**
     * Get the value of authenticated
     *
     * @return the value of authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Set the value of authenticated
     *
     * @param authenticated new value of authenticated
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
    
        private String passwordStatus;

    /**
     * Get the value of passwordStatus
     *
     * @return the value of passwordStatus
     */
    public String getPasswordStatus() {
        return passwordStatus;
    }

    /**
     * Set the value of passwordStatus
     *
     * @param passwordStatus new value of passwordStatus
     */
    public void setPasswordStatus(String passwordStatus) {
        this.passwordStatus = passwordStatus;
    }


}
