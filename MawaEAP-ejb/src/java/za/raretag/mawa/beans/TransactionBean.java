/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import za.raretag.mawa.entities.CheckOut;
import za.raretag.mawa.entities.ConfigBankAccountUsage;
import za.raretag.mawa.entities.ConfigPaymentType;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.ProductAttribute;
import za.raretag.mawa.entities.ProductPricing;
import za.raretag.mawa.entities.ProductPricingPK;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionBank;
import za.raretag.mawa.entities.TransactionDate;
import za.raretag.mawa.entities.TransactionDatePK;
import za.raretag.mawa.entities.TransactionItem;
import za.raretag.mawa.entities.TransactionLocation;
import za.raretag.mawa.entities.TransactionLocationPK;
//import za.raretag.mawa.entities.TransactionPK;
import za.raretag.mawa.entities.TransactionPartner;
import za.raretag.mawa.entities.TransactionPartnerPK;
import za.raretag.mawa.entities.TransactionPayment;
import za.raretag.mawa.entities.TransactionRelation;
import za.raretag.mawa.entities.TransactionRelationPK;
import za.raretag.mawa.entities.TransactionStatus;
import za.raretag.mawa.entities.TransactionStatusPK;
import za.raretag.mawa.entities.controllers.ConfigBankAccountTypeJpaController;
import za.raretag.mawa.entities.controllers.ConfigBankAccountUsageJpaController;
import za.raretag.mawa.entities.controllers.ConfigBankNameJpaController;
import za.raretag.mawa.entities.controllers.ConfigDateTypeJpaController;
import za.raretag.mawa.entities.controllers.ConfigLocationTypeJpaController;
import za.raretag.mawa.entities.controllers.ConfigPartnerFunctionJpaController;
import za.raretag.mawa.entities.controllers.ConfigPaymentTypeJpaController;
import za.raretag.mawa.entities.controllers.ConfigProductCategoryJpaController;
import za.raretag.mawa.entities.controllers.ConfigSalesAreaJpaController;
import za.raretag.mawa.entities.controllers.ConfigStatusTypeJpaController;
import za.raretag.mawa.entities.controllers.ConfigTransactionTypeJpaController;
import za.raretag.mawa.entities.controllers.PartnerJpaController;
import za.raretag.mawa.entities.controllers.ProductAttributeJpaController;
import za.raretag.mawa.entities.controllers.ProductJpaController;
import za.raretag.mawa.entities.controllers.ProductPricingJpaController;
import za.raretag.mawa.entities.controllers.TransactionBankJpaController;
import za.raretag.mawa.entities.controllers.TransactionDateJpaController;
import za.raretag.mawa.entities.controllers.TransactionItemJpaController;
import za.raretag.mawa.entities.controllers.TransactionJpaController;
import za.raretag.mawa.entities.controllers.TransactionLocationJpaController;
import za.raretag.mawa.entities.controllers.TransactionPartnerJpaController;
import za.raretag.mawa.entities.controllers.TransactionPaymentJpaController;
import za.raretag.mawa.entities.controllers.TransactionRelationJpaController;
import za.raretag.mawa.entities.controllers.TransactionStatusJpaController;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;
import za.raretag.mawa.generic.BankAccount;
import za.raretag.mawa.generic.Cashup;
import za.raretag.mawa.generic.Claim;
import za.raretag.mawa.generic.Contact;
import za.raretag.mawa.generic.Conversion;
import za.raretag.mawa.generic.Customer;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.Dependent;
import za.raretag.mawa.generic.Identity;
import za.raretag.mawa.generic.Item;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Order;
import za.raretag.mawa.generic.OrderArea;
import za.raretag.mawa.generic.OrderDate;
import za.raretag.mawa.generic.OrderItem;
import za.raretag.mawa.generic.OrderPartner;
import za.raretag.mawa.generic.OrderPayment;
import za.raretag.mawa.generic.OrderQuery;
import za.raretag.mawa.generic.PartnerQuery;
import za.raretag.mawa.generic.Payment;
import za.raretag.mawa.generic.Person;
import za.raretag.mawa.generic.Policy;
import za.raretag.mawa.generic.ReferenceDocument;
import za.raretag.mawa.generic.Request;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TransactionBean implements TransactionBeanLocal {

    @EJB
    private PartnerBeanLocal partnerBean;
    @EJB
    private NumberRangeBeanLocal numberRangeBean;

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;
    TransactionJpaController transactionController;
    TransactionPartnerJpaController transactionPartnerController;
    TransactionItemJpaController transactionItemController;
    TransactionDateJpaController transactionDateController;
    TransactionPaymentJpaController transactionPaymentController;
    TransactionBankJpaController transactionBankController;

    ProductPricingJpaController productPricingController;
    TransactionLocationJpaController transactionLocationController;
    ProductJpaController productController;
    ProductAttributeJpaController productAttributeController;
    ConfigProductCategoryJpaController productCategoryController;
    ConfigTransactionTypeJpaController transactionTypeController;
    ConfigPartnerFunctionJpaController partnerFunctionController;
    ConfigLocationTypeJpaController locationTypeController;
    ConfigPaymentTypeJpaController paymentTypeController;
    ConfigSalesAreaJpaController salesAreaController;
    PartnerJpaController partnerController;
    ConfigDateTypeJpaController dateTypeController;
    ConfigStatusTypeJpaController statusTypeController;
    TransactionStatusJpaController statusController;
    ConfigBankNameJpaController bankNameController;
    ConfigBankAccountUsageJpaController accountUsageController;
    ConfigBankAccountTypeJpaController bankAccountTypeController;
    TransactionRelationJpaController transactionRelationController;

    private void init() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        transactionController = new TransactionJpaController(utx, emf);
        transactionPartnerController = new TransactionPartnerJpaController(utx, emf);
        transactionItemController = new TransactionItemJpaController(utx, emf);
        productPricingController = new ProductPricingJpaController(utx, emf);
        statusController = new TransactionStatusJpaController(utx, emf);
        transactionLocationController = new TransactionLocationJpaController(utx, emf);
        productController = new ProductJpaController(utx, emf);
        transactionTypeController = new ConfigTransactionTypeJpaController(utx, emf);
        partnerFunctionController = new ConfigPartnerFunctionJpaController(utx, emf);
        partnerController = new PartnerJpaController(utx, emf);
        locationTypeController = new ConfigLocationTypeJpaController(utx, emf);
        salesAreaController = new ConfigSalesAreaJpaController(utx, emf);
        productCategoryController = new ConfigProductCategoryJpaController(utx, emf);
        dateTypeController = new ConfigDateTypeJpaController(utx, emf);
        paymentTypeController = new ConfigPaymentTypeJpaController(utx, emf);
        transactionDateController = new TransactionDateJpaController(utx, emf);
        transactionPaymentController = new TransactionPaymentJpaController(utx, emf);
        transactionBankController = new TransactionBankJpaController(utx, emf);
        productAttributeController = new ProductAttributeJpaController(utx, emf);
        statusTypeController = new ConfigStatusTypeJpaController(utx, emf);
        bankNameController = new ConfigBankNameJpaController(utx, emf);
        bankAccountTypeController = new ConfigBankAccountTypeJpaController(utx, emf);
        transactionRelationController = new TransactionRelationJpaController(utx, emf);
        accountUsageController = new ConfigBankAccountUsageJpaController(utx, emf);
    }

    @Override
    public Response createPolicy(Policy policy) {
        String trxType = "FUNERALPOLICY";
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<String> data = new Data();

        String trxNo = numberRangeBean.getNumber(trxType);
        try {
            Transaction transaction = new Transaction(trxNo);
            transaction.setTransactionType(transactionTypeController.findConfigTransactionType(trxType));
            transaction.setStatus(statusTypeController.findConfigStatusType("ACTIVE"));
            transaction.setStatusDate(new Date());
            transaction.setValidFrom(new Date());
            transaction.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionController.create(transaction);

//            TransactionPartnerPK transactionPartnerPK = new TransactionPartnerPK("MAINMEMBER", trxNo, policy.getCustomer());
//            messageList.addAll(addPartner(transactionPartnerPK).getMessages());
//
//            transactionPartnerPK = new TransactionPartnerPK("SALESREPRESENTATIVE", trxNo, policy.getSalesRepresentative());
//            messageList.addAll(addPartner(transactionPartnerPK).getMessages());
//
//            messageList.addAll(addProduct(transaction, policy.getProductCode(), 1).getMessages());
//
//            TransactionLocationPK transactionLocationPK = new TransactionLocationPK("SALESAREA", trxNo, policy.getSalesArea());
//            messageList.addAll(addLocation(transactionLocationPK).getMessages());
//
//            messageList.addAll(addDate(trxNo, "DATEJOINED", new Date()).getMessages());
//            Date dateEffective = Conversion.addMonthsToDate(new Date(), Integer.parseInt(getProductAttributeValue(policy.getProductCode(), "WAITINGPERIOD")));
//            messageList.addAll(addDate(trxNo, "DATEEFFECTIVE", dateEffective).getMessages());
            data.set(trxNo);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);

            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);

            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }

    @Override
    public Response addPartner(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> responseData = new Data();
        Data<OrderPartner> orderPartnerData = request.getData();
        OrderPartner orderPartner = orderPartnerData.get();

        if ("".equals(orderPartner.getPartnerNo())) {
            orderPartner.setPartnerNo(getPartnerNo(orderPartner));
        }
        try {
            TransactionPartnerPK transactionPartnerPK = new TransactionPartnerPK(orderPartner.getType(), orderPartner.getOrderNo(), orderPartner.getPartnerNo());
            TransactionPartner transactionPartner = new TransactionPartner();
            transactionPartner.setTransactionPartnerPK(transactionPartnerPK);
            transactionPartner.setConfigPartnerFunction(partnerFunctionController.findConfigPartnerFunction(orderPartner.getType()));
            transactionPartner.setPartner(partnerController.findPartner(transactionPartnerPK.getPartnerNo()));
            transactionPartner.setTransaction(transactionController.findTransaction(orderPartner.getOrderNo()));
            transactionPartner.setValidFrom(new Date());
            transactionPartner.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionPartnerController.create(transactionPartner);
            responseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(responseData, messageList);
    }

    @Override
    public Response addProduct(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> reponseData = new Data();
        Data<OrderItem> itemData = request.getData();
        OrderItem orderItem = itemData.get();
        try {
            Transaction transaction = transactionController.findTransaction(orderItem.getOrderNo());
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setProductId(productController.findProduct(orderItem.getCode()));
            transactionItem.setQuantity(Conversion.stringToInteger(orderItem.getQuantity()));
            transactionItem.setTransactionNo(transaction);
            transactionItem.setValidFrom(new Date());
            transactionItem.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionItem.setItemCategory(productCategoryController.findConfigProductCategory(transaction.getTransactionType().getId()));
            //transactionItemController.create(transactionItem);

//            TransactionItem transactionItem = transactionItemController.
            ProductPricingPK prodPricePK;
            switch (transactionItem.getItemCategory().getId()) {
                case "FUNERALPOLICY":
                    prodPricePK = new ProductPricingPK(orderItem.getCode(), "UNITPRICE");
                    ProductPricing prodPrice = productPricingController.findProductPricing(prodPricePK);
                    if (prodPrice != null) {
                        transactionItem.setUnitPrice(prodPrice.getValue());
                    }
                    break;
                case "CLAIM":
                    prodPricePK = new ProductPricingPK(orderItem.getCode(), "CASHPAYOUT");
                    prodPrice = productPricingController.findProductPricing(prodPricePK);
                    break;
            }

            transactionItemController.create(transactionItem);
            reponseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(reponseData, messageList);
    }

    @Override
    public Response addLocation(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> responseData = new Data();
        Data<OrderArea> orderAreaData = request.getData();
        OrderArea orderArea = orderAreaData.get();
        try {
            TransactionLocationPK transactionLocationPK = new TransactionLocationPK(orderArea.getType(),
                    orderArea.getOrderNo(), orderArea.getAreaID());
            TransactionLocation transactionLocation = new TransactionLocation();
            transactionLocation.setTransactionLocationPK(transactionLocationPK);
            transactionLocation.setTransaction(transactionController.findTransaction(orderArea.getOrderNo()));
            transactionLocation.setConfigLocationType(locationTypeController.findConfigLocationType(orderArea.getType()));
            transactionLocation.setConfigSalesArea(salesAreaController.findConfigSalesArea(orderArea.getAreaID()));
            transactionLocation.setValidFrom(new Date());
            transactionLocation.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionLocationController.create(transactionLocation);
            responseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(responseData, messageList);
    }

    @Override
    public Response getTransaction(String transactionNo) {
        init();
        Response response = null;

        List<MessageContainer> messageList = new ArrayList<>();
        Transaction transaction = transactionController.findTransaction(transactionNo);

        if (transaction != null) {
            switch (transaction.getTransactionType().getId()) {
                case "FUNERALPOLICY":
                    Data<Policy> data = new Data();
                    data.set(new Policy(transaction));
                    response = new Response(data, messageList);
                    break;
                default:
                    Data<Order> orderData = new Data();
                    orderData.set(new Order(transaction));
                    response = new Response(orderData, messageList);
                    break;

            }
        }
        return response;
    }

    @Override
    public Response addDate(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> responseData = new Data();
        Data<OrderDate> orderDateData = request.getData();
        OrderDate orderDate = orderDateData.get();
        try {
            TransactionDatePK transactionDatePK = new TransactionDatePK(orderDate.getOrderNo(), orderDate.getType());
            TransactionDate transactionDate = new TransactionDate(transactionDatePK);

            transactionDate.setDateValue(orderDate.getValue());
            transactionDate.setConfigDateType(dateTypeController.findConfigDateType(orderDate.getType()));
            transactionDate.setTransaction(transactionController.findTransaction(orderDate.getOrderNo()));
            transactionDate.setValidFrom(new Date());
            transactionDate.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionDateController.create(transactionDate);
            responseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(responseData, messageList);

    }

    @Override
    public Response addPayment(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> responseData = new Data();
        Data<OrderPayment> paymentData = request.getData();
        OrderPayment orderPayment = paymentData.get();
        try {
            Transaction transaction = transactionController.findTransaction(orderPayment.getOrderNo());
            TransactionPayment transactionPayment = new TransactionPayment();
//            String payType = determinePaymentType(orderPayment);
            ConfigPaymentType paymentType = paymentTypeController.findConfigPaymentType(orderPayment.getPaymentPeriod());
            transactionPayment.setPaymentType(paymentType);

            transactionPayment.setTransactionNo(transaction);
            transactionPayment.setExternalReceiptNo(orderPayment.getReceiptNo());
            transactionPayment.setPaymentDate(Conversion.stringToDate(orderPayment.getPaymentDate()));
            transactionPayment.setAmount(Conversion.stringToBigDecimal(orderPayment.getAmount()));
            transactionPayment.setCreatedAt(new Date());
            transactionPayment.setCreatedBy(partnerController.findPartner(request.getRequester()));
            transactionPayment.setReceivedBy(partnerController.findPartner(orderPayment.getReceivedBy()));
            transactionPayment.setTerminalId(orderPayment.getTerminalId());

            transactionPaymentController.create(transactionPayment);
            responseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(responseData, messageList);
    }

    private String determinePaymentType(OrderPayment orderPayment) {
        String paymentType;
        Transaction transaction = transactionController.findTransaction(orderPayment.getOrderNo());
        String stringMonth = "";
        if (transaction.getTransactionType().getId().equals("FUNERALPOLICY")) {
            Iterator<TransactionPayment> iteratorPaymentList = transaction.getTransactionPaymentCollection().iterator();
            if (!iteratorPaymentList.hasNext()) {
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH) + 1;
                stringMonth = Integer.toString(month);
                if (stringMonth.length() < 2) {
                    stringMonth = "0" + stringMonth;
                }
                paymentType = Integer.toString(year) + stringMonth;
            } else {
                int lastMonth = 0;
                while (iteratorPaymentList.hasNext()) {
                    TransactionPayment paymentTrx = iteratorPaymentList.next();
                    int intPayType = Conversion.stringToInteger(paymentTrx.getPaymentType().getId());
                    if (intPayType > lastMonth) {
                        lastMonth = intPayType;
                    }
                }
                String type = Integer.toString(lastMonth);
                int year;
                int month = Conversion.stringToInteger(type.substring(4, 6)) + 1;
                if (month > 12) {
                    year = Conversion.stringToInteger(type.substring(0, 4)) + 1;
                    month = Conversion.stringToInteger("01");
                } else {
                    year = Conversion.stringToInteger(type.substring(0, 4));
                }
                if (Integer.toString(month).length() < 2) {
                    stringMonth = "0" + Integer.toString(month);
                } else {
                    stringMonth = Integer.toString(month);
                }
                paymentType = Integer.toString(year) + stringMonth;
            }
        } else {
            paymentType = "INVPAYMENT";
        }
        return paymentType;
    }

    private String getProductAttributeValue(String productCode, String attribute) {
        List<ProductAttribute> productAttributeList = productAttributeController.findProductAttributeEntities();
        for (ProductAttribute prodAttr : productAttributeList) {
            if (prodAttr.getAttributeType().getId().equals(attribute) && prodAttr.getProductCode().getProductCode().equals(productCode)) {
                return prodAttr.getAttributeValue();
            }
        }
        return null;
    }

    @Override
    public Response getTransactionPartner(String transactionNo, String partnerFunction) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Partner> data = new Data();
        Transaction trx = transactionController.findTransaction(transactionNo);
        for (TransactionPartner trxPartner : trx.getTransactionPartnerCollection()) {
            if (trxPartner.getConfigPartnerFunction().getId().equals(partnerFunction)) {
                data.set(trxPartner.getPartner());
                return new Response(data, messageList);
            }
        }
        return null;
    }

    private String getLocation(String transactionNo, String locationType) {
        Transaction trx = transactionController.findTransaction(transactionNo);
        for (TransactionLocation trxLocation : trx.getTransactionLocationCollection()) {
            if (trxLocation.getConfigLocationType().getId().equals(locationType)) {
                return trxLocation.getConfigSalesArea().getDescription();
            }
        }
        return null;
    }

    @Override
    public Response setStatus(String transactionNo, String status) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> data = new Data();
        try {
            Transaction trx = transactionController.findTransaction(transactionNo);
            TransactionStatus trxStatus = new TransactionStatus();
            trxStatus.setTransactionStatusPK(new TransactionStatusPK(transactionNo, status));
            trxStatus.setTransaction(trx);
            trxStatus.setConfigStatusType(statusTypeController.findConfigStatusType(status));
            trxStatus.setStatusDate(new Date());
            statusController.create(trxStatus);
            data.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }

    @Override
    public Response addDependent(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> responseData = new Data();
        Data<Dependent> dependentData = request.getData();
        Dependent dependent = dependentData.get();
        String dependentNo;

        try {
            Response resp;

            PartnerQuery partnerQuery = new PartnerQuery();
            if (!"".equals(dependent.getIdNumber())) {
                partnerQuery.setIdNumber(dependent.getIdNumber());
            }
            resp = partnerBean.search(partnerQuery);
            Data<List<Customer>> searchResult = resp.getData();
            List<Customer> custList = searchResult.get();
            if (custList.size() > 0) {
                dependentNo = custList.iterator().next().getId();
            } else {
                Response response = partnerBean.create(dependent);
                Data<String> createResult = response.getData();
                dependentNo = createResult.get();

            }
            TransactionPartner transactionPartner = new TransactionPartner();
            TransactionPartnerPK transactionPartnerPK = new TransactionPartnerPK();
            transactionPartnerPK.setPartnerNo(dependentNo);
            transactionPartnerPK.setPartnerType("DEPENDENT");
            transactionPartnerPK.setTransactionNo(dependent.getPolicyNo());
            transactionPartner.setTransactionPartnerPK(transactionPartnerPK);
            transactionPartner.setConfigPartnerFunction(partnerFunctionController.findConfigPartnerFunction(transactionPartnerPK.getPartnerType()));
            transactionPartner.setPartner(partnerController.findPartner(dependentNo));
            Transaction transaction = transactionController.findTransaction(dependent.getPolicyNo());
            transactionPartner.setTransaction(transaction);
            transactionPartner.setDateAdded(new Date());
            transactionPartner.setStatus("ACTIVE");
            transactionPartner.setStatusDate(new Date());
            if (Conversion.dateToString(transaction.getValidFrom()).equals(Conversion.dateToString(new Date()))) {
                transactionPartner.setDateEffective(new Date());
            } else {
                transactionPartner.setDateEffective(Conversion.addMonthsToDate(new Date(), 3));
            }
            transactionPartner.setValidFrom(new Date());
            transactionPartner.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionPartnerController.create(transactionPartner);
            responseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(responseData, messageList);
    }

    @Override
    public Response createClaim(Claim claim) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<String> data = new Data();

        String trxNo = numberRangeBean.getNumber(claim.getType());
        try {
            Transaction transaction = new Transaction(trxNo);
            transaction.setTransactionType(transactionTypeController.findConfigTransactionType(claim.getType()));
            transaction.setStatus(statusTypeController.findConfigStatusType("NEW"));
            transaction.setStatusDate(new Date());
            transaction.setValidFrom(new Date());
            transaction.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionController.create(transaction);

//            TransactionPartnerPK transactionPartnerPK = new TransactionPartnerPK("MAINMEMBER", trxNo, claim.getCustomer());
//            messageList.addAll(addPartner(transactionPartnerPK).getMessages());
//
//            transactionPartnerPK = new TransactionPartnerPK("DECEASED", trxNo, claim.getDeceased());
//            messageList.addAll(addPartner(transactionPartnerPK).getMessages());
//
//            transactionPartnerPK = new TransactionPartnerPK("CLAIMANT", trxNo, claim.getClaimant());
//            messageList.addAll(addPartner(transactionPartnerPK).getMessages());
//
//            messageList.addAll(addProduct(transaction, claim.getType(), 1).getMessages());
//
//            if (claim.getGrocery() != null) {
//                messageList.addAll(addProduct(transaction, claim.getGrocery(), 1).getMessages());
//            }
//
//            if ("CASHPAYOUT".equals(claim.getType()) || "CASHPAYOUT".equals(claim.getGrocery())) {
//                messageList.addAll(addBank(claim.getBankAccount()).getMessages());
//            }
//
//            TransactionLocationPK transactionLocationPK = new TransactionLocationPK("SUBMISSIONOFFICE", trxNo, claim.getSubmissionOffice());
//            messageList.addAll(addLocation(transactionLocationPK).getMessages());
//
//            transactionLocationPK = new TransactionLocationPK("COLLECTIONOFFICE", trxNo, claim.getCollectionOffice());
//            messageList.addAll(addLocation(transactionLocationPK).getMessages());
//
//            messageList.addAll(addDate(trxNo, "SUBMISSIONDATE", new Date()).getMessages());
//            messageList.addAll(addDate(trxNo, "DEATHDATE", Conversion.stringToDate(claim.getDeathDate())).getMessages());
//            messageList.addAll(addDate(trxNo, "FUNERALDATE", Conversion.stringToDate(claim.getFuneralDate())).getMessages());
            data.set(trxNo);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);

            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);

            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }

    private Response addBank(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> responseData = new Data();
        Data<BankAccount> bankData = request.getData();
        BankAccount bankAccount = bankData.get();
        try {
            TransactionBank trxBank = new TransactionBank();
            trxBank.setAccountHolder(bankAccount.getAccountHolder());
            trxBank.setTransactionNo(transactionController.findTransaction(bankAccount.getOrderNo()));
            trxBank.setAccountNo(bankAccount.getAccountNumber());
            trxBank.setAccountType(bankAccountTypeController.findConfigBankAccountType(bankAccount.getAccountType()));
            trxBank.setBankName(bankNameController.findConfigBankName(bankAccount.getBankName()));
            trxBank.setValidFrom(new Date());
            trxBank.setValidTo(Conversion.stringToDate("9999-12-31"));
            trxBank.setAccountIdNumber(bankAccount.getAccountHolderIDNumber());
            trxBank.setUsageType(accountUsageController.findConfigBankAccountUsage(bankAccount.getUsageType()));
            transactionBankController.create(trxBank);
            responseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(responseData, messageList);
    }

    @Override
    public Response create(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<String> data = new Data();
        Data<Order> orderData = request.getData();
        Order order = orderData.get();
        try {
            order.setId(numberRangeBean.getNumber(order.getType()));
            Transaction transaction = new Transaction(order.getId());
            transaction.setTransactionType(transactionTypeController.findConfigTransactionType(order.getType()));
            transaction.setStatus(statusTypeController.findConfigStatusType("NEW"));
            transaction.setStatusDate(new Date());
            transaction.setValidFrom(new Date());
            transaction.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionController.create(transaction);
            data.set(order.getId());
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

            for (OrderItem orderItem : order.getItems()) {
                Request itemAddRequest = new Request();
                itemAddRequest.setRequester(request.getRequester());
                orderItem.setOrderNo(order.getId());
                Data<OrderItem> itemData = new Data();
                itemData.set(orderItem);
                itemAddRequest.setData(itemData);
                messageList.addAll(addProduct(itemAddRequest).getMessages());
            }
            for (OrderArea orderArea : order.getAreas()) {
                Request addLocationRequest = new Request();
                addLocationRequest.setRequester(request.getRequester());
                orderArea.setOrderNo(order.getId());
                Data<OrderArea> locationData = new Data();
                locationData.set(orderArea);
                addLocationRequest.setData(locationData);
                messageList.addAll(addLocation(addLocationRequest).getMessages());
            }
            for (OrderDate orderDate : order.getDates()) {
                Request addDateRequest = new Request();
                addDateRequest.setRequester(request.getRequester());
                orderDate.setOrderNo(order.getId());
                Data<OrderDate> dateData = new Data();
                dateData.set(orderDate);
                addDateRequest.setData(dateData);
                messageList.addAll(addDate(addDateRequest).getMessages());
            }
            for (OrderPartner orderPartner : order.getPartners()) {
                Request addPartnerRequest = new Request();
                addPartnerRequest.setRequester(request.getRequester());
                orderPartner.setOrderNo(order.getId());
                Data<OrderPartner> partnerData = new Data();
                partnerData.set(orderPartner);
                addPartnerRequest.setData(partnerData);
                messageList.addAll(addPartner(addPartnerRequest).getMessages());
            }
            for (OrderPayment orderPayment : order.getPayments()) {
                Request addPaymentRequest = new Request();
                addPaymentRequest.setRequester(request.getRequester());
                orderPayment.setOrderNo(order.getId());
                Data<OrderPayment> paymentData = new Data();
                paymentData.set(orderPayment);
                addPaymentRequest.setData(paymentData);
                messageList.addAll(addPayment(addPaymentRequest).getMessages());
            }
            for (BankAccount bankAccount : order.getBankAccounts()) {
                Request addBankRequest = new Request();
                addBankRequest.setRequester(request.getRequester());
                bankAccount.setOrderNo(order.getId());
                Data<BankAccount> bankData = new Data();
                bankData.set(bankAccount);
                addBankRequest.setData(bankData);
                messageList.addAll(addBank(addBankRequest).getMessages());
            }

            for (ReferenceDocument referenceDocument : order.getReferenceDocuments()) {
                Request addReferenceRequest = new Request();
                addReferenceRequest.setRequester(request.getRequester());
                referenceDocument.setOrderNo(order.getId());
                Data<ReferenceDocument> referenceData = new Data();
                referenceData.set(referenceDocument);
                addReferenceRequest.setData(referenceData);
                messageList.addAll(addReferenceDocument(addReferenceRequest).getMessages());
            }

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response(data, messageList);
    }

    private String getPartnerNo(OrderPartner orderPartner) {
        String idnumber = orderPartner.getIdNumber();
        String cellNumber = orderPartner.getCellNumber();
        String emailAddress = orderPartner.getEmail();
        List<String> roleList = new ArrayList<>();
//        if (roleType != null) {
//            if (roleType.length() > 0) {
//                roleList.add(roleType);
//            }
//        }
        List<Identity> identityList = new ArrayList<>();
        if (idnumber != null) {
            if (idnumber.length() > 0) {
                Identity ident = new Identity();
                ident.setType("SAIDENTITY");
                ident.setNumber(idnumber);
                identityList.add(ident);
            }
        }

        List<Contact> contactList = new ArrayList<>();
        if (cellNumber != null) {
            Contact contact = new Contact();
            contact.setContactType("CEL1");
            contact.setContactValue(cellNumber);
            contactList.add(contact);
        }
//        if (alternateCellNumber != null) {
//            Contact contact = new Contact();
//            contact.setContactType("CEL2");
//            contact.setContactValue(alternateCellNumber);
//            contactList.add(contact);
//        }
        if (emailAddress != null) {
            Contact contact = new Contact();
            contact.setContactType("EMAIL");
            contact.setContactValue(emailAddress);
            contactList.add(contact);
        }

        Person person = new Person();
        person.setLastName(orderPartner.getLastName());
        person.setFirstName(orderPartner.getFirstName());
        person.setMiddleName(orderPartner.getMiddleName());
        person.setRoles(roleList);
        person.setContacts(contactList);
        person.setIdentity(identityList);
        Response response = partnerBean.create(person);
        Data<String> responseData = response.getData();
        return responseData.get();
    }

    @Override
    public Response replaceDependent(Request request) {
        return null;
    }

    @Override
    public Response deactivateDependent(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> responseData = new Data();
        Data<Dependent> dependentData = request.getData();
        Dependent dependent = dependentData.get();
        try {

            TransactionPartnerPK transactionPartnerPK = new TransactionPartnerPK();
            transactionPartnerPK.setPartnerNo(dependent.getId());
            transactionPartnerPK.setPartnerType("DEPENDENT");
            transactionPartnerPK.setTransactionNo(dependent.getPolicyNo());
            TransactionPartner transactionPartner = transactionPartnerController.findTransactionPartner(transactionPartnerPK);

            transactionPartner.setStatus("INACTIVE");
            transactionPartner.setStatusDate(new Date());
            transactionPartner.setStatusReason("DECEASED");

            transactionPartnerController.edit(transactionPartner);
            responseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(responseData, messageList);
    }

    @Override
    public Response addReferenceDocument(Request request) {
        init();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Boolean> responseData = new Data();
        Data<ReferenceDocument> refDocData = request.getData();
        ReferenceDocument refDoc = refDocData.get();
        try {
            TransactionRelationPK transactionRelationPK = new TransactionRelationPK(refDoc.getDocType(), refDoc.getOrderNo(), refDoc.getDocID());
            TransactionRelation transactionRelation = new TransactionRelation(transactionRelationPK);

            transactionRelation.setConfigTransactionType(transactionTypeController.findConfigTransactionType(refDoc.getDocType()));
            transactionRelation.setTransaction(transactionController.findTransaction(refDoc.getOrderNo()));
            transactionRelation.setValidFrom(new Date());
            transactionRelation.setValidTo(Conversion.stringToDate("9999-12-31"));
            transactionRelationController.create(transactionRelation);
            responseData.set(Boolean.TRUE);
            MessageContainer message = new MessageContainer("S", "Processed Succesfully");
            messageList.add(message);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(TransactionBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(responseData, messageList);

    }

    @Override
    public Response getTransactionType(String transactionNo) {
        init();
        Response response = null;
        List<MessageContainer> messageList = new ArrayList<>();
        Transaction transaction = transactionController.findTransaction(transactionNo);
        if (transaction != null) {
            Data<String> data = new Data();
            data.set(transaction.getTransactionType().getDescription().replaceAll("\\s", ""));
            response = new Response(data, messageList);

        }
        return response;
    }

    @Override
    public Response search(OrderQuery query) {
        init();
        List<Order> orderList = new ArrayList<>();
        List<Transaction> transactionList = new ArrayList<>();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<List<Order>> data = new Data();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Transaction> q = cb.createQuery(Transaction.class);
            Root<Transaction> c = q.from(Transaction.class);
            ParameterExpression<String> p = cb.parameter(String.class);

            Path<Date> dateEntryPath = c.get("validFrom");
//            Path<Date> type = c.get("transactionType");
            Predicate predicate = cb.between(dateEntryPath, Conversion.stringToDate(query.getStartDate()), Conversion.addDaysToDate(Conversion.stringToDate(query.getEndDate()), 1));
//            cb.and(query.getType().equals(context));
            q.select(c).where(predicate);

            TypedQuery<Transaction> typeQuery = em.createQuery(q);
//            query.setParameter(p, checkingQuery.getStartDate());
//            query.setParameter(p, checkingQuery.getStartDate());
            transactionList = typeQuery.getResultList();
            for (Transaction transaction : transactionList) {
                Order order = new Order(transaction);
                orderList.add(order);
            }

        } finally {
            //em.close();

        }
        data.set(orderList);
        return new Response(data, messageList);
    }

}
