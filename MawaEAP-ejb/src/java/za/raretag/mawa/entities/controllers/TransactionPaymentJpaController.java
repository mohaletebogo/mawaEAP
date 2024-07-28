/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.ConfigPaymentType;
import za.raretag.mawa.entities.TransactionPayment;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionPaymentJpaController implements Serializable {

    public TransactionPaymentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionPayment transactionPayment) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Partner createdBy = transactionPayment.getCreatedBy();
            if (createdBy != null) {
                createdBy = em.getReference(createdBy.getClass(), createdBy.getPartnerNo());
                transactionPayment.setCreatedBy(createdBy);
            }
            Partner receivedBy = transactionPayment.getReceivedBy();
            if (receivedBy != null) {
                receivedBy = em.getReference(receivedBy.getClass(), receivedBy.getPartnerNo());
                transactionPayment.setReceivedBy(receivedBy);
            }
            Transaction transactionNo = transactionPayment.getTransactionNo();
            if (transactionNo != null) {
                transactionNo = em.getReference(transactionNo.getClass(), transactionNo.getTransactionNo());
                transactionPayment.setTransactionNo(transactionNo);
            }
            ConfigPaymentType paymentType = transactionPayment.getPaymentType();
            if (paymentType != null) {
                paymentType = em.getReference(paymentType.getClass(), paymentType.getId());
                transactionPayment.setPaymentType(paymentType);
            }
            em.persist(transactionPayment);
            if (createdBy != null) {
                createdBy.getTransactionPaymentCollection().add(transactionPayment);
                createdBy = em.merge(createdBy);
            }
            if (receivedBy != null) {
                receivedBy.getTransactionPaymentCollection().add(transactionPayment);
                receivedBy = em.merge(receivedBy);
            }
            if (transactionNo != null) {
                transactionNo.getTransactionPaymentCollection().add(transactionPayment);
                transactionNo = em.merge(transactionNo);
            }
            if (paymentType != null) {
                paymentType.getTransactionPaymentCollection().add(transactionPayment);
                paymentType = em.merge(paymentType);
            }
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

    public void edit(TransactionPayment transactionPayment) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionPayment persistentTransactionPayment = em.find(TransactionPayment.class, transactionPayment.getPaymentNo());
            Partner createdByOld = persistentTransactionPayment.getCreatedBy();
            Partner createdByNew = transactionPayment.getCreatedBy();
            Partner receivedByOld = persistentTransactionPayment.getReceivedBy();
            Partner receivedByNew = transactionPayment.getReceivedBy();
            Transaction transactionNoOld = persistentTransactionPayment.getTransactionNo();
            Transaction transactionNoNew = transactionPayment.getTransactionNo();
            ConfigPaymentType paymentTypeOld = persistentTransactionPayment.getPaymentType();
            ConfigPaymentType paymentTypeNew = transactionPayment.getPaymentType();
            if (createdByNew != null) {
                createdByNew = em.getReference(createdByNew.getClass(), createdByNew.getPartnerNo());
                transactionPayment.setCreatedBy(createdByNew);
            }
            if (receivedByNew != null) {
                receivedByNew = em.getReference(receivedByNew.getClass(), receivedByNew.getPartnerNo());
                transactionPayment.setReceivedBy(receivedByNew);
            }
            if (transactionNoNew != null) {
                transactionNoNew = em.getReference(transactionNoNew.getClass(), transactionNoNew.getTransactionNo());
                transactionPayment.setTransactionNo(transactionNoNew);
            }
            if (paymentTypeNew != null) {
                paymentTypeNew = em.getReference(paymentTypeNew.getClass(), paymentTypeNew.getId());
                transactionPayment.setPaymentType(paymentTypeNew);
            }
            transactionPayment = em.merge(transactionPayment);
            if (createdByOld != null && !createdByOld.equals(createdByNew)) {
                createdByOld.getTransactionPaymentCollection().remove(transactionPayment);
                createdByOld = em.merge(createdByOld);
            }
            if (createdByNew != null && !createdByNew.equals(createdByOld)) {
                createdByNew.getTransactionPaymentCollection().add(transactionPayment);
                createdByNew = em.merge(createdByNew);
            }
            if (receivedByOld != null && !receivedByOld.equals(receivedByNew)) {
                receivedByOld.getTransactionPaymentCollection().remove(transactionPayment);
                receivedByOld = em.merge(receivedByOld);
            }
            if (receivedByNew != null && !receivedByNew.equals(receivedByOld)) {
                receivedByNew.getTransactionPaymentCollection().add(transactionPayment);
                receivedByNew = em.merge(receivedByNew);
            }
            if (transactionNoOld != null && !transactionNoOld.equals(transactionNoNew)) {
                transactionNoOld.getTransactionPaymentCollection().remove(transactionPayment);
                transactionNoOld = em.merge(transactionNoOld);
            }
            if (transactionNoNew != null && !transactionNoNew.equals(transactionNoOld)) {
                transactionNoNew.getTransactionPaymentCollection().add(transactionPayment);
                transactionNoNew = em.merge(transactionNoNew);
            }
            if (paymentTypeOld != null && !paymentTypeOld.equals(paymentTypeNew)) {
                paymentTypeOld.getTransactionPaymentCollection().remove(transactionPayment);
                paymentTypeOld = em.merge(paymentTypeOld);
            }
            if (paymentTypeNew != null && !paymentTypeNew.equals(paymentTypeOld)) {
                paymentTypeNew.getTransactionPaymentCollection().add(transactionPayment);
                paymentTypeNew = em.merge(paymentTypeNew);
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
                Integer id = transactionPayment.getPaymentNo();
                if (findTransactionPayment(id) == null) {
                    throw new NonexistentEntityException("The transactionPayment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionPayment transactionPayment;
            try {
                transactionPayment = em.getReference(TransactionPayment.class, id);
                transactionPayment.getPaymentNo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionPayment with id " + id + " no longer exists.", enfe);
            }
            Partner createdBy = transactionPayment.getCreatedBy();
            if (createdBy != null) {
                createdBy.getTransactionPaymentCollection().remove(transactionPayment);
                createdBy = em.merge(createdBy);
            }
            Partner receivedBy = transactionPayment.getReceivedBy();
            if (receivedBy != null) {
                receivedBy.getTransactionPaymentCollection().remove(transactionPayment);
                receivedBy = em.merge(receivedBy);
            }
            Transaction transactionNo = transactionPayment.getTransactionNo();
            if (transactionNo != null) {
                transactionNo.getTransactionPaymentCollection().remove(transactionPayment);
                transactionNo = em.merge(transactionNo);
            }
            ConfigPaymentType paymentType = transactionPayment.getPaymentType();
            if (paymentType != null) {
                paymentType.getTransactionPaymentCollection().remove(transactionPayment);
                paymentType = em.merge(paymentType);
            }
            em.remove(transactionPayment);
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

    public List<TransactionPayment> findTransactionPaymentEntities() {
        return findTransactionPaymentEntities(true, -1, -1);
    }

    public List<TransactionPayment> findTransactionPaymentEntities(int maxResults, int firstResult) {
        return findTransactionPaymentEntities(false, maxResults, firstResult);
    }

    private List<TransactionPayment> findTransactionPaymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionPayment.class));
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

    public TransactionPayment findTransactionPayment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionPayment.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionPaymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionPayment> rt = cq.from(TransactionPayment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
