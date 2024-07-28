/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.CardUsage;
import za.raretag.mawa.entities.controllers.CardUsageJpaController;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Response;
import za.raretag.mawa.generic.Swipe;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CardUsageBean implements CardUsageBeanLocal {

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;
    CardUsageJpaController cardUsageController;

    private void initControllers() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        cardUsageController = new CardUsageJpaController(utx, emf);
    }

    @Override
    public Response addSwipe(Swipe swipe) {
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> data = new Data();
        try {
            initControllers();
            CardUsage cardUsage = new CardUsage();
            cardUsage.setCardNo(swipe.getCardNo());
            cardUsage.setSupplier(swipe.getSupplier());
            cardUsage.setAmount(swipe.getAmount());
            cardUsage.setSwipeDate(new Date());
            cardUsageController.create(cardUsage);
            data.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(CardUsageBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }
}
