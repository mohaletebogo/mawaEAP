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
public class OrderQuery {
    
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

    private String startDate;

    /**
     * Get the value of startDate
     *
     * @return the value of startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Set the value of startDate
     *
     * @param startDate new value of startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    private String endDate;

    /**
     * Get the value of endDate
     *
     * @return the value of endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Set the value of endDate
     *
     * @param endDate new value of endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
