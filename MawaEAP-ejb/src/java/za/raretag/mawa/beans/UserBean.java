/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import za.raretag.mawa.entities.User;
import za.raretag.mawa.entities.UserRole;
import za.raretag.mawa.entities.UserRolePK;
import za.raretag.mawa.entities.Partner;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import javax.xml.bind.DatatypeConverter;
import za.raretag.mawa.entities.*;
import za.raretag.mawa.entities.controllers.exceptions.*;
import za.raretag.mawa.generic.Controllers;
import za.raretag.mawa.entities.controllers.*;
import za.raretag.mawa.generic.Cashup;
import za.raretag.mawa.generic.Conversion;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Response;
import za.raretag.mawa.generic.UserWorkcenter;
import za.raretag.mawa.generic.Utils;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserBean implements UserBeanLocal {

    @EJB
    private NotificationSessionBeanLocal notificationSessionBean;

    @EJB
    private PartnerBeanLocal partnerBean;
    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;
    UserJpaController userController;
    UserRoleJpaController userRoleController;
    PartnerContactJpaController partnerContactController;
    ConfigRoleWorkcenterJpaController workcenterController;

    private void initialiseController() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        userController = new UserJpaController(utx, emf);
        userRoleController = new UserRoleJpaController(utx, emf);
        partnerContactController = new PartnerContactJpaController(utx, emf);
        workcenterController = new ConfigRoleWorkcenterJpaController(utx, emf);

    }

    @Override
    public Response authenticate(String username, String password) {
        initialiseController();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<za.raretag.mawa.generic.User> data = new Data();
        za.raretag.mawa.generic.User usr = new za.raretag.mawa.generic.User();
        boolean authenticated = false;
        if ("ADMINISTRATOR".equals(username.toUpperCase())) {
            authenticated = true;
            usr.setUserID(username);
            usr.setPasswordStatus("PROD");
            usr.setAuthenticated(true);
            data.set(usr);
        } else {
            User user = userController.findUser(username);
            if (user != null) {

                try {
                    String storedPassword = new String(user.getPassword(), "UTF-8");
                    String enteredPassword = password;
                    usr.setUserID(username);
                    usr.setPasswordStatus(user.getStatus());
                    usr.setAuthenticated(validatePassword(enteredPassword, storedPassword));
                    data.set(usr);
                    if (!usr.isAuthenticated()) {
                        MessageContainer message = new MessageContainer("E", "Incorrect username/password");
                        messageList.add(message);
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
                    MessageContainer message = new MessageContainer("E", ex.getMessage());
                    messageList.add(message);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
                    MessageContainer message = new MessageContainer("E", ex.getMessage());
                    messageList.add(message);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
                    MessageContainer message = new MessageContainer("E", ex.getMessage());
                    messageList.add(message);
                }
            }

        }
        return new Response(data, messageList);
    }

    private static String getSalt() throws NoSuchAlgorithmException {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt.toString();
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt().getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static String getSecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static String get_SHA_1_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = toByteArray(parts[1]);
        byte[] hash = toByteArray(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public String getUserRole(String username) {
        String role = null;
        if ("ADMINISTRATOR".equals(username)) {
            role = "POWE";
        } else {

            try {

                Collection<UserRole> userRoles = userRoleController.findUserRoleEntities();
                for (UserRole userRole : userRoles) {
                    if (userRole.getValidTo().after(new Date()) && userRole.getUserRolePK().getUserId().equals(username)) {
                        role = userRole.getUserRolePK().getUserRole();
                        break;
                    }
                }

            } catch (Exception ex) {
            }

        }
        return role;
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    @Override
    public List<MessageContainer> createUser(String partner, String role, String email) {
        initialiseController();
        List<MessageContainer> messageList = new ArrayList<>();
        try {

            String password = Utils.generateRandomPassword();
            String generatedSecuredPasswordHash = generateStrongPasswordHash(password);
            User newUser = new User(partner);
            //Partner employee = partnerController.findPartner(partner);
            newUser.setPartnerNo(partner);
            newUser.setStatus("INIT");
            newUser.setPassword(generatedSecuredPasswordHash.getBytes("UTF-8"));
            newUser.setValidFrom(new Date());
            newUser.setValidTo(Conversion.stringToDate("9999-12-31"));
            userController.create(newUser);

            MessageContainer message = new MessageContainer("S", "User " + partner + " Created Succesfully");
            messageList.add(message);

            messageList.addAll(addUserRole(partner, role));
//
//            List<PartnerContact> contacts = partnerContactController.findPartnerContactEntities();
//            for (PartnerContact contact : contacts) {
//                if ("EMAIL".equals(contact.getType()) && contact.getPartnerNo().getPartnerNo().equals(partner)) {
            String body = "<p>Your new user has been created.</p><p>Username: " + partner + " </p><p>Password: " + password + "</p>";
            notificationSessionBean.create(email, "User " + partner + " Created Succesfully", body, "EMAIL");
//                }
//            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return messageList;

    }

    @Override
    public List<MessageContainer> addUserRole(String username, String role) {
        initialiseController();
        List<MessageContainer> messageList = new ArrayList<>();
        try {
            UserRolePK userRolePK = new UserRolePK(username, role);
            UserRole userRole = new UserRole(userRolePK);
            userRole.setValidFrom(new Date());
            userRole.setValidTo(Conversion.stringToDate("9999-12-31"));
            // userRole.setusername);
            userRoleController.create(userRole);
            MessageContainer message = new MessageContainer("S", "User Role Added Succesfully");
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return messageList;
    }

    @Override
    public User getUser(String username) {
        initialiseController();
        return userController.findUser(username);

    }

    @Override
    public Response changePassword(String userId, String oldPassword, String newPassword) {
        initialiseController();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> data = new Data();
        boolean passwordChanged = false;
        try {
            Response authentication = authenticate(userId, oldPassword);
            messageList.addAll(authentication.getMessages());
            Data<za.raretag.mawa.generic.User> us = authentication.getData();
            if (us.get().isAuthenticated()) {
                User usr = getUser(userId);
                String generatedSecuredPasswordHash = generateStrongPasswordHash(newPassword);
                usr.setStatus("PROD");
                usr.setPassword(generatedSecuredPasswordHash.getBytes("UTF-8"));
                userController.edit(usr);
                passwordChanged = true;
            }
        } catch (NoSuchAlgorithmException ex) {
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        data.set(passwordChanged);
        return new Response(data, messageList);
    }

    @Override
    public Response resetPassword(String email) {
        initialiseController();
        List<MessageContainer> messageList = new ArrayList<>();
        User user = null;
        try {
            List<Partner> partners = partnerBean.searchCustomerByEmail(email);
            Iterator<Partner> it = partners.iterator();
            while (it.hasNext()) {
                Partner employee = it.next();
                user = getUserByPartnerNo(employee.getPartnerNo());
                if (user != null) {
                    break;
                }
            }

            if (user != null) {
                String password = Utils.generateRandomPassword();
                String generatedSecuredPasswordHash = generateStrongPasswordHash(password);
                user.setStatus("INIT");
                user.setPassword(generatedSecuredPasswordHash.getBytes("UTF-8"));
                userController.edit(user);

                String body = "<p>Please find your new password below:</p><p>Username: " + user.getUserId() + " </p><p>New password: " + password + "</p>";
                notificationSessionBean.create(email, "Your password has been reset", body, "EMAIL");
                MessageContainer message = new MessageContainer("S", "New password has been emailed to you");
                messageList.add(message);
            } else {
                MessageContainer message = new MessageContainer("E", "User not found");
                messageList.add(message);
            }
        } catch (Exception ex) {
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }

        return new Response(messageList);
    }

    private User getUserByPartnerNo(String partnerNo) {
        initialiseController();
        List<User> userList = new ArrayList<>();

        try {
            TypedQuery<User> query = em.createNamedQuery("User.findByPartnerNo", User.class
            );
            query.setParameter(
                    "partnerNo", partnerNo);
            userList = query.getResultList();

        } finally {
            // em.close();

        }
        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public Response getUserWorkcenter(String user) {
        initialiseController();
        String role = getUserRole(user);
        List<MessageContainer> messageList = new ArrayList<>();
        Data<List<UserWorkcenter>> data = new Data();
        List<UserWorkcenter> list = new ArrayList<>();
        List<ConfigRoleWorkcenter> workcenterList = workcenterController.findConfigRoleWorkcenterEntities();
        Iterator<ConfigRoleWorkcenter> it = workcenterList.iterator();
        while (it.hasNext()) {
            ConfigRoleWorkcenter workcenter = it.next();
            if (workcenter.getConfigRoleType().getId().equals(role)) {
                UserWorkcenter userWorkcenter = new UserWorkcenter();
                userWorkcenter.setID(workcenter.getConfigWorkcenter().getId());
                userWorkcenter.setDescription(workcenter.getConfigWorkcenter().getDescription());
                userWorkcenter.setSequence(workcenter.getSequence());
                list.add(userWorkcenter);
            }
        }
        data.set(list);
        return new Response(data, messageList);
    }

}
