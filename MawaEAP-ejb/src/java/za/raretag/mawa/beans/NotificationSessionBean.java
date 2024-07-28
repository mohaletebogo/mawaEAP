/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigSetting;
import za.raretag.mawa.entities.MailList;
import za.raretag.mawa.entities.Notification;
import za.raretag.mawa.entities.NotificationLog;
import za.raretag.mawa.entities.controllers.ConfigSettingJpaController;
import za.raretag.mawa.entities.controllers.MailListJpaController;
import za.raretag.mawa.entities.controllers.MessageJpaController;
import za.raretag.mawa.entities.controllers.NotificationJpaController;
import za.raretag.mawa.entities.controllers.NotificationLogJpaController;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;
import za.raretag.mawa.generic.Email;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Protocol;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class NotificationSessionBean implements NotificationSessionBeanLocal {

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;

    NotificationJpaController notificationController;
    NotificationLogJpaController notificationLogController;
    MailListJpaController mailListController;
    ConfigSettingJpaController settingController;

    private final int port = 25;
    private final String host = "mail.raretag.co.za";
    private final String from = "mawa.system@raretag.co.za";
    private final boolean auth = true;
    // private final String username = "mawa.system@raretag.co.za";
    private final String username = "tebogo.mohale@raretag.co.za";
    private final String password = "Rrtg@6365";
    private final Protocol protocol = Protocol.SMTPS;
    private final boolean debug = true;
    // private Session session;

    public void initControllers() {

        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        notificationController = new NotificationJpaController(utx, emf);
        notificationLogController = new NotificationLogJpaController(utx, emf);
        mailListController = new MailListJpaController(utx, emf);
        settingController = new ConfigSettingJpaController(utx, emf);

    }

    @Override
    public void send() {

        initControllers();

        try {
            List<ConfigSetting> settingList = settingController.findConfigSettingEntities();
            Iterator<ConfigSetting> it = settingList.iterator();
            Properties props = new Properties();
            while (it.hasNext()) {

                ConfigSetting setting = it.next();
                if ("EMAIL".equals(setting.getConfigSettingPK().getSetting())) {
                    props.put(setting.getConfigSettingPK().getAttribute(), setting.getValue());
                }
            }

//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.host", "smtp.gmail.com");
//            props.put("mail.smtp.port", "587");
//            props.put("mail.smtp.password", password);
//            props.put("mail.stmp.user", username);
//            props.put("mail.smtp.timeout", 1000);

//            session = Session.getInstance(props);
//            store = session.getStore("pop3s");
//            store.connect(host, username, password);
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
//           Store store = session.getStore("pop3s");
//            store.connect("smtp.gmail.com", username, password);
//            

            List<Notification> notifications = notificationController.findNotificationEntities();
            for (Notification notif : notifications) {
                if (notif.getStatus().equals("SCHEDULED")) {
                    if ("EMAIL".equals(notif.getType())) {
                        NotificationLog log = new NotificationLog();
                        log.setNotificationId(notif.getId());
                        log.setAction("SEND");
                        log.setExecutionTime(new Date());
                        try {
                            notificationLogController.create(log);
                            MimeMessage mess = buildMimeMessage(notif.getRecipient(), notif.getSubject(), notif.getBody(), session);
                            Transport.send(mess);

                            notif.setStatus("EXECUTED");
                            notificationController.edit(notif);
                            log.setResult("SUCCESS");
                            notificationLogController.edit(log);
                        } catch (RollbackFailureException ex) {
                            Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                            log.setResult("FAILURE");
                            try {
                                notificationLogController.edit(log);
                            } catch (RollbackFailureException ex1) {
                                Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex1);
                            } catch (Exception ex1) {
                                Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                            log.setResult("FAILURE");
                            try {
                                notificationLogController.edit(log);
                            } catch (RollbackFailureException ex1) {
                                Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex1);
                            } catch (Exception ex1) {
                                Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    }
                }
            }
//            store.close();
        } catch (Exception ex) {
            Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<MessageContainer> create(String recipient, String subject, String body, String type) {
        initControllers();

        List<MessageContainer> messageList = new ArrayList<>();
        try {
            Notification msg = new Notification();
            msg.setRecipient(recipient);
            msg.setBody(body);
            msg.setType(type);
            msg.setSubject(subject);
            msg.setStatus("SCHEDULED");
            notificationController.create(msg);
        } catch (Exception ex) {
            Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return messageList;
    }

    @Override
    public List<String> getRecipients(String mailingList) {
        initControllers();
        List<String> emails = new ArrayList<>();
        List<MailList> lst = mailListController.findMailListEntities();
        for (MailList mailList : lst) {
            if (mailList.getListId().equals(mailingList)) {
                emails.add(mailList.getEmailAddress());
            }
        }
        return emails;
    }

    private MimeMessage buildMimeMessage(String recipient, String subject, String body, Session session) {
        MimeMessage message = new MimeMessage(session);
        try {
            ArrayList<String> recipients = new ArrayList<>();
            recipients.add(recipient);

            InternetAddress[] address = new InternetAddress[recipients.size()];
            int count = 0;

            message.setFrom(new InternetAddress(from));
            for (String to : recipients) {
                address[count] = new InternetAddress(to);
                count++;
            }

            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());

            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart textPart = new MimeBodyPart();
            String textContent = body;

            textPart.setText(textContent);

            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent = body;
            htmlPart.setContent(htmlContent, "text/html");

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(NotificationSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

}
