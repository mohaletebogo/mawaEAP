/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import za.raretag.mawa.entities.TransactionDocument;
import za.raretag.mawa.entities.ConfigStatusType;
import za.raretag.mawa.entities.ConfigTransactionType;
import za.raretag.mawa.entities.TransactionItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionRelation;
import za.raretag.mawa.entities.TransactionPayment;
import za.raretag.mawa.entities.TransactionDate;
import za.raretag.mawa.entities.TransactionBank;
import za.raretag.mawa.entities.TransactionPartner;
import za.raretag.mawa.entities.TransactionStatus;
import za.raretag.mawa.entities.TransactionAmount;
import za.raretag.mawa.entities.TransactionLocation;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionJpaController implements Serializable {

    public TransactionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaction transaction) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transaction.getTransactionItemCollection() == null) {
            transaction.setTransactionItemCollection(new ArrayList<TransactionItem>());
        }
        if (transaction.getTransactionRelationCollection() == null) {
            transaction.setTransactionRelationCollection(new ArrayList<TransactionRelation>());
        }
        if (transaction.getTransactionRelationCollection1() == null) {
            transaction.setTransactionRelationCollection1(new ArrayList<TransactionRelation>());
        }
        if (transaction.getTransactionPaymentCollection() == null) {
            transaction.setTransactionPaymentCollection(new ArrayList<TransactionPayment>());
        }
        if (transaction.getTransactionDateCollection() == null) {
            transaction.setTransactionDateCollection(new ArrayList<TransactionDate>());
        }
        if (transaction.getTransactionBankCollection() == null) {
            transaction.setTransactionBankCollection(new ArrayList<TransactionBank>());
        }
        if (transaction.getTransactionPartnerCollection() == null) {
            transaction.setTransactionPartnerCollection(new ArrayList<TransactionPartner>());
        }
        if (transaction.getTransactionStatusCollection() == null) {
            transaction.setTransactionStatusCollection(new ArrayList<TransactionStatus>());
        }
        if (transaction.getTransactionAmountCollection() == null) {
            transaction.setTransactionAmountCollection(new ArrayList<TransactionAmount>());
        }
        if (transaction.getTransactionLocationCollection() == null) {
            transaction.setTransactionLocationCollection(new ArrayList<TransactionLocation>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionDocument transactionDocument = transaction.getTransactionDocument();
            if (transactionDocument != null) {
                transactionDocument = em.getReference(transactionDocument.getClass(), transactionDocument.getTransactionNo());
                transaction.setTransactionDocument(transactionDocument);
            }
            ConfigStatusType status = transaction.getStatus();
            if (status != null) {
                status = em.getReference(status.getClass(), status.getId());
                transaction.setStatus(status);
            }
            ConfigTransactionType transactionType = transaction.getTransactionType();
            if (transactionType != null) {
                transactionType = em.getReference(transactionType.getClass(), transactionType.getId());
                transaction.setTransactionType(transactionType);
            }
            Collection<TransactionItem> attachedTransactionItemCollection = new ArrayList<TransactionItem>();
            for (TransactionItem transactionItemCollectionTransactionItemToAttach : transaction.getTransactionItemCollection()) {
                transactionItemCollectionTransactionItemToAttach = em.getReference(transactionItemCollectionTransactionItemToAttach.getClass(), transactionItemCollectionTransactionItemToAttach.getItemId());
                attachedTransactionItemCollection.add(transactionItemCollectionTransactionItemToAttach);
            }
            transaction.setTransactionItemCollection(attachedTransactionItemCollection);
            Collection<TransactionRelation> attachedTransactionRelationCollection = new ArrayList<TransactionRelation>();
            for (TransactionRelation transactionRelationCollectionTransactionRelationToAttach : transaction.getTransactionRelationCollection()) {
                transactionRelationCollectionTransactionRelationToAttach = em.getReference(transactionRelationCollectionTransactionRelationToAttach.getClass(), transactionRelationCollectionTransactionRelationToAttach.getTransactionRelationPK());
                attachedTransactionRelationCollection.add(transactionRelationCollectionTransactionRelationToAttach);
            }
            transaction.setTransactionRelationCollection(attachedTransactionRelationCollection);
            Collection<TransactionRelation> attachedTransactionRelationCollection1 = new ArrayList<TransactionRelation>();
            for (TransactionRelation transactionRelationCollection1TransactionRelationToAttach : transaction.getTransactionRelationCollection1()) {
                transactionRelationCollection1TransactionRelationToAttach = em.getReference(transactionRelationCollection1TransactionRelationToAttach.getClass(), transactionRelationCollection1TransactionRelationToAttach.getTransactionRelationPK());
                attachedTransactionRelationCollection1.add(transactionRelationCollection1TransactionRelationToAttach);
            }
            transaction.setTransactionRelationCollection1(attachedTransactionRelationCollection1);
            Collection<TransactionPayment> attachedTransactionPaymentCollection = new ArrayList<TransactionPayment>();
            for (TransactionPayment transactionPaymentCollectionTransactionPaymentToAttach : transaction.getTransactionPaymentCollection()) {
                transactionPaymentCollectionTransactionPaymentToAttach = em.getReference(transactionPaymentCollectionTransactionPaymentToAttach.getClass(), transactionPaymentCollectionTransactionPaymentToAttach.getPaymentNo());
                attachedTransactionPaymentCollection.add(transactionPaymentCollectionTransactionPaymentToAttach);
            }
            transaction.setTransactionPaymentCollection(attachedTransactionPaymentCollection);
            Collection<TransactionDate> attachedTransactionDateCollection = new ArrayList<TransactionDate>();
            for (TransactionDate transactionDateCollectionTransactionDateToAttach : transaction.getTransactionDateCollection()) {
                transactionDateCollectionTransactionDateToAttach = em.getReference(transactionDateCollectionTransactionDateToAttach.getClass(), transactionDateCollectionTransactionDateToAttach.getTransactionDatePK());
                attachedTransactionDateCollection.add(transactionDateCollectionTransactionDateToAttach);
            }
            transaction.setTransactionDateCollection(attachedTransactionDateCollection);
            Collection<TransactionBank> attachedTransactionBankCollection = new ArrayList<TransactionBank>();
            for (TransactionBank transactionBankCollectionTransactionBankToAttach : transaction.getTransactionBankCollection()) {
                transactionBankCollectionTransactionBankToAttach = em.getReference(transactionBankCollectionTransactionBankToAttach.getClass(), transactionBankCollectionTransactionBankToAttach.getTransactionBankId());
                attachedTransactionBankCollection.add(transactionBankCollectionTransactionBankToAttach);
            }
            transaction.setTransactionBankCollection(attachedTransactionBankCollection);
            Collection<TransactionPartner> attachedTransactionPartnerCollection = new ArrayList<TransactionPartner>();
            for (TransactionPartner transactionPartnerCollectionTransactionPartnerToAttach : transaction.getTransactionPartnerCollection()) {
                transactionPartnerCollectionTransactionPartnerToAttach = em.getReference(transactionPartnerCollectionTransactionPartnerToAttach.getClass(), transactionPartnerCollectionTransactionPartnerToAttach.getTransactionPartnerPK());
                attachedTransactionPartnerCollection.add(transactionPartnerCollectionTransactionPartnerToAttach);
            }
            transaction.setTransactionPartnerCollection(attachedTransactionPartnerCollection);
            Collection<TransactionStatus> attachedTransactionStatusCollection = new ArrayList<TransactionStatus>();
            for (TransactionStatus transactionStatusCollectionTransactionStatusToAttach : transaction.getTransactionStatusCollection()) {
                transactionStatusCollectionTransactionStatusToAttach = em.getReference(transactionStatusCollectionTransactionStatusToAttach.getClass(), transactionStatusCollectionTransactionStatusToAttach.getTransactionStatusPK());
                attachedTransactionStatusCollection.add(transactionStatusCollectionTransactionStatusToAttach);
            }
            transaction.setTransactionStatusCollection(attachedTransactionStatusCollection);
            Collection<TransactionAmount> attachedTransactionAmountCollection = new ArrayList<TransactionAmount>();
            for (TransactionAmount transactionAmountCollectionTransactionAmountToAttach : transaction.getTransactionAmountCollection()) {
                transactionAmountCollectionTransactionAmountToAttach = em.getReference(transactionAmountCollectionTransactionAmountToAttach.getClass(), transactionAmountCollectionTransactionAmountToAttach.getTransactionAmountPK());
                attachedTransactionAmountCollection.add(transactionAmountCollectionTransactionAmountToAttach);
            }
            transaction.setTransactionAmountCollection(attachedTransactionAmountCollection);
            Collection<TransactionLocation> attachedTransactionLocationCollection = new ArrayList<TransactionLocation>();
            for (TransactionLocation transactionLocationCollectionTransactionLocationToAttach : transaction.getTransactionLocationCollection()) {
                transactionLocationCollectionTransactionLocationToAttach = em.getReference(transactionLocationCollectionTransactionLocationToAttach.getClass(), transactionLocationCollectionTransactionLocationToAttach.getTransactionLocationPK());
                attachedTransactionLocationCollection.add(transactionLocationCollectionTransactionLocationToAttach);
            }
            transaction.setTransactionLocationCollection(attachedTransactionLocationCollection);
            em.persist(transaction);
            if (transactionDocument != null) {
                Transaction oldTransactionOfTransactionDocument = transactionDocument.getTransaction();
                if (oldTransactionOfTransactionDocument != null) {
                    oldTransactionOfTransactionDocument.setTransactionDocument(null);
                    oldTransactionOfTransactionDocument = em.merge(oldTransactionOfTransactionDocument);
                }
                transactionDocument.setTransaction(transaction);
                transactionDocument = em.merge(transactionDocument);
            }
            if (status != null) {
                status.getTransactionCollection().add(transaction);
                status = em.merge(status);
            }
            if (transactionType != null) {
                transactionType.getTransactionCollection().add(transaction);
                transactionType = em.merge(transactionType);
            }
            for (TransactionItem transactionItemCollectionTransactionItem : transaction.getTransactionItemCollection()) {
                Transaction oldTransactionNoOfTransactionItemCollectionTransactionItem = transactionItemCollectionTransactionItem.getTransactionNo();
                transactionItemCollectionTransactionItem.setTransactionNo(transaction);
                transactionItemCollectionTransactionItem = em.merge(transactionItemCollectionTransactionItem);
                if (oldTransactionNoOfTransactionItemCollectionTransactionItem != null) {
                    oldTransactionNoOfTransactionItemCollectionTransactionItem.getTransactionItemCollection().remove(transactionItemCollectionTransactionItem);
                    oldTransactionNoOfTransactionItemCollectionTransactionItem = em.merge(oldTransactionNoOfTransactionItemCollectionTransactionItem);
                }
            }
            for (TransactionRelation transactionRelationCollectionTransactionRelation : transaction.getTransactionRelationCollection()) {
                Transaction oldTransactionOfTransactionRelationCollectionTransactionRelation = transactionRelationCollectionTransactionRelation.getTransaction();
                transactionRelationCollectionTransactionRelation.setTransaction(transaction);
                transactionRelationCollectionTransactionRelation = em.merge(transactionRelationCollectionTransactionRelation);
                if (oldTransactionOfTransactionRelationCollectionTransactionRelation != null) {
                    oldTransactionOfTransactionRelationCollectionTransactionRelation.getTransactionRelationCollection().remove(transactionRelationCollectionTransactionRelation);
                    oldTransactionOfTransactionRelationCollectionTransactionRelation = em.merge(oldTransactionOfTransactionRelationCollectionTransactionRelation);
                }
            }
            for (TransactionRelation transactionRelationCollection1TransactionRelation : transaction.getTransactionRelationCollection1()) {
                Transaction oldTransaction1OfTransactionRelationCollection1TransactionRelation = transactionRelationCollection1TransactionRelation.getTransaction1();
                transactionRelationCollection1TransactionRelation.setTransaction1(transaction);
                transactionRelationCollection1TransactionRelation = em.merge(transactionRelationCollection1TransactionRelation);
                if (oldTransaction1OfTransactionRelationCollection1TransactionRelation != null) {
                    oldTransaction1OfTransactionRelationCollection1TransactionRelation.getTransactionRelationCollection1().remove(transactionRelationCollection1TransactionRelation);
                    oldTransaction1OfTransactionRelationCollection1TransactionRelation = em.merge(oldTransaction1OfTransactionRelationCollection1TransactionRelation);
                }
            }
            for (TransactionPayment transactionPaymentCollectionTransactionPayment : transaction.getTransactionPaymentCollection()) {
                Transaction oldTransactionNoOfTransactionPaymentCollectionTransactionPayment = transactionPaymentCollectionTransactionPayment.getTransactionNo();
                transactionPaymentCollectionTransactionPayment.setTransactionNo(transaction);
                transactionPaymentCollectionTransactionPayment = em.merge(transactionPaymentCollectionTransactionPayment);
                if (oldTransactionNoOfTransactionPaymentCollectionTransactionPayment != null) {
                    oldTransactionNoOfTransactionPaymentCollectionTransactionPayment.getTransactionPaymentCollection().remove(transactionPaymentCollectionTransactionPayment);
                    oldTransactionNoOfTransactionPaymentCollectionTransactionPayment = em.merge(oldTransactionNoOfTransactionPaymentCollectionTransactionPayment);
                }
            }
            for (TransactionDate transactionDateCollectionTransactionDate : transaction.getTransactionDateCollection()) {
                Transaction oldTransactionOfTransactionDateCollectionTransactionDate = transactionDateCollectionTransactionDate.getTransaction();
                transactionDateCollectionTransactionDate.setTransaction(transaction);
                transactionDateCollectionTransactionDate = em.merge(transactionDateCollectionTransactionDate);
                if (oldTransactionOfTransactionDateCollectionTransactionDate != null) {
                    oldTransactionOfTransactionDateCollectionTransactionDate.getTransactionDateCollection().remove(transactionDateCollectionTransactionDate);
                    oldTransactionOfTransactionDateCollectionTransactionDate = em.merge(oldTransactionOfTransactionDateCollectionTransactionDate);
                }
            }
            for (TransactionBank transactionBankCollectionTransactionBank : transaction.getTransactionBankCollection()) {
                Transaction oldTransactionNoOfTransactionBankCollectionTransactionBank = transactionBankCollectionTransactionBank.getTransactionNo();
                transactionBankCollectionTransactionBank.setTransactionNo(transaction);
                transactionBankCollectionTransactionBank = em.merge(transactionBankCollectionTransactionBank);
                if (oldTransactionNoOfTransactionBankCollectionTransactionBank != null) {
                    oldTransactionNoOfTransactionBankCollectionTransactionBank.getTransactionBankCollection().remove(transactionBankCollectionTransactionBank);
                    oldTransactionNoOfTransactionBankCollectionTransactionBank = em.merge(oldTransactionNoOfTransactionBankCollectionTransactionBank);
                }
            }
            for (TransactionPartner transactionPartnerCollectionTransactionPartner : transaction.getTransactionPartnerCollection()) {
                Transaction oldTransactionOfTransactionPartnerCollectionTransactionPartner = transactionPartnerCollectionTransactionPartner.getTransaction();
                transactionPartnerCollectionTransactionPartner.setTransaction(transaction);
                transactionPartnerCollectionTransactionPartner = em.merge(transactionPartnerCollectionTransactionPartner);
                if (oldTransactionOfTransactionPartnerCollectionTransactionPartner != null) {
                    oldTransactionOfTransactionPartnerCollectionTransactionPartner.getTransactionPartnerCollection().remove(transactionPartnerCollectionTransactionPartner);
                    oldTransactionOfTransactionPartnerCollectionTransactionPartner = em.merge(oldTransactionOfTransactionPartnerCollectionTransactionPartner);
                }
            }
            for (TransactionStatus transactionStatusCollectionTransactionStatus : transaction.getTransactionStatusCollection()) {
                Transaction oldTransactionOfTransactionStatusCollectionTransactionStatus = transactionStatusCollectionTransactionStatus.getTransaction();
                transactionStatusCollectionTransactionStatus.setTransaction(transaction);
                transactionStatusCollectionTransactionStatus = em.merge(transactionStatusCollectionTransactionStatus);
                if (oldTransactionOfTransactionStatusCollectionTransactionStatus != null) {
                    oldTransactionOfTransactionStatusCollectionTransactionStatus.getTransactionStatusCollection().remove(transactionStatusCollectionTransactionStatus);
                    oldTransactionOfTransactionStatusCollectionTransactionStatus = em.merge(oldTransactionOfTransactionStatusCollectionTransactionStatus);
                }
            }
            for (TransactionAmount transactionAmountCollectionTransactionAmount : transaction.getTransactionAmountCollection()) {
                Transaction oldTransactionOfTransactionAmountCollectionTransactionAmount = transactionAmountCollectionTransactionAmount.getTransaction();
                transactionAmountCollectionTransactionAmount.setTransaction(transaction);
                transactionAmountCollectionTransactionAmount = em.merge(transactionAmountCollectionTransactionAmount);
                if (oldTransactionOfTransactionAmountCollectionTransactionAmount != null) {
                    oldTransactionOfTransactionAmountCollectionTransactionAmount.getTransactionAmountCollection().remove(transactionAmountCollectionTransactionAmount);
                    oldTransactionOfTransactionAmountCollectionTransactionAmount = em.merge(oldTransactionOfTransactionAmountCollectionTransactionAmount);
                }
            }
            for (TransactionLocation transactionLocationCollectionTransactionLocation : transaction.getTransactionLocationCollection()) {
                Transaction oldTransactionOfTransactionLocationCollectionTransactionLocation = transactionLocationCollectionTransactionLocation.getTransaction();
                transactionLocationCollectionTransactionLocation.setTransaction(transaction);
                transactionLocationCollectionTransactionLocation = em.merge(transactionLocationCollectionTransactionLocation);
                if (oldTransactionOfTransactionLocationCollectionTransactionLocation != null) {
                    oldTransactionOfTransactionLocationCollectionTransactionLocation.getTransactionLocationCollection().remove(transactionLocationCollectionTransactionLocation);
                    oldTransactionOfTransactionLocationCollectionTransactionLocation = em.merge(oldTransactionOfTransactionLocationCollectionTransactionLocation);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransaction(transaction.getTransactionNo()) != null) {
                throw new PreexistingEntityException("Transaction " + transaction + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaction transaction) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction persistentTransaction = em.find(Transaction.class, transaction.getTransactionNo());
            TransactionDocument transactionDocumentOld = persistentTransaction.getTransactionDocument();
            TransactionDocument transactionDocumentNew = transaction.getTransactionDocument();
            ConfigStatusType statusOld = persistentTransaction.getStatus();
            ConfigStatusType statusNew = transaction.getStatus();
            ConfigTransactionType transactionTypeOld = persistentTransaction.getTransactionType();
            ConfigTransactionType transactionTypeNew = transaction.getTransactionType();
            Collection<TransactionItem> transactionItemCollectionOld = persistentTransaction.getTransactionItemCollection();
            Collection<TransactionItem> transactionItemCollectionNew = transaction.getTransactionItemCollection();
            Collection<TransactionRelation> transactionRelationCollectionOld = persistentTransaction.getTransactionRelationCollection();
            Collection<TransactionRelation> transactionRelationCollectionNew = transaction.getTransactionRelationCollection();
            Collection<TransactionRelation> transactionRelationCollection1Old = persistentTransaction.getTransactionRelationCollection1();
            Collection<TransactionRelation> transactionRelationCollection1New = transaction.getTransactionRelationCollection1();
            Collection<TransactionPayment> transactionPaymentCollectionOld = persistentTransaction.getTransactionPaymentCollection();
            Collection<TransactionPayment> transactionPaymentCollectionNew = transaction.getTransactionPaymentCollection();
            Collection<TransactionDate> transactionDateCollectionOld = persistentTransaction.getTransactionDateCollection();
            Collection<TransactionDate> transactionDateCollectionNew = transaction.getTransactionDateCollection();
            Collection<TransactionBank> transactionBankCollectionOld = persistentTransaction.getTransactionBankCollection();
            Collection<TransactionBank> transactionBankCollectionNew = transaction.getTransactionBankCollection();
            Collection<TransactionPartner> transactionPartnerCollectionOld = persistentTransaction.getTransactionPartnerCollection();
            Collection<TransactionPartner> transactionPartnerCollectionNew = transaction.getTransactionPartnerCollection();
            Collection<TransactionStatus> transactionStatusCollectionOld = persistentTransaction.getTransactionStatusCollection();
            Collection<TransactionStatus> transactionStatusCollectionNew = transaction.getTransactionStatusCollection();
            Collection<TransactionAmount> transactionAmountCollectionOld = persistentTransaction.getTransactionAmountCollection();
            Collection<TransactionAmount> transactionAmountCollectionNew = transaction.getTransactionAmountCollection();
            Collection<TransactionLocation> transactionLocationCollectionOld = persistentTransaction.getTransactionLocationCollection();
            Collection<TransactionLocation> transactionLocationCollectionNew = transaction.getTransactionLocationCollection();
            List<String> illegalOrphanMessages = null;
            if (transactionDocumentOld != null && !transactionDocumentOld.equals(transactionDocumentNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TransactionDocument " + transactionDocumentOld + " since its transaction field is not nullable.");
            }
            for (TransactionRelation transactionRelationCollectionOldTransactionRelation : transactionRelationCollectionOld) {
                if (!transactionRelationCollectionNew.contains(transactionRelationCollectionOldTransactionRelation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionRelation " + transactionRelationCollectionOldTransactionRelation + " since its transaction field is not nullable.");
                }
            }
            for (TransactionRelation transactionRelationCollection1OldTransactionRelation : transactionRelationCollection1Old) {
                if (!transactionRelationCollection1New.contains(transactionRelationCollection1OldTransactionRelation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionRelation " + transactionRelationCollection1OldTransactionRelation + " since its transaction1 field is not nullable.");
                }
            }
            for (TransactionDate transactionDateCollectionOldTransactionDate : transactionDateCollectionOld) {
                if (!transactionDateCollectionNew.contains(transactionDateCollectionOldTransactionDate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionDate " + transactionDateCollectionOldTransactionDate + " since its transaction field is not nullable.");
                }
            }
            for (TransactionBank transactionBankCollectionOldTransactionBank : transactionBankCollectionOld) {
                if (!transactionBankCollectionNew.contains(transactionBankCollectionOldTransactionBank)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionBank " + transactionBankCollectionOldTransactionBank + " since its transactionNo field is not nullable.");
                }
            }
            for (TransactionPartner transactionPartnerCollectionOldTransactionPartner : transactionPartnerCollectionOld) {
                if (!transactionPartnerCollectionNew.contains(transactionPartnerCollectionOldTransactionPartner)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionPartner " + transactionPartnerCollectionOldTransactionPartner + " since its transaction field is not nullable.");
                }
            }
            for (TransactionStatus transactionStatusCollectionOldTransactionStatus : transactionStatusCollectionOld) {
                if (!transactionStatusCollectionNew.contains(transactionStatusCollectionOldTransactionStatus)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionStatus " + transactionStatusCollectionOldTransactionStatus + " since its transaction field is not nullable.");
                }
            }
            for (TransactionAmount transactionAmountCollectionOldTransactionAmount : transactionAmountCollectionOld) {
                if (!transactionAmountCollectionNew.contains(transactionAmountCollectionOldTransactionAmount)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionAmount " + transactionAmountCollectionOldTransactionAmount + " since its transaction field is not nullable.");
                }
            }
            for (TransactionLocation transactionLocationCollectionOldTransactionLocation : transactionLocationCollectionOld) {
                if (!transactionLocationCollectionNew.contains(transactionLocationCollectionOldTransactionLocation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionLocation " + transactionLocationCollectionOldTransactionLocation + " since its transaction field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transactionDocumentNew != null) {
                transactionDocumentNew = em.getReference(transactionDocumentNew.getClass(), transactionDocumentNew.getTransactionNo());
                transaction.setTransactionDocument(transactionDocumentNew);
            }
            if (statusNew != null) {
                statusNew = em.getReference(statusNew.getClass(), statusNew.getId());
                transaction.setStatus(statusNew);
            }
            if (transactionTypeNew != null) {
                transactionTypeNew = em.getReference(transactionTypeNew.getClass(), transactionTypeNew.getId());
                transaction.setTransactionType(transactionTypeNew);
            }
            Collection<TransactionItem> attachedTransactionItemCollectionNew = new ArrayList<TransactionItem>();
            for (TransactionItem transactionItemCollectionNewTransactionItemToAttach : transactionItemCollectionNew) {
                transactionItemCollectionNewTransactionItemToAttach = em.getReference(transactionItemCollectionNewTransactionItemToAttach.getClass(), transactionItemCollectionNewTransactionItemToAttach.getItemId());
                attachedTransactionItemCollectionNew.add(transactionItemCollectionNewTransactionItemToAttach);
            }
            transactionItemCollectionNew = attachedTransactionItemCollectionNew;
            transaction.setTransactionItemCollection(transactionItemCollectionNew);
            Collection<TransactionRelation> attachedTransactionRelationCollectionNew = new ArrayList<TransactionRelation>();
            for (TransactionRelation transactionRelationCollectionNewTransactionRelationToAttach : transactionRelationCollectionNew) {
                transactionRelationCollectionNewTransactionRelationToAttach = em.getReference(transactionRelationCollectionNewTransactionRelationToAttach.getClass(), transactionRelationCollectionNewTransactionRelationToAttach.getTransactionRelationPK());
                attachedTransactionRelationCollectionNew.add(transactionRelationCollectionNewTransactionRelationToAttach);
            }
            transactionRelationCollectionNew = attachedTransactionRelationCollectionNew;
            transaction.setTransactionRelationCollection(transactionRelationCollectionNew);
            Collection<TransactionRelation> attachedTransactionRelationCollection1New = new ArrayList<TransactionRelation>();
            for (TransactionRelation transactionRelationCollection1NewTransactionRelationToAttach : transactionRelationCollection1New) {
                transactionRelationCollection1NewTransactionRelationToAttach = em.getReference(transactionRelationCollection1NewTransactionRelationToAttach.getClass(), transactionRelationCollection1NewTransactionRelationToAttach.getTransactionRelationPK());
                attachedTransactionRelationCollection1New.add(transactionRelationCollection1NewTransactionRelationToAttach);
            }
            transactionRelationCollection1New = attachedTransactionRelationCollection1New;
            transaction.setTransactionRelationCollection1(transactionRelationCollection1New);
            Collection<TransactionPayment> attachedTransactionPaymentCollectionNew = new ArrayList<TransactionPayment>();
            for (TransactionPayment transactionPaymentCollectionNewTransactionPaymentToAttach : transactionPaymentCollectionNew) {
                transactionPaymentCollectionNewTransactionPaymentToAttach = em.getReference(transactionPaymentCollectionNewTransactionPaymentToAttach.getClass(), transactionPaymentCollectionNewTransactionPaymentToAttach.getPaymentNo());
                attachedTransactionPaymentCollectionNew.add(transactionPaymentCollectionNewTransactionPaymentToAttach);
            }
            transactionPaymentCollectionNew = attachedTransactionPaymentCollectionNew;
            transaction.setTransactionPaymentCollection(transactionPaymentCollectionNew);
            Collection<TransactionDate> attachedTransactionDateCollectionNew = new ArrayList<TransactionDate>();
            for (TransactionDate transactionDateCollectionNewTransactionDateToAttach : transactionDateCollectionNew) {
                transactionDateCollectionNewTransactionDateToAttach = em.getReference(transactionDateCollectionNewTransactionDateToAttach.getClass(), transactionDateCollectionNewTransactionDateToAttach.getTransactionDatePK());
                attachedTransactionDateCollectionNew.add(transactionDateCollectionNewTransactionDateToAttach);
            }
            transactionDateCollectionNew = attachedTransactionDateCollectionNew;
            transaction.setTransactionDateCollection(transactionDateCollectionNew);
            Collection<TransactionBank> attachedTransactionBankCollectionNew = new ArrayList<TransactionBank>();
            for (TransactionBank transactionBankCollectionNewTransactionBankToAttach : transactionBankCollectionNew) {
                transactionBankCollectionNewTransactionBankToAttach = em.getReference(transactionBankCollectionNewTransactionBankToAttach.getClass(), transactionBankCollectionNewTransactionBankToAttach.getTransactionBankId());
                attachedTransactionBankCollectionNew.add(transactionBankCollectionNewTransactionBankToAttach);
            }
            transactionBankCollectionNew = attachedTransactionBankCollectionNew;
            transaction.setTransactionBankCollection(transactionBankCollectionNew);
            Collection<TransactionPartner> attachedTransactionPartnerCollectionNew = new ArrayList<TransactionPartner>();
            for (TransactionPartner transactionPartnerCollectionNewTransactionPartnerToAttach : transactionPartnerCollectionNew) {
                transactionPartnerCollectionNewTransactionPartnerToAttach = em.getReference(transactionPartnerCollectionNewTransactionPartnerToAttach.getClass(), transactionPartnerCollectionNewTransactionPartnerToAttach.getTransactionPartnerPK());
                attachedTransactionPartnerCollectionNew.add(transactionPartnerCollectionNewTransactionPartnerToAttach);
            }
            transactionPartnerCollectionNew = attachedTransactionPartnerCollectionNew;
            transaction.setTransactionPartnerCollection(transactionPartnerCollectionNew);
            Collection<TransactionStatus> attachedTransactionStatusCollectionNew = new ArrayList<TransactionStatus>();
            for (TransactionStatus transactionStatusCollectionNewTransactionStatusToAttach : transactionStatusCollectionNew) {
                transactionStatusCollectionNewTransactionStatusToAttach = em.getReference(transactionStatusCollectionNewTransactionStatusToAttach.getClass(), transactionStatusCollectionNewTransactionStatusToAttach.getTransactionStatusPK());
                attachedTransactionStatusCollectionNew.add(transactionStatusCollectionNewTransactionStatusToAttach);
            }
            transactionStatusCollectionNew = attachedTransactionStatusCollectionNew;
            transaction.setTransactionStatusCollection(transactionStatusCollectionNew);
            Collection<TransactionAmount> attachedTransactionAmountCollectionNew = new ArrayList<TransactionAmount>();
            for (TransactionAmount transactionAmountCollectionNewTransactionAmountToAttach : transactionAmountCollectionNew) {
                transactionAmountCollectionNewTransactionAmountToAttach = em.getReference(transactionAmountCollectionNewTransactionAmountToAttach.getClass(), transactionAmountCollectionNewTransactionAmountToAttach.getTransactionAmountPK());
                attachedTransactionAmountCollectionNew.add(transactionAmountCollectionNewTransactionAmountToAttach);
            }
            transactionAmountCollectionNew = attachedTransactionAmountCollectionNew;
            transaction.setTransactionAmountCollection(transactionAmountCollectionNew);
            Collection<TransactionLocation> attachedTransactionLocationCollectionNew = new ArrayList<TransactionLocation>();
            for (TransactionLocation transactionLocationCollectionNewTransactionLocationToAttach : transactionLocationCollectionNew) {
                transactionLocationCollectionNewTransactionLocationToAttach = em.getReference(transactionLocationCollectionNewTransactionLocationToAttach.getClass(), transactionLocationCollectionNewTransactionLocationToAttach.getTransactionLocationPK());
                attachedTransactionLocationCollectionNew.add(transactionLocationCollectionNewTransactionLocationToAttach);
            }
            transactionLocationCollectionNew = attachedTransactionLocationCollectionNew;
            transaction.setTransactionLocationCollection(transactionLocationCollectionNew);
            transaction = em.merge(transaction);
            if (transactionDocumentNew != null && !transactionDocumentNew.equals(transactionDocumentOld)) {
                Transaction oldTransactionOfTransactionDocument = transactionDocumentNew.getTransaction();
                if (oldTransactionOfTransactionDocument != null) {
                    oldTransactionOfTransactionDocument.setTransactionDocument(null);
                    oldTransactionOfTransactionDocument = em.merge(oldTransactionOfTransactionDocument);
                }
                transactionDocumentNew.setTransaction(transaction);
                transactionDocumentNew = em.merge(transactionDocumentNew);
            }
            if (statusOld != null && !statusOld.equals(statusNew)) {
                statusOld.getTransactionCollection().remove(transaction);
                statusOld = em.merge(statusOld);
            }
            if (statusNew != null && !statusNew.equals(statusOld)) {
                statusNew.getTransactionCollection().add(transaction);
                statusNew = em.merge(statusNew);
            }
            if (transactionTypeOld != null && !transactionTypeOld.equals(transactionTypeNew)) {
                transactionTypeOld.getTransactionCollection().remove(transaction);
                transactionTypeOld = em.merge(transactionTypeOld);
            }
            if (transactionTypeNew != null && !transactionTypeNew.equals(transactionTypeOld)) {
                transactionTypeNew.getTransactionCollection().add(transaction);
                transactionTypeNew = em.merge(transactionTypeNew);
            }
            for (TransactionItem transactionItemCollectionOldTransactionItem : transactionItemCollectionOld) {
                if (!transactionItemCollectionNew.contains(transactionItemCollectionOldTransactionItem)) {
                    transactionItemCollectionOldTransactionItem.setTransactionNo(null);
                    transactionItemCollectionOldTransactionItem = em.merge(transactionItemCollectionOldTransactionItem);
                }
            }
            for (TransactionItem transactionItemCollectionNewTransactionItem : transactionItemCollectionNew) {
                if (!transactionItemCollectionOld.contains(transactionItemCollectionNewTransactionItem)) {
                    Transaction oldTransactionNoOfTransactionItemCollectionNewTransactionItem = transactionItemCollectionNewTransactionItem.getTransactionNo();
                    transactionItemCollectionNewTransactionItem.setTransactionNo(transaction);
                    transactionItemCollectionNewTransactionItem = em.merge(transactionItemCollectionNewTransactionItem);
                    if (oldTransactionNoOfTransactionItemCollectionNewTransactionItem != null && !oldTransactionNoOfTransactionItemCollectionNewTransactionItem.equals(transaction)) {
                        oldTransactionNoOfTransactionItemCollectionNewTransactionItem.getTransactionItemCollection().remove(transactionItemCollectionNewTransactionItem);
                        oldTransactionNoOfTransactionItemCollectionNewTransactionItem = em.merge(oldTransactionNoOfTransactionItemCollectionNewTransactionItem);
                    }
                }
            }
            for (TransactionRelation transactionRelationCollectionNewTransactionRelation : transactionRelationCollectionNew) {
                if (!transactionRelationCollectionOld.contains(transactionRelationCollectionNewTransactionRelation)) {
                    Transaction oldTransactionOfTransactionRelationCollectionNewTransactionRelation = transactionRelationCollectionNewTransactionRelation.getTransaction();
                    transactionRelationCollectionNewTransactionRelation.setTransaction(transaction);
                    transactionRelationCollectionNewTransactionRelation = em.merge(transactionRelationCollectionNewTransactionRelation);
                    if (oldTransactionOfTransactionRelationCollectionNewTransactionRelation != null && !oldTransactionOfTransactionRelationCollectionNewTransactionRelation.equals(transaction)) {
                        oldTransactionOfTransactionRelationCollectionNewTransactionRelation.getTransactionRelationCollection().remove(transactionRelationCollectionNewTransactionRelation);
                        oldTransactionOfTransactionRelationCollectionNewTransactionRelation = em.merge(oldTransactionOfTransactionRelationCollectionNewTransactionRelation);
                    }
                }
            }
            for (TransactionRelation transactionRelationCollection1NewTransactionRelation : transactionRelationCollection1New) {
                if (!transactionRelationCollection1Old.contains(transactionRelationCollection1NewTransactionRelation)) {
                    Transaction oldTransaction1OfTransactionRelationCollection1NewTransactionRelation = transactionRelationCollection1NewTransactionRelation.getTransaction1();
                    transactionRelationCollection1NewTransactionRelation.setTransaction1(transaction);
                    transactionRelationCollection1NewTransactionRelation = em.merge(transactionRelationCollection1NewTransactionRelation);
                    if (oldTransaction1OfTransactionRelationCollection1NewTransactionRelation != null && !oldTransaction1OfTransactionRelationCollection1NewTransactionRelation.equals(transaction)) {
                        oldTransaction1OfTransactionRelationCollection1NewTransactionRelation.getTransactionRelationCollection1().remove(transactionRelationCollection1NewTransactionRelation);
                        oldTransaction1OfTransactionRelationCollection1NewTransactionRelation = em.merge(oldTransaction1OfTransactionRelationCollection1NewTransactionRelation);
                    }
                }
            }
            for (TransactionPayment transactionPaymentCollectionOldTransactionPayment : transactionPaymentCollectionOld) {
                if (!transactionPaymentCollectionNew.contains(transactionPaymentCollectionOldTransactionPayment)) {
                    transactionPaymentCollectionOldTransactionPayment.setTransactionNo(null);
                    transactionPaymentCollectionOldTransactionPayment = em.merge(transactionPaymentCollectionOldTransactionPayment);
                }
            }
            for (TransactionPayment transactionPaymentCollectionNewTransactionPayment : transactionPaymentCollectionNew) {
                if (!transactionPaymentCollectionOld.contains(transactionPaymentCollectionNewTransactionPayment)) {
                    Transaction oldTransactionNoOfTransactionPaymentCollectionNewTransactionPayment = transactionPaymentCollectionNewTransactionPayment.getTransactionNo();
                    transactionPaymentCollectionNewTransactionPayment.setTransactionNo(transaction);
                    transactionPaymentCollectionNewTransactionPayment = em.merge(transactionPaymentCollectionNewTransactionPayment);
                    if (oldTransactionNoOfTransactionPaymentCollectionNewTransactionPayment != null && !oldTransactionNoOfTransactionPaymentCollectionNewTransactionPayment.equals(transaction)) {
                        oldTransactionNoOfTransactionPaymentCollectionNewTransactionPayment.getTransactionPaymentCollection().remove(transactionPaymentCollectionNewTransactionPayment);
                        oldTransactionNoOfTransactionPaymentCollectionNewTransactionPayment = em.merge(oldTransactionNoOfTransactionPaymentCollectionNewTransactionPayment);
                    }
                }
            }
            for (TransactionDate transactionDateCollectionNewTransactionDate : transactionDateCollectionNew) {
                if (!transactionDateCollectionOld.contains(transactionDateCollectionNewTransactionDate)) {
                    Transaction oldTransactionOfTransactionDateCollectionNewTransactionDate = transactionDateCollectionNewTransactionDate.getTransaction();
                    transactionDateCollectionNewTransactionDate.setTransaction(transaction);
                    transactionDateCollectionNewTransactionDate = em.merge(transactionDateCollectionNewTransactionDate);
                    if (oldTransactionOfTransactionDateCollectionNewTransactionDate != null && !oldTransactionOfTransactionDateCollectionNewTransactionDate.equals(transaction)) {
                        oldTransactionOfTransactionDateCollectionNewTransactionDate.getTransactionDateCollection().remove(transactionDateCollectionNewTransactionDate);
                        oldTransactionOfTransactionDateCollectionNewTransactionDate = em.merge(oldTransactionOfTransactionDateCollectionNewTransactionDate);
                    }
                }
            }
            for (TransactionBank transactionBankCollectionNewTransactionBank : transactionBankCollectionNew) {
                if (!transactionBankCollectionOld.contains(transactionBankCollectionNewTransactionBank)) {
                    Transaction oldTransactionNoOfTransactionBankCollectionNewTransactionBank = transactionBankCollectionNewTransactionBank.getTransactionNo();
                    transactionBankCollectionNewTransactionBank.setTransactionNo(transaction);
                    transactionBankCollectionNewTransactionBank = em.merge(transactionBankCollectionNewTransactionBank);
                    if (oldTransactionNoOfTransactionBankCollectionNewTransactionBank != null && !oldTransactionNoOfTransactionBankCollectionNewTransactionBank.equals(transaction)) {
                        oldTransactionNoOfTransactionBankCollectionNewTransactionBank.getTransactionBankCollection().remove(transactionBankCollectionNewTransactionBank);
                        oldTransactionNoOfTransactionBankCollectionNewTransactionBank = em.merge(oldTransactionNoOfTransactionBankCollectionNewTransactionBank);
                    }
                }
            }
            for (TransactionPartner transactionPartnerCollectionNewTransactionPartner : transactionPartnerCollectionNew) {
                if (!transactionPartnerCollectionOld.contains(transactionPartnerCollectionNewTransactionPartner)) {
                    Transaction oldTransactionOfTransactionPartnerCollectionNewTransactionPartner = transactionPartnerCollectionNewTransactionPartner.getTransaction();
                    transactionPartnerCollectionNewTransactionPartner.setTransaction(transaction);
                    transactionPartnerCollectionNewTransactionPartner = em.merge(transactionPartnerCollectionNewTransactionPartner);
                    if (oldTransactionOfTransactionPartnerCollectionNewTransactionPartner != null && !oldTransactionOfTransactionPartnerCollectionNewTransactionPartner.equals(transaction)) {
                        oldTransactionOfTransactionPartnerCollectionNewTransactionPartner.getTransactionPartnerCollection().remove(transactionPartnerCollectionNewTransactionPartner);
                        oldTransactionOfTransactionPartnerCollectionNewTransactionPartner = em.merge(oldTransactionOfTransactionPartnerCollectionNewTransactionPartner);
                    }
                }
            }
            for (TransactionStatus transactionStatusCollectionNewTransactionStatus : transactionStatusCollectionNew) {
                if (!transactionStatusCollectionOld.contains(transactionStatusCollectionNewTransactionStatus)) {
                    Transaction oldTransactionOfTransactionStatusCollectionNewTransactionStatus = transactionStatusCollectionNewTransactionStatus.getTransaction();
                    transactionStatusCollectionNewTransactionStatus.setTransaction(transaction);
                    transactionStatusCollectionNewTransactionStatus = em.merge(transactionStatusCollectionNewTransactionStatus);
                    if (oldTransactionOfTransactionStatusCollectionNewTransactionStatus != null && !oldTransactionOfTransactionStatusCollectionNewTransactionStatus.equals(transaction)) {
                        oldTransactionOfTransactionStatusCollectionNewTransactionStatus.getTransactionStatusCollection().remove(transactionStatusCollectionNewTransactionStatus);
                        oldTransactionOfTransactionStatusCollectionNewTransactionStatus = em.merge(oldTransactionOfTransactionStatusCollectionNewTransactionStatus);
                    }
                }
            }
            for (TransactionAmount transactionAmountCollectionNewTransactionAmount : transactionAmountCollectionNew) {
                if (!transactionAmountCollectionOld.contains(transactionAmountCollectionNewTransactionAmount)) {
                    Transaction oldTransactionOfTransactionAmountCollectionNewTransactionAmount = transactionAmountCollectionNewTransactionAmount.getTransaction();
                    transactionAmountCollectionNewTransactionAmount.setTransaction(transaction);
                    transactionAmountCollectionNewTransactionAmount = em.merge(transactionAmountCollectionNewTransactionAmount);
                    if (oldTransactionOfTransactionAmountCollectionNewTransactionAmount != null && !oldTransactionOfTransactionAmountCollectionNewTransactionAmount.equals(transaction)) {
                        oldTransactionOfTransactionAmountCollectionNewTransactionAmount.getTransactionAmountCollection().remove(transactionAmountCollectionNewTransactionAmount);
                        oldTransactionOfTransactionAmountCollectionNewTransactionAmount = em.merge(oldTransactionOfTransactionAmountCollectionNewTransactionAmount);
                    }
                }
            }
            for (TransactionLocation transactionLocationCollectionNewTransactionLocation : transactionLocationCollectionNew) {
                if (!transactionLocationCollectionOld.contains(transactionLocationCollectionNewTransactionLocation)) {
                    Transaction oldTransactionOfTransactionLocationCollectionNewTransactionLocation = transactionLocationCollectionNewTransactionLocation.getTransaction();
                    transactionLocationCollectionNewTransactionLocation.setTransaction(transaction);
                    transactionLocationCollectionNewTransactionLocation = em.merge(transactionLocationCollectionNewTransactionLocation);
                    if (oldTransactionOfTransactionLocationCollectionNewTransactionLocation != null && !oldTransactionOfTransactionLocationCollectionNewTransactionLocation.equals(transaction)) {
                        oldTransactionOfTransactionLocationCollectionNewTransactionLocation.getTransactionLocationCollection().remove(transactionLocationCollectionNewTransactionLocation);
                        oldTransactionOfTransactionLocationCollectionNewTransactionLocation = em.merge(oldTransactionOfTransactionLocationCollectionNewTransactionLocation);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = transaction.getTransactionNo();
                if (findTransaction(id) == null) {
                    throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction transaction;
            try {
                transaction = em.getReference(Transaction.class, id);
                transaction.getTransactionNo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TransactionDocument transactionDocumentOrphanCheck = transaction.getTransactionDocument();
            if (transactionDocumentOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionDocument " + transactionDocumentOrphanCheck + " in its transactionDocument field has a non-nullable transaction field.");
            }
            Collection<TransactionRelation> transactionRelationCollectionOrphanCheck = transaction.getTransactionRelationCollection();
            for (TransactionRelation transactionRelationCollectionOrphanCheckTransactionRelation : transactionRelationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionRelation " + transactionRelationCollectionOrphanCheckTransactionRelation + " in its transactionRelationCollection field has a non-nullable transaction field.");
            }
            Collection<TransactionRelation> transactionRelationCollection1OrphanCheck = transaction.getTransactionRelationCollection1();
            for (TransactionRelation transactionRelationCollection1OrphanCheckTransactionRelation : transactionRelationCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionRelation " + transactionRelationCollection1OrphanCheckTransactionRelation + " in its transactionRelationCollection1 field has a non-nullable transaction1 field.");
            }
            Collection<TransactionDate> transactionDateCollectionOrphanCheck = transaction.getTransactionDateCollection();
            for (TransactionDate transactionDateCollectionOrphanCheckTransactionDate : transactionDateCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionDate " + transactionDateCollectionOrphanCheckTransactionDate + " in its transactionDateCollection field has a non-nullable transaction field.");
            }
            Collection<TransactionBank> transactionBankCollectionOrphanCheck = transaction.getTransactionBankCollection();
            for (TransactionBank transactionBankCollectionOrphanCheckTransactionBank : transactionBankCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionBank " + transactionBankCollectionOrphanCheckTransactionBank + " in its transactionBankCollection field has a non-nullable transactionNo field.");
            }
            Collection<TransactionPartner> transactionPartnerCollectionOrphanCheck = transaction.getTransactionPartnerCollection();
            for (TransactionPartner transactionPartnerCollectionOrphanCheckTransactionPartner : transactionPartnerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionPartner " + transactionPartnerCollectionOrphanCheckTransactionPartner + " in its transactionPartnerCollection field has a non-nullable transaction field.");
            }
            Collection<TransactionStatus> transactionStatusCollectionOrphanCheck = transaction.getTransactionStatusCollection();
            for (TransactionStatus transactionStatusCollectionOrphanCheckTransactionStatus : transactionStatusCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionStatus " + transactionStatusCollectionOrphanCheckTransactionStatus + " in its transactionStatusCollection field has a non-nullable transaction field.");
            }
            Collection<TransactionAmount> transactionAmountCollectionOrphanCheck = transaction.getTransactionAmountCollection();
            for (TransactionAmount transactionAmountCollectionOrphanCheckTransactionAmount : transactionAmountCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionAmount " + transactionAmountCollectionOrphanCheckTransactionAmount + " in its transactionAmountCollection field has a non-nullable transaction field.");
            }
            Collection<TransactionLocation> transactionLocationCollectionOrphanCheck = transaction.getTransactionLocationCollection();
            for (TransactionLocation transactionLocationCollectionOrphanCheckTransactionLocation : transactionLocationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaction (" + transaction + ") cannot be destroyed since the TransactionLocation " + transactionLocationCollectionOrphanCheckTransactionLocation + " in its transactionLocationCollection field has a non-nullable transaction field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ConfigStatusType status = transaction.getStatus();
            if (status != null) {
                status.getTransactionCollection().remove(transaction);
                status = em.merge(status);
            }
            ConfigTransactionType transactionType = transaction.getTransactionType();
            if (transactionType != null) {
                transactionType.getTransactionCollection().remove(transaction);
                transactionType = em.merge(transactionType);
            }
            Collection<TransactionItem> transactionItemCollection = transaction.getTransactionItemCollection();
            for (TransactionItem transactionItemCollectionTransactionItem : transactionItemCollection) {
                transactionItemCollectionTransactionItem.setTransactionNo(null);
                transactionItemCollectionTransactionItem = em.merge(transactionItemCollectionTransactionItem);
            }
            Collection<TransactionPayment> transactionPaymentCollection = transaction.getTransactionPaymentCollection();
            for (TransactionPayment transactionPaymentCollectionTransactionPayment : transactionPaymentCollection) {
                transactionPaymentCollectionTransactionPayment.setTransactionNo(null);
                transactionPaymentCollectionTransactionPayment = em.merge(transactionPaymentCollectionTransactionPayment);
            }
            em.remove(transaction);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaction> findTransactionEntities() {
        return findTransactionEntities(true, -1, -1);
    }

    public List<Transaction> findTransactionEntities(int maxResults, int firstResult) {
        return findTransactionEntities(false, maxResults, firstResult);
    }

    private List<Transaction> findTransactionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaction.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Transaction findTransaction(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaction.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaction> rt = cq.from(Transaction.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
