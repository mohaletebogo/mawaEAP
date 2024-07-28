/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import za.raretag.mawa.generic.Cashup;
import za.raretag.mawa.entities.CheckOut;
import za.raretag.mawa.entities.DirectDeposit;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.controllers.CheckOutJpaController;
import za.raretag.mawa.entities.controllers.ConfigSalesAreaJpaController;
import za.raretag.mawa.entities.controllers.DirectDepositJpaController;
import za.raretag.mawa.entities.controllers.PartnerJpaController;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;
import za.raretag.mawa.generic.CheckingQuery;
import za.raretag.mawa.generic.Conversion;

import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.Deposit;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CashupBean implements CashupBeanLocal {

    @EJB
    private NumberRangeBeanLocal numberRangeBean;

    @EJB
    private NotificationSessionBeanLocal notificationSessionBean;

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;

    CheckOutJpaController checkOutController;
    DirectDepositJpaController directDepositController;
    PartnerJpaController partnerController;
    ConfigSalesAreaJpaController salesAreaController;

    private void initControllers() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        checkOutController = new CheckOutJpaController(utx, emf);
        directDepositController = new DirectDepositJpaController(utx, emf);
        partnerController = new PartnerJpaController(utx, emf);
        salesAreaController = new ConfigSalesAreaJpaController(utx, emf);
    }

    @Override
    public Response create(Cashup cashup) {
        String body;
        initControllers();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Cashup> data = new Data();
        boolean hasDeposits = false;

        CheckOut checkOut = new CheckOut();
        checkOut.setCheckingId(cashup.getSalesArea() + numberRangeBean.getNumber("CHECKOUT"));
        checkOut.setCreatedAt(new Date());
        checkOut.setCreatedBy(partnerController.findPartner(cashup.getCreatedBy()));
        checkOut.setReceivedBy(partnerController.findPartner(cashup.getEmployeeResponsible()));
        checkOut.setTerminalId(cashup.getTerminalID());
        checkOut.setCashAmount(Conversion.stringToBigDecimal(cashup.getCashAmount()));
        checkOut.setDepositAmount(Conversion.stringToBigDecimal(cashup.getDepositAmount()));
        checkOut.setSalesArea(salesAreaController.findConfigSalesArea(cashup.getSalesArea()));
        checkOut.setReceiptFrom(cashup.getReceiptFrom());
        checkOut.setReceiptTo(cashup.getReceiptTo());
        try {
            checkOutController.create(checkOut);
            body = "<table><tr><td>Reference Number</td><td>" + checkOut.getCheckingId() + "</td>";
            body = body + "<tr><td>Sales Area</td><td>" + checkOut.getSalesArea().getDescription() + "</td>";
            body = body + "<tr><td>Receipt From</td><td>" + checkOut.getReceiptFrom() + "</td>";
            body = body + "<tr><td>Receipt To</td><td>" + checkOut.getReceiptTo() + "</td>";
            body = body + "<tr><td>Cash Amount Received</td><td>" + checkOut.getCashAmount() + "</td>";
//            body = body + "<tr><td>Direct Deposit Amount</td><td>" + checkOut.getDepositAmount() + "</td>";
            body = body + "</table>";
            Iterator<Deposit> it = cashup.getDeposits().iterator();
            if (it.hasNext()) {
                body = body + "<table><tr><th>Deposit Date</th><th>Deposit Reference</th><th>Deposit Amount</th></tr>";
            }
            for (Deposit deposit : cashup.getDeposits()) {
                hasDeposits = true;
                DirectDeposit dirDepo = new DirectDeposit();
                dirDepo.setCheckingId(checkOut.getCheckingId());
                dirDepo.setDepositDate(deposit.getDepositDate());
                dirDepo.setDepositReference(deposit.getReference());
                dirDepo.setAmount(deposit.getAmount());
                directDepositController.create(dirDepo);
                body = body + "<tr><td>" + deposit.getDepositDate() + "</td><td>" + deposit.getReference() + "</td><td>" + deposit.getAmount();
            }

            if (hasDeposits) {
                body = body + "</table>";
            }
            List<String> recipients = notificationSessionBean.getRecipients("CHECKOUT");
            for (String recipient : recipients) {
                notificationSessionBean.create(recipient, "Receipt Usage Log - Reference Number:" + checkOut.getCheckingId(), body, "EMAIL");
            }
            data.set(new Cashup(checkOut));
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(CashupBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(CashupBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }

    @Override
    public Response get(String checkingId) {
        initControllers();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Cashup> data = new Data();
        Cashup cashup = new Cashup(checkOutController.findCheckOut(checkingId));
        data.set(cashup);
        return new Response(data, messageList);
    }

    @Override
    public Response search(CheckingQuery checkingQuery) {
        initControllers();
        List<Cashup> cashupList = new ArrayList<>();
        List<CheckOut> checkoutList = new ArrayList<>();
          List<MessageContainer> messageList = new ArrayList<>();
        Data<List<Cashup>> data = new Data();
        

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<CheckOut> q = cb.createQuery(CheckOut.class);
            Root<CheckOut> c = q.from(CheckOut.class);
            ParameterExpression<String> p = cb.parameter(String.class);
            
            Path<Date> dateEntryPath = c.get("createdAt");
            Predicate predicate = cb.between(dateEntryPath, Conversion.stringToDate(checkingQuery.getStartDate()), Conversion.addDaysToDate(Conversion.stringToDate(checkingQuery.getEndDate()),1));
            q.select(c).where(predicate);

            TypedQuery<CheckOut> query = em.createQuery(q);
//            query.setParameter(p, checkingQuery.getStartDate());
//            query.setParameter(p, checkingQuery.getStartDate());
            checkoutList = query.getResultList();
            for(CheckOut check : checkoutList){
                Cashup cashup = new Cashup(check);
                cashupList.add(cashup);                
            }
                    

        } finally {
            //em.close();

        }
        data.set(cashupList);
        return new Response(data, messageList);
    }


}
