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
public class Note {
     
    private String recipient;

    /**
     * Get the value of recipient
     *
     * @return the value of recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Set the value of recipient
     *
     * @param recipient new value of recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    private String subject;

    /**
     * Get the value of subject
     *
     * @return the value of subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the value of subject
     *
     * @param subject new value of subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String body;

    /**
     * Get the value of body
     *
     * @return the value of body
     */
    public String getBody() {
        return body;
    }

    /**
     * Set the value of body
     *
     * @param body new value of body
     */
    public void setBody(String body) {
        this.body = body;
    }

}
