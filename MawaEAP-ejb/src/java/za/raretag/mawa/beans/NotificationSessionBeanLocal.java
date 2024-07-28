/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.raretag.mawa.beans;

import java.util.List;
import javax.ejb.Local;
import za.raretag.mawa.generic.MessageContainer;

/**
 *
 * @author tebogom
 */
@Local
public interface NotificationSessionBeanLocal {

    void send();

    List<MessageContainer> create(String recipients,String subject, String body, String type);

    List<String> getRecipients(String mailingList);
    
}
