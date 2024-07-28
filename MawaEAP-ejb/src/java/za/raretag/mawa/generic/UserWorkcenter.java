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
public class UserWorkcenter {
    
    private String ID;

    /**
     * Get the value of ID
     *
     * @return the value of ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Set the value of ID
     *
     * @param ID new value of ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    private String Description;

    /**
     * Get the value of Description
     *
     * @return the value of Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Set the value of Description
     *
     * @param Description new value of Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    private int sequence;

    /**
     * Get the value of sequence
     *
     * @return the value of sequence
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * Set the value of sequence
     *
     * @param sequence new value of sequence
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

}
