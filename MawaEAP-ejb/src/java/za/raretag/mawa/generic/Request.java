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
public class Request {

    public Request() {

    }

    public Request(Data value) {
        this.value = value;
    }

    private Data value;

    /**
     * Get the value of data
     *
     * @return the value of data
     */
    public Data getData() {
        return value;
    }

    /**
     * Set the value of data
     *
     * @param value new value of data
     */
    public void setData(Data value) {
        this.value = value;
    }

    private String requester;

    /**
     * Get the value of requester
     *
     * @return the value of requester
     */
    public String getRequester() {
        return requester;
    }

    /**
     * Set the value of requester
     *
     * @param requester new value of requester
     */
    public void setRequester(String requester) {
        this.requester = requester;
    }

}
