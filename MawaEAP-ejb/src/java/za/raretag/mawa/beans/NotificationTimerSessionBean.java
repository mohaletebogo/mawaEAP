/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import javax.batch.runtime.BatchStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author tebogom
 */
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class NotificationTimerSessionBean {

  
    @EJB
    private NotificationSessionBeanLocal notificationSessionBean;

    @Resource
    TimerService timerService;

    private Date lastProgrammaticTimeout;
    private Date lastAutomaticTimeout;

    private Logger logger = Logger.getLogger(
            "za.raretag.mawa.beans.NotificationTimerSessionBean");

    public void setTimer(long intervalDuration) {
        logger.info("Setting a programmatic timeout for "
                + intervalDuration + " milliseconds from now.");
        Timer timer = timerService.createTimer(intervalDuration,
                "Created new programmatic timer");
    }

    @Timeout
    public void programmaticTimeout(Timer timer) {
        this.setLastProgrammaticTimeout(new Date());
        logger.info("Programmatic timeout occurred.");
    }

    @Schedule(minute="*/5")
    public void automaticTimeout() {
        this.setLastAutomaticTimeout(new Date());
        logger.info("Automatic timeout occured not sure");
        notificationSessionBean.send();
//        List<String> recipients = new ArrayList<>();
//        //recipients.add("yolanda.shimange@phamoha.co.za");
//        recipients.add("tebogo.mohale@phamoha.co.za");
//                //recipients.add("ebby.mohale@phamoha.co.za");
//        //emailSessionBean.sendEmail(recipients, "Test Timer", "Test Timer");
//        
//        
//        System.out.println("Starting Job at :" + new Date());
//        JobOperator jobOperator = BatchRuntime.getJobOperator();
//        Properties props = new Properties();
//        //props.setProperty("orderBean", orderBean);
//        jobOperator.start("EODJobs", props);
//        System.out.println("Job Started at :" + new Date());

    }

    public String getLastProgrammaticTimeout() {
        if (lastProgrammaticTimeout != null) {
            return lastProgrammaticTimeout.toString();
        } else {
            return "never";
        }

    }

    public void setLastProgrammaticTimeout(Date lastTimeout) {
        this.lastProgrammaticTimeout = lastTimeout;
    }

    public String getLastAutomaticTimeout() {
        if (lastAutomaticTimeout != null) {
            return lastAutomaticTimeout.toString();
        } else {
            return "never";
        }
    }

    public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
        this.lastAutomaticTimeout = lastAutomaticTimeout;
    }
}
