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
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.ConfigAmountType;
import za.raretag.mawa.entities.TransactionAmount;
import za.raretag.mawa.entities.TransactionAmountPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionAmountJpaController implements Serializable {

    public TransactionAmountJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionAmount transactionAmount) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transactionAmount.getTransactionAmountPK() == null) {
            transactionAmount.setTransactionAmountPK(new TransactionAmountPK());
        }
        transactionAmount.getTransactionAmountPK().setAmountType(transactionAmount.getConfigAmountType().getId());
        transactionAmount.getTransactionAmountPK().setTransactionNo(transactionAmount.getTransaction().getTransactionNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction transaction = transactionAmount.getTransaction();
            if (transaction != null) {
                transaction = em.getReference(transaction.getClass(), transaction.getTransactionNo());
                transactionAmount.setTransaction(transaction);
            }
            ConfigAmountType configAmountType = transactionAmount.getConfigAmountType();
            if (configAmountType != null) {
                configAmountType = em.getReference(configAmountType.getClass(), configAmountType.getId());
                transactionAmount.setConfigAmountType(configAmountType);
            }
            em.persist(transactionAmount);
            if (transaction != null) {
                transaction.getTransactionAmountCollection().add(transactionAmount);
                transaction = em.merge(transaction);
            }
            if (configAmountType != null) {
                configAmountType.getTransactionAmountCollection().add(transactionAmount);
                configAmountType = em.merge(configAmountType);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransactionAmount(transactionAmount.getTransactionAmountPK()) != null) {
                throw new PreexistingEntityException("TransactionAmount " + transactionAmount + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionAmount transactionAmount) throws NonexistentEntityException, RollbackFailureException, Exception {
        transactionAmount.getTransactionAmountPK().setAmountType(transactionAmount.getConfigAmountType().getId());
        transactionAmount.getTransactionAmountPK().setTransactionNo(transactionAmount.getTransaction().getTransactionNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionAmount persistentTransactionAmount = em.find(TransactionAmount.class, transactionAmount.getTransactionAmountPK());
            Transaction transactionOld = persistentTransactionAmount.getTransaction();
            Transaction transactionNew = transactionAmount.getTransaction();
            ConfigAmountType configAmountTypeOld = persistentTransactionAmount.getConfigAmountType();
            ConfigAmountType configAmountTypeNew = transactionAmount.getConfigAmountType();
            if (transactionNew != null) {
                transactionNew = em.getReference(transactionNew.getClass(), transactionNew.getTransactionNo());
                transactionAmount.setTransaction(transactionNew);
            }
            if (configAmountTypeNew != null) {
                configAmountTypeNew = em.getReference(configAmountTypeNew.getClass(), configAmountTypeNew.getId());
                transactionAmount.setConfigAmountType(configAmountTypeNew);
            }
            transactionAmount = em.merge(transactionAmount);
            if (transactionOld != null && !transactionOld.equals(transactionNew)) {
                transactionOld.getTransactionAmountCollection().remove(transactionAmount);
                transactionOld = em.merge(transactionOld);
            }
            if (transactionNew != null && !transactionNew.equals(transactionOld)) {
                transactionNew.getTransactionAmountCollection().add(transactionAmount);
                transactionNew = em.merge(transactionNew);
            }
            if (configAmountTypeOld != null && !configAmountTypeOld.equals(configAmountTypeNew)) {
                configAmountTypeOld.getTransactionAmountCollection().remove(transactionAmount);
                configAmountTypeOld = em.merge(configAmountTypeOld);
            }
            if (configAmountTypeNew != null && !configAmountTypeNew.equals(configAmountTypeOld)) {
                configAmountTypeNew.getTransactionAmountCollection().add(transactionAmount);
                configAmountTypeNew = em.merge(configAmountTypeNew);
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
                TransactionAmountPK id = transactionAmount.getTransactionAmountPK();
                if (findTransactionAmount(id) == null) {
                    throw new NonexistentEntityException("The transactionAmount with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TransactionAmountPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionAmount transactionAmount;
            try {
                transactionAmount = em.getReference(TransactionAmount.class, id);
                transactionAmount.getTransactionAmountPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionAmount with id " + id + " no longer exists.", enfe);
            }
            Transaction transaction = transactionAmount.getTransaction();
            if (transaction != null) {
                transaction.getTransactionAmountCollection().remove(transactionAmount);
                transaction = em.merge(transaction);
            }
            ConfigAmountType configAmountType = transactionAmount.getConfigAmountType();
            if (configAmountType != null) {
                configAmountType.getTransactionAmountCollection().remove(transactionAmount);
                configAmountType = em.merge(configAmountType);
            }
            em.remove(transactionAmount);
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

    public List<TransactionAmount> findTransactionAmountEntities() {
        return findTransactionAmountEntities(true, -1, -1);
    }

    public List<TransactionAmount> findTransactionAmountEntities(int maxResults, int firstResult) {
        return findTransactionAmountEntities(false, maxResults, firstResult);
    }

    private List<TransactionAmount> findTransactionAmountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionAmount.class));
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

    public TransactionAmount findTransactionAmount(TransactionAmountPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionAmount.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionAmountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionAmount> rt = cq.from(TransactionAmount.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
