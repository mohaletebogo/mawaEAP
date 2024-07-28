/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.util.ArrayList;
import java.util.List;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionBank;
import za.raretag.mawa.entities.TransactionDate;
import za.raretag.mawa.entities.TransactionItem;
import za.raretag.mawa.entities.TransactionLocation;
import za.raretag.mawa.entities.TransactionPartner;
import za.raretag.mawa.entities.TransactionRelation;

/**
 *
 * @author tebogom
 */
public class Order {
    
    public Order() {
    }
    
    public Order(Transaction transaction) {
        
        this.id = transaction.getTransactionNo();
        this.type = transaction.getTransactionType().getDescription();        
//        this.customer = ;
        this.status = transaction.getStatus().getDescription();
        for (TransactionItem item : transaction.getTransactionItemCollection()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCode(item.getProductId().getProductCode());
            orderItem.setItemDescription(item.getProductId().getDescription());
            orderItem.setQuantity(item.getQuantity().toString());
            this.items.add(orderItem);
        }
        
        for (TransactionPartner partner : transaction.getTransactionPartnerCollection()) {
            OrderPartner orderPartner = new OrderPartner(partner);
            this.partners.add(orderPartner);
        }
        for (TransactionDate date : transaction.getTransactionDateCollection()) {
            OrderDate orderDate = new OrderDate();
            orderDate.setType(date.getConfigDateType().getId());
            orderDate.setTypeDescription(date.getConfigDateType().getDescription());
            orderDate.setValue(date.getDateValue());
            this.dates.add(orderDate);
        }
        for (TransactionLocation area : transaction.getTransactionLocationCollection()) {
            OrderArea orderArea = new OrderArea();
            orderArea.setType(area.getConfigLocationType().getId());
            orderArea.setTypeDescription(area.getConfigLocationType().getDescription());
            orderArea.setAreaID(area.getConfigSalesArea().getId());
            orderArea.setAreaDescription(area.getConfigSalesArea().getDescription());
            this.areas.add(orderArea);
        }
        
        for (TransactionRelation relation : transaction.getTransactionRelationCollection()) {
            ReferenceDocument refDoc = new ReferenceDocument();
            refDoc.setDocType(relation.getConfigTransactionType().getId());
            refDoc.setDocID(relation.getTransaction().getTransactionNo());
            this.referenceDocuments.add(refDoc);
        }
        
        for (TransactionBank bank : transaction.getTransactionBankCollection()) {            
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountHolder(bank.getAccountHolder());
            bankAccount.setUsageType(bank.getUsageType().getDescription());
            bankAccount.setAccountHolderIDNumber(bank.getAccountIdNumber());
            bankAccount.setAccountNumber(bank.getAccountNo());
            bankAccount.setAccountType(bank.getAccountType().getDescription());
            bankAccount.setBankName(bankAccount.getBankName());
            this.bankAccounts.add(bankAccount);
        }        
        
    }
    
    private String id;

    /**
     * Get the value of orderId
     *
     * @return the value of orderId
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    private String type;

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(String type) {
        this.type = type;
    }
    
    private String customer;

    /**
     * Get the value of customer
     *
     * @return the value of customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Set the value of customer
     *
     * @param customer new value of customer
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    
    private String status;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    private List<OrderItem> items = new ArrayList<>();

    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Set the value of items
     *
     * @param items new value of items
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
    
    private List<OrderPartner> partners = new ArrayList<>();;

    /**
     * Get the value of partners
     *
     * @return the value of partners
     */
    public List<OrderPartner> getPartners() {
        return partners;
    }

    /**
     * Set the value of partners
     *
     * @param partners new value of partners
     */
    public void setPartners(List<OrderPartner> partners) {
        this.partners = partners;
    }
    
    private List<OrderDate> dates = new ArrayList<>();;

    /**
     * Get the value of dates
     *
     * @return the value of dates
     */
    public List<OrderDate> getDates() {
        return dates;
    }

    /**
     * Set the value of dates
     *
     * @param dates new value of dates
     */
    public void setDates(List<OrderDate> dates) {
        this.dates = dates;
    }
    
    private List<OrderArea> areas = new ArrayList<>();;

    /**
     * Get the value of areas
     *
     * @return the value of areas
     */
    public List<OrderArea> getAreas() {
        return areas;
    }

    /**
     * Set the value of areas
     *
     * @param areas new value of areas
     */
    public void setAreas(List<OrderArea> areas) {
        this.areas = areas;
    }
    
    private List<BankAccount> bankAccounts = new ArrayList<>();;

    /**
     * Get the value of bankAccount
     *
     * @return the value of bankAccount
     */
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    /**
     * Set the value of bankAccount
     *
     * @param bankAccounts new value of bankAccounts
     */
    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
    
    private List<ReferenceDocument> referenceDocuments = new ArrayList<>();;

    /**
     * Get the value of referenceDocuments
     *
     * @return the value of referenceDocuments
     */
    public List<ReferenceDocument> getReferenceDocuments() {
        return referenceDocuments;
    }

    /**
     * Set the value of referenceDocuments
     *
     * @param referenceDocuments new value of referenceDocuments
     */
    public void setReferenceDocuments(List<ReferenceDocument> referenceDocuments) {
        this.referenceDocuments = referenceDocuments;
    }
    
    private List<OrderPayment> payments = new ArrayList<>();

    /**
     * Get the value of payments
     *
     * @return the value of payments
     */
    public List<OrderPayment> getPayments() {
        return payments;
    }

    /**
     * Set the value of payments
     *
     * @param payments new value of payments
     */
    public void setPayments(List<OrderPayment> payments) {
        this.payments = payments;
    }
    
}
