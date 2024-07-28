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
public class Response {

    public Response(Data value, List<MessageContainer> messages) {
        this.value = value;
        this.messages = messages;
    }

    public Response(List<MessageContainer> messages) {
        this.messages = messages;
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

    
    private List<MessageContainer> messages;

    /**
     * Get the value of messages
     *
     * @return the value of messages
     */
    public List<MessageContainer> getMessages() {
        return messages;
    }

    /**
     * Set the value of messages
     *
     * @param messages new value of messages
     */
    public void setMessages(List<MessageContainer> messages) {
        this.messages = messages;
    }

}
