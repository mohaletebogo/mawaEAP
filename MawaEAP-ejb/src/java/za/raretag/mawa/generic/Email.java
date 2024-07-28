/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
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
import javax.mail.search.FlagTerm;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringUtils;
import za.raretag.mawa.beans.CardUsageBeanLocal;
import za.raretag.mawa.beans.TimerSessionBean;

/**
 *
 * @author tebogom
 */
public class Email {

    private final int port = 25;
    private final String host = "mail.raretag.co.za";
    private final String from = "mawa.system@raretag.co.za";
    private final boolean auth = true;
    private final String username = "mawa.system@raretag.co.za";
    private final String password = "P@s5w0rd";
    private final Protocol protocol = Protocol.SMTPS;
    private final boolean debug = true;

    public Email() {
    }

    public boolean create() {
        return false;
    }

    public List<Swipe> fetch() {
        List<Swipe> swipeList = new ArrayList<>();
        Message[] messages = {};
        try {
            // create properties field
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getInstance(properties);
         // emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, username, password);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));

            // retrieve the messages from the folder in an array and print it
//            Message[] messages = emailFolder.();
            messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            int i = 0;
            for (Message message : messages) {

                //Message message = message1;
                //message.setFlag(Flag.SEEN, true);
                //message.saveChanges();        
                String subject = message.getSubject();
                Address[] senders = message.getFrom();
                BigDecimal amount = null;
                String sender = senders[0].toString();
                if ("inContact@fnb.co.za".equals(sender) || "Tebogo Mohale <tebogo.mohale@raretag.co.za>".equals(sender)) {
                    String amountString = StringUtils.substringBetween(subject, "FNB :-) R", " reserved for purchase");
                    if (amountString != null) {
                        amount = new BigDecimal(amountString);
                    }
                    String garage = StringUtils.substringBetween(subject, "@ ", " from cheq");
                    String accountNumber = StringUtils.substringBetween(subject, "a/c..", " using card");
                    String card = StringUtils.substringBetween(subject, "using card..", ". ");
                    if ("471117".equals(accountNumber)) {
                        Swipe swipe = new Swipe();
                        swipe.setAmount(amount);
                        swipe.setCardNo(card);
                        swipe.setSupplier(garage);
                        swipeList.add(swipe);
                    }

                }

                messages[i].setFlag(Flags.Flag.DELETED, true);
                i++;
            }
            emailFolder.close(true);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return swipeList;
    }

    public boolean send(String recipient, String subject, String body) {
        boolean sent = false;
        try {

            ArrayList<String> recipients = new ArrayList<>();
            recipients.add(recipient);

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.timeout", 1000);
//            props.put("mail.smtp.starttls.enable", true);
//            switch (protocol) {
//                case SMTPS:
//                    props.put("mail.smtp.ssl.enable", true);
//                    break;
//                case TLS:
//                    props.put("mail.smtp.starttls.enable", true);
//                    break;
//            }

//            Authenticator authenticator = null;
//            if (auth) {
//                
//                authenticator = new Authenticator() {
//                    private PasswordAuthentication pa = new PasswordAuthentication(username, password);
//
//                    @Override
//                    public PasswordAuthentication getPasswordAuthentication() {
//                        return pa;
//                    }
//                };
//            }
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);
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
            Transport.send(message, address, username, password);
            sent = true;
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sent;
    }

    public boolean send(ArrayList<Note> emails) {
        boolean sent = false;
        try {

            ArrayList<String> recipients = new ArrayList<>();

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.timeout", 1000);
//            props.put("mail.smtp.starttls.enable", true);
//            switch (protocol) {
//                case SMTPS:
//                    props.put("mail.smtp.ssl.enable", true);
//                    break;
//                case TLS:
//                    props.put("mail.smtp.starttls.enable", true);
//                    break;
//            }

//            Authenticator authenticator = null;
//            if (auth) {
//                
//                authenticator = new Authenticator() {
//                    private PasswordAuthentication pa = new PasswordAuthentication(username, password);
//
//                    @Override
//                    public PasswordAuthentication getPasswordAuthentication() {
//                        return pa;
//                    }
//                };
//            }
            Session session = Session.getInstance(props);
            Store store = session.getStore("pop3s");
            store.connect(host, username, password);

            for (Note note : emails) {
                MimeMessage message = new MimeMessage(session);

                recipients.add(note.getRecipient());
                InternetAddress[] address = new InternetAddress[recipients.size()];
                int count = 0;

                message.setFrom(new InternetAddress(from));
                for (String to : recipients) {
                    address[count] = new InternetAddress(to);
                    count++;
                }

                message.setRecipients(Message.RecipientType.TO, address);
                message.setSubject(note.getSubject());
                message.setSentDate(new Date());

                Multipart multipart = new MimeMultipart("alternative");
                MimeBodyPart textPart = new MimeBodyPart();
                String textContent = note.getBody();

                textPart.setText(textContent);

                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = note.getBody();
                htmlPart.setContent(htmlContent, "text/html");

                multipart.addBodyPart(textPart);
                multipart.addBodyPart(htmlPart);

                message.setContent(multipart);
                Transport.send(message, address, username, password);
                sent = true;
            }
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sent;
    }
}
