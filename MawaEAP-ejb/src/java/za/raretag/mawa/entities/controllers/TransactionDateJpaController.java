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
import za.raretag.mawa.entities.ConfigDateType;
import za.raretag.mawa.entities.TransactionDate;
import za.raretag.mawa.entities.TransactionDatePK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionDateJpaController implements Serializable {

    public TransactionDateJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionDate transactionDate) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transactionDate.getTransactionDatePK() == null) {
            transactionDate.setTransactionDatePK(new TransactionDatePK());
        }
        transactionDate.getTransactionDatePK().setDateType(transactionDate.getConfigDateType().getId());
        transactionDate.getTransactionDatePK().setTransactionNo(transactionDate.getTransaction().getTransactionNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction transaction = transactionDate.getTransaction();
            if (transaction != null) {
                transaction = em.getReference(transaction.getClass(), transaction.getTransactionNo());
                transactionDate.setTransaction(transaction);
            }
            ConfigDateType configDateType = transactionDate.getConfigDateType();
            if (configDateType != null) {
                configDateType = em.getReference(configDateType.getClass(), configDateType.getId());
                transactionDate.setConfigDateType(configDateType);
            }
            em.persist(transactionDate);
            if (transaction != null) {
                transaction.getTransactionDateCollection().add(transactionDate);
                transaction = em.merge(transaction);
            }
            if (configDateType != null) {
                configDateType.getTransactionDateCollection().add(transactionDate);
                configDateType = em.merge(configDateType);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransactionDate(transactionDate.getTransactionDatePK()) != null) {
                throw new PreexistingEntityException("TransactionDate " + transactionDate + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionDate transactionDate) throws NonexistentEntityException, RollbackFailureException, Exception {
        transactionDate.getTransactionDatePK().setDateType(transactionDate.getConfigDateType().getId());
        transactionDate.getTransactionDatePK().setTransactionNo(transactionDate.getTransaction().getTransactionNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionDate persistentTransactionDate = em.find(TransactionDate.class, transactionDate.getTransactionDatePK());
            Transaction transactionOld = persistentTransactionDate.getTransaction();
            Transaction transactionNew = transactionDate.getTransaction();
            ConfigDateType configDateTypeOld = persistentTransactionDate.getConfigDateType();
            ConfigDateType configDateTypeNew = transactionDate.getConfigDateType();
            if (transactionNew != null) {
                transactionNew = em.getReference(transactionNew.getClass(), transactionNew.getTransactionNo());
                transactionDate.setTransaction(transactionNew);
            }
            if (configDateTypeNew != null) {
                configDateTypeNew = em.getReference(configDateTypeNew.getClass(), configDateTypeNew.getId());
                transactionDate.setConfigDateType(configDateTypeNew);
            }
            transactionDate = em.merge(transactionDate);
            if (transactionOld != null && !transactionOld.equals(transactionNew)) {
                transactionOld.getTransactionDateCollection().remove(transactionDate);
                transactionOld = em.merge(transactionOld);
            }
            if (transactionNew != null && !transactionNew.equals(transactionOld)) {
                transactionNew.getTransactionDateCollection().add(transactionDate);
                transactionNew = em.merge(transactionNew);
            }
            if (configDateTypeOld != null && !configDateTypeOld.equals(configDateTypeNew)) {
                configDateTypeOld.getTransactionDateCollection().remove(transactionDate);
                configDateTypeOld = em.merge(configDateTypeOld);
            }
            if (configDateTypeNew != null && !configDateTypeNew.equals(configDateTypeOld)) {
                configDateTypeNew.getTransactionDateCollection().add(transactionDate);
                configDateTypeNew = em.merge(configDateTypeNew);
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
                TransactionDatePK id = transactionDate.getTransactionDatePK();
                if (findTransactionDate(id) == null) {
                    throw new NonexistentEntityException("The transactionDate with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TransactionDatePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionDate transactionDate;
            try {
                transactionDate = em.getReference(TransactionDate.class, id);
                transactionDate.getTransactionDatePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionDate with id " + id + " no longer exists.", enfe);
            }
            Transaction transaction = transactionDate.getTransaction();
            if (transaction != null) {
                transaction.getTransactionDateCollection().remove(transactionDate);
                transaction = em.merge(transaction);
            }
            ConfigDateType configDateType = transactionDate.getConfigDateType();
            if (configDateType != null) {
                configDateType.getTransactionDateCollection().remove(transactionDate);
                configDateType = em.merge(configDateType);
            }
            em.remove(transactionDate);
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

    public List<TransactionDate> findTransactionDateEntities() {
        return findTransactionDateEntities(true, -1, -1);
    }

    public List<TransactionDate> findTransactionDateEntities(int maxResults, int firstResult) {
        return findTransactionDateEntities(false, maxResults, firstResult);
    }

    private List<TransactionDate> findTransactionDateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionDate.class));
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

    public TransactionDate findTransactionDate(TransactionDatePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionDate.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionDateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionDate> rt = cq.from(TransactionDate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
