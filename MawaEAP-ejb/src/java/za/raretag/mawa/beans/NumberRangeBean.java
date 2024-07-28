/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.util.ArrayList;
import java.util.Date;
import za.raretag.mawa.entities.NumRange;
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
import za.raretag.mawa.entities.*;
import za.raretag.mawa.entities.controllers.exceptions.*;
import za.raretag.mawa.entities.controllers.*;
import za.raretag.mawa.generic.Controllers;
import za.raretag.mawa.generic.Conversion;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class NumberRangeBean implements NumberRangeBeanLocal {

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;
    NumRangeJpaController numRangeController;

    List<NumRange> numRanges;

    private void initController() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        numRangeController = new NumRangeJpaController(utx, emf);
    }

    @Override
    public String getNumber(String objectType) {
        initController();
        String currentNumber = null;
        numRanges = numRangeController.findNumRangeEntities();

        for (NumRange num : numRanges) {
            if (num.getObjectType().equals(objectType)) {
                Integer newNumber;
                currentNumber = num.getCurrentNumber();
                Integer strLength = num.getCurrentNumber().length();
                newNumber = Integer.parseInt(num.getCurrentNumber()) + 1;
                
                num.setCurrentNumber(newNumber.toString());
                try {
                    numRangeController.edit(num);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(NumberRangeBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(NumberRangeBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return currentNumber;
    }

    @Override
    public Response addRange(String object_type, String start, String end) {
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> data = new Data();
        initController();
        NumRange range = new NumRange();
        range.setObjectType(object_type);
        range.setCurrentNumber(start);
        range.setStartNumber(start);
        range.setEndNumber(end);
        range.setValidFrom(new Date());
        range.setValidTo(Conversion.stringToDate("9999-12-31"));
        try {
            numRangeController.create(range);
            data.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(NumberRangeBean.class.getName()).log(Level.SEVERE, null, ex);
            data.set(Boolean.FALSE);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }
}
