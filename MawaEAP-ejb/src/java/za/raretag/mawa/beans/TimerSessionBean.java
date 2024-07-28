/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TimerService;
import javax.mail.Message;
import javax.mail.MessagingException;
import za.raretag.mawa.generic.Email;
import za.raretag.mawa.generic.Swipe;

/**
 *
 * @author tebogom
 */
@Singleton
public class TimerSessionBean implements TimerSessionBeanLocal {
    @EJB
    private CardUsageBeanLocal cardUsageBean;

    @EJB
    private NotificationSessionBeanLocal notificationSessionBean;
    @Resource
    TimerService timerService;

    @Schedule(second = "30", minute = "*", hour = "*")
    @Override
    public void myTimer() {

        System.out.println("Timer event: " + new Date());
//        notificationSessionBean.send();

        Email email = new Email();
//        List<Swipe> swipeList = email.fetch();
//        for(Swipe swipe: swipeList){
//            cardUsageBean.addSwipe(swipe);
//        }
        

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
