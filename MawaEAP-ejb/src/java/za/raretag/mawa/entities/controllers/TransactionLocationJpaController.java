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
import za.raretag.mawa.entities.ConfigSalesArea;
import za.raretag.mawa.entities.ConfigLocationType;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionLocation;
import za.raretag.mawa.entities.TransactionLocationPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionLocationJpaController implements Serializable {

    public TransactionLocationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionLocation transactionLocation) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transactionLocation.getTransactionLocationPK() == null) {
            transactionLocation.setTransactionLocationPK(new TransactionLocationPK());
        }
        transactionLocation.getTransactionLocationPK().setTransactionNo(transactionLocation.getTransaction().getTransactionNo());
        transactionLocation.getTransactionLocationPK().setLocationId(transactionLocation.getConfigSalesArea().getId());
        transactionLocation.getTransactionLocationPK().setLocationType(transactionLocation.getConfigLocationType().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigSalesArea configSalesArea = transactionLocation.getConfigSalesArea();
            if (configSalesArea != null) {
                configSalesArea = em.getReference(configSalesArea.getClass(), configSalesArea.getId());
                transactionLocation.setConfigSalesArea(configSalesArea);
            }
            ConfigLocationType configLocationType = transactionLocation.getConfigLocationType();
            if (configLocationType != null) {
                configLocationType = em.getReference(configLocationType.getClass(), configLocationType.getId());
                transactionLocation.setConfigLocationType(configLocationType);
            }
            Transaction transaction = transactionLocation.getTransaction();
            if (transaction != null) {
                transaction = em.getReference(transaction.getClass(), transaction.getTransactionNo());
                transactionLocation.setTransaction(transaction);
            }
            em.persist(transactionLocation);
            if (configSalesArea != null) {
                configSalesArea.getTransactionLocationCollection().add(transactionLocation);
                configSalesArea = em.merge(configSalesArea);
            }
            if (configLocationType != null) {
                configLocationType.getTransactionLocationCollection().add(transactionLocation);
                configLocationType = em.merge(configLocationType);
            }
            if (transaction != null) {
                transaction.getTransactionLocationCollection().add(transactionLocation);
                transaction = em.merge(transaction);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransactionLocation(transactionLocation.getTransactionLocationPK()) != null) {
                throw new PreexistingEntityException("TransactionLocation " + transactionLocation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionLocation transactionLocation) throws NonexistentEntityException, RollbackFailureException, Exception {
        transactionLocation.getTransactionLocationPK().setTransactionNo(transactionLocation.getTransaction().getTransactionNo());
        transactionLocation.getTransactionLocationPK().setLocationId(transactionLocation.getConfigSalesArea().getId());
        transactionLocation.getTransactionLocationPK().setLocationType(transactionLocation.getConfigLocationType().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionLocation persistentTransactionLocation = em.find(TransactionLocation.class, transactionLocation.getTransactionLocationPK());
            ConfigSalesArea configSalesAreaOld = persistentTransactionLocation.getConfigSalesArea();
            ConfigSalesArea configSalesAreaNew = transactionLocation.getConfigSalesArea();
            ConfigLocationType configLocationTypeOld = persistentTransactionLocation.getConfigLocationType();
            ConfigLocationType configLocationTypeNew = transactionLocation.getConfigLocationType();
            Transaction transactionOld = persistentTransactionLocation.getTransaction();
            Transaction transactionNew = transactionLocation.getTransaction();
            if (configSalesAreaNew != null) {
                configSalesAreaNew = em.getReference(configSalesAreaNew.getClass(), configSalesAreaNew.getId());
                transactionLocation.setConfigSalesArea(configSalesAreaNew);
            }
            if (configLocationTypeNew != null) {
                configLocationTypeNew = em.getReference(configLocationTypeNew.getClass(), configLocationTypeNew.getId());
                transactionLocation.setConfigLocationType(configLocationTypeNew);
            }
            if (transactionNew != null) {
                transactionNew = em.getReference(transactionNew.getClass(), transactionNew.getTransactionNo());
                transactionLocation.setTransaction(transactionNew);
            }
            transactionLocation = em.merge(transactionLocation);
            if (configSalesAreaOld != null && !configSalesAreaOld.equals(configSalesAreaNew)) {
                configSalesAreaOld.getTransactionLocationCollection().remove(transactionLocation);
                configSalesAreaOld = em.merge(configSalesAreaOld);
            }
            if (configSalesAreaNew != null && !configSalesAreaNew.equals(configSalesAreaOld)) {
                configSalesAreaNew.getTransactionLocationCollection().add(transactionLocation);
                configSalesAreaNew = em.merge(configSalesAreaNew);
            }
            if (configLocationTypeOld != null && !configLocationTypeOld.equals(configLocationTypeNew)) {
                configLocationTypeOld.getTransactionLocationCollection().remove(transactionLocation);
                configLocationTypeOld = em.merge(configLocationTypeOld);
            }
            if (configLocationTypeNew != null && !configLocationTypeNew.equals(configLocationTypeOld)) {
                configLocationTypeNew.getTransactionLocationCollection().add(transactionLocation);
                configLocationTypeNew = em.merge(configLocationTypeNew);
            }
            if (transactionOld != null && !transactionOld.equals(transactionNew)) {
                transactionOld.getTransactionLocationCollection().remove(transactionLocation);
                transactionOld = em.merge(transactionOld);
            }
            if (transactionNew != null && !transactionNew.equals(transactionOld)) {
                transactionNew.getTransactionLocationCollection().add(transactionLocation);
                transactionNew = em.merge(transactionNew);
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
                TransactionLocationPK id = transactionLocation.getTransactionLocationPK();
                if (findTransactionLocation(id) == null) {
                    throw new NonexistentEntityException("The transactionLocation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TransactionLocationPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionLocation transactionLocation;
            try {
                transactionLocation = em.getReference(TransactionLocation.class, id);
                transactionLocation.getTransactionLocationPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionLocation with id " + id + " no longer exists.", enfe);
            }
            ConfigSalesArea configSalesArea = transactionLocation.getConfigSalesArea();
            if (configSalesArea != null) {
                configSalesArea.getTransactionLocationCollection().remove(transactionLocation);
                configSalesArea = em.merge(configSalesArea);
            }
            ConfigLocationType configLocationType = transactionLocation.getConfigLocationType();
            if (configLocationType != null) {
                configLocationType.getTransactionLocationCollection().remove(transactionLocation);
                configLocationType = em.merge(configLocationType);
            }
            Transaction transaction = transactionLocation.getTransaction();
            if (transaction != null) {
                transaction.getTransactionLocationCollection().remove(transactionLocation);
                transaction = em.merge(transaction);
            }
            em.remove(transactionLocation);
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

    public List<TransactionLocation> findTransactionLocationEntities() {
        return findTransactionLocationEntities(true, -1, -1);
    }

    public List<TransactionLocation> findTransactionLocationEntities(int maxResults, int firstResult) {
        return findTransactionLocationEntities(false, maxResults, firstResult);
    }

    private List<TransactionLocation> findTransactionLocationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionLocation.class));
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

    public TransactionLocation findTransactionLocation(TransactionLocationPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionLocation.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionLocationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionLocation> rt = cq.from(TransactionLocation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
