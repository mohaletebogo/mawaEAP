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
import za.raretag.mawa.entities.ConfigStatusType;
import za.raretag.mawa.entities.TransactionStatus;
import za.raretag.mawa.entities.TransactionStatusPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionStatusJpaController implements Serializable {

    public TransactionStatusJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionStatus transactionStatus) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transactionStatus.getTransactionStatusPK() == null) {
            transactionStatus.setTransactionStatusPK(new TransactionStatusPK());
        }
        transactionStatus.getTransactionStatusPK().setStatus(transactionStatus.getConfigStatusType().getId());
        transactionStatus.getTransactionStatusPK().setTransactionNo(transactionStatus.getTransaction().getTransactionNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction transaction = transactionStatus.getTransaction();
            if (transaction != null) {
                transaction = em.getReference(transaction.getClass(), transaction.getTransactionNo());
                transactionStatus.setTransaction(transaction);
            }
            ConfigStatusType configStatusType = transactionStatus.getConfigStatusType();
            if (configStatusType != null) {
                configStatusType = em.getReference(configStatusType.getClass(), configStatusType.getId());
                transactionStatus.setConfigStatusType(configStatusType);
            }
            em.persist(transactionStatus);
            if (transaction != null) {
                transaction.getTransactionStatusCollection().add(transactionStatus);
                transaction = em.merge(transaction);
            }
            if (configStatusType != null) {
                configStatusType.getTransactionStatusCollection().add(transactionStatus);
                configStatusType = em.merge(configStatusType);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransactionStatus(transactionStatus.getTransactionStatusPK()) != null) {
                throw new PreexistingEntityException("TransactionStatus " + transactionStatus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionStatus transactionStatus) throws NonexistentEntityException, RollbackFailureException, Exception {
        transactionStatus.getTransactionStatusPK().setStatus(transactionStatus.getConfigStatusType().getId());
        transactionStatus.getTransactionStatusPK().setTransactionNo(transactionStatus.getTransaction().getTransactionNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionStatus persistentTransactionStatus = em.find(TransactionStatus.class, transactionStatus.getTransactionStatusPK());
            Transaction transactionOld = persistentTransactionStatus.getTransaction();
            Transaction transactionNew = transactionStatus.getTransaction();
            ConfigStatusType configStatusTypeOld = persistentTransactionStatus.getConfigStatusType();
            ConfigStatusType configStatusTypeNew = transactionStatus.getConfigStatusType();
            if (transactionNew != null) {
                transactionNew = em.getReference(transactionNew.getClass(), transactionNew.getTransactionNo());
                transactionStatus.setTransaction(transactionNew);
            }
            if (configStatusTypeNew != null) {
                configStatusTypeNew = em.getReference(configStatusTypeNew.getClass(), configStatusTypeNew.getId());
                transactionStatus.setConfigStatusType(configStatusTypeNew);
            }
            transactionStatus = em.merge(transactionStatus);
            if (transactionOld != null && !transactionOld.equals(transactionNew)) {
                transactionOld.getTransactionStatusCollection().remove(transactionStatus);
                transactionOld = em.merge(transactionOld);
            }
            if (transactionNew != null && !transactionNew.equals(transactionOld)) {
                transactionNew.getTransactionStatusCollection().add(transactionStatus);
                transactionNew = em.merge(transactionNew);
            }
            if (configStatusTypeOld != null && !configStatusTypeOld.equals(configStatusTypeNew)) {
                configStatusTypeOld.getTransactionStatusCollection().remove(transactionStatus);
                configStatusTypeOld = em.merge(configStatusTypeOld);
            }
            if (configStatusTypeNew != null && !configStatusTypeNew.equals(configStatusTypeOld)) {
                configStatusTypeNew.getTransactionStatusCollection().add(transactionStatus);
                configStatusTypeNew = em.merge(configStatusTypeNew);
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
                TransactionStatusPK id = transactionStatus.getTransactionStatusPK();
                if (findTransactionStatus(id) == null) {
                    throw new NonexistentEntityException("The transactionStatus with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TransactionStatusPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionStatus transactionStatus;
            try {
                transactionStatus = em.getReference(TransactionStatus.class, id);
                transactionStatus.getTransactionStatusPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionStatus with id " + id + " no longer exists.", enfe);
            }
            Transaction transaction = transactionStatus.getTransaction();
            if (transaction != null) {
                transaction.getTransactionStatusCollection().remove(transactionStatus);
                transaction = em.merge(transaction);
            }
            ConfigStatusType configStatusType = transactionStatus.getConfigStatusType();
            if (configStatusType != null) {
                configStatusType.getTransactionStatusCollection().remove(transactionStatus);
                configStatusType = em.merge(configStatusType);
            }
            em.remove(transactionStatus);
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

    public List<TransactionStatus> findTransactionStatusEntities() {
        return findTransactionStatusEntities(true, -1, -1);
    }

    public List<TransactionStatus> findTransactionStatusEntities(int maxResults, int firstResult) {
        return findTransactionStatusEntities(false, maxResults, firstResult);
    }

    private List<TransactionStatus> findTransactionStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionStatus.class));
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

    public TransactionStatus findTransactionStatus(TransactionStatusPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionStatus> rt = cq.from(TransactionStatus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
