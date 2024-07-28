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
import za.raretag.mawa.entities.TransactionStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigStatusType;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigStatusTypeJpaController implements Serializable {

    public ConfigStatusTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigStatusType configStatusType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configStatusType.getTransactionStatusCollection() == null) {
            configStatusType.setTransactionStatusCollection(new ArrayList<TransactionStatus>());
        }
        if (configStatusType.getTransactionCollection() == null) {
            configStatusType.setTransactionCollection(new ArrayList<Transaction>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionStatus> attachedTransactionStatusCollection = new ArrayList<TransactionStatus>();
            for (TransactionStatus transactionStatusCollectionTransactionStatusToAttach : configStatusType.getTransactionStatusCollection()) {
                transactionStatusCollectionTransactionStatusToAttach = em.getReference(transactionStatusCollectionTransactionStatusToAttach.getClass(), transactionStatusCollectionTransactionStatusToAttach.getTransactionStatusPK());
                attachedTransactionStatusCollection.add(transactionStatusCollectionTransactionStatusToAttach);
            }
            configStatusType.setTransactionStatusCollection(attachedTransactionStatusCollection);
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : configStatusType.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getTransactionNo());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            configStatusType.setTransactionCollection(attachedTransactionCollection);
            em.persist(configStatusType);
            for (TransactionStatus transactionStatusCollectionTransactionStatus : configStatusType.getTransactionStatusCollection()) {
                ConfigStatusType oldConfigStatusTypeOfTransactionStatusCollectionTransactionStatus = transactionStatusCollectionTransactionStatus.getConfigStatusType();
                transactionStatusCollectionTransactionStatus.setConfigStatusType(configStatusType);
                transactionStatusCollectionTransactionStatus = em.merge(transactionStatusCollectionTransactionStatus);
                if (oldConfigStatusTypeOfTransactionStatusCollectionTransactionStatus != null) {
                    oldConfigStatusTypeOfTransactionStatusCollectionTransactionStatus.getTransactionStatusCollection().remove(transactionStatusCollectionTransactionStatus);
                    oldConfigStatusTypeOfTransactionStatusCollectionTransactionStatus = em.merge(oldConfigStatusTypeOfTransactionStatusCollectionTransactionStatus);
                }
            }
            for (Transaction transactionCollectionTransaction : configStatusType.getTransactionCollection()) {
                ConfigStatusType oldStatusOfTransactionCollectionTransaction = transactionCollectionTransaction.getStatus();
                transactionCollectionTransaction.setStatus(configStatusType);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldStatusOfTransactionCollectionTransaction != null) {
                    oldStatusOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldStatusOfTransactionCollectionTransaction = em.merge(oldStatusOfTransactionCollectionTransaction);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigStatusType(configStatusType.getId()) != null) {
                throw new PreexistingEntityException("ConfigStatusType " + configStatusType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigStatusType configStatusType) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigStatusType persistentConfigStatusType = em.find(ConfigStatusType.class, configStatusType.getId());
            Collection<TransactionStatus> transactionStatusCollectionOld = persistentConfigStatusType.getTransactionStatusCollection();
            Collection<TransactionStatus> transactionStatusCollectionNew = configStatusType.getTransactionStatusCollection();
            Collection<Transaction> transactionCollectionOld = persistentConfigStatusType.getTransactionCollection();
            Collection<Transaction> transactionCollectionNew = configStatusType.getTransactionCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionStatus transactionStatusCollectionOldTransactionStatus : transactionStatusCollectionOld) {
                if (!transactionStatusCollectionNew.contains(transactionStatusCollectionOldTransactionStatus)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionStatus " + transactionStatusCollectionOldTransactionStatus + " since its configStatusType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionStatus> attachedTransactionStatusCollectionNew = new ArrayList<TransactionStatus>();
            for (TransactionStatus transactionStatusCollectionNewTransactionStatusToAttach : transactionStatusCollectionNew) {
                transactionStatusCollectionNewTransactionStatusToAttach = em.getReference(transactionStatusCollectionNewTransactionStatusToAttach.getClass(), transactionStatusCollectionNewTransactionStatusToAttach.getTransactionStatusPK());
                attachedTransactionStatusCollectionNew.add(transactionStatusCollectionNewTransactionStatusToAttach);
            }
            transactionStatusCollectionNew = attachedTransactionStatusCollectionNew;
            configStatusType.setTransactionStatusCollection(transactionStatusCollectionNew);
            Collection<Transaction> attachedTransactionCollectionNew = new ArrayList<Transaction>();
            for (Transaction transactionCollectionNewTransactionToAttach : transactionCollectionNew) {
                transactionCollectionNewTransactionToAttach = em.getReference(transactionCollectionNewTransactionToAttach.getClass(), transactionCollectionNewTransactionToAttach.getTransactionNo());
                attachedTransactionCollectionNew.add(transactionCollectionNewTransactionToAttach);
            }
            transactionCollectionNew = attachedTransactionCollectionNew;
            configStatusType.setTransactionCollection(transactionCollectionNew);
            configStatusType = em.merge(configStatusType);
            for (TransactionStatus transactionStatusCollectionNewTransactionStatus : transactionStatusCollectionNew) {
                if (!transactionStatusCollectionOld.contains(transactionStatusCollectionNewTransactionStatus)) {
                    ConfigStatusType oldConfigStatusTypeOfTransactionStatusCollectionNewTransactionStatus = transactionStatusCollectionNewTransactionStatus.getConfigStatusType();
                    transactionStatusCollectionNewTransactionStatus.setConfigStatusType(configStatusType);
                    transactionStatusCollectionNewTransactionStatus = em.merge(transactionStatusCollectionNewTransactionStatus);
                    if (oldConfigStatusTypeOfTransactionStatusCollectionNewTransactionStatus != null && !oldConfigStatusTypeOfTransactionStatusCollectionNewTransactionStatus.equals(configStatusType)) {
                        oldConfigStatusTypeOfTransactionStatusCollectionNewTransactionStatus.getTransactionStatusCollection().remove(transactionStatusCollectionNewTransactionStatus);
                        oldConfigStatusTypeOfTransactionStatusCollectionNewTransactionStatus = em.merge(oldConfigStatusTypeOfTransactionStatusCollectionNewTransactionStatus);
                    }
                }
            }
            for (Transaction transactionCollectionOldTransaction : transactionCollectionOld) {
                if (!transactionCollectionNew.contains(transactionCollectionOldTransaction)) {
                    transactionCollectionOldTransaction.setStatus(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    ConfigStatusType oldStatusOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getStatus();
                    transactionCollectionNewTransaction.setStatus(configStatusType);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldStatusOfTransactionCollectionNewTransaction != null && !oldStatusOfTransactionCollectionNewTransaction.equals(configStatusType)) {
                        oldStatusOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldStatusOfTransactionCollectionNewTransaction = em.merge(oldStatusOfTransactionCollectionNewTransaction);
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
                String id = configStatusType.getId();
                if (findConfigStatusType(id) == null) {
                    throw new NonexistentEntityException("The configStatusType with id " + id + " no longer exists.");
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
            ConfigStatusType configStatusType;
            try {
                configStatusType = em.getReference(ConfigStatusType.class, id);
                configStatusType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configStatusType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionStatus> transactionStatusCollectionOrphanCheck = configStatusType.getTransactionStatusCollection();
            for (TransactionStatus transactionStatusCollectionOrphanCheckTransactionStatus : transactionStatusCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigStatusType (" + configStatusType + ") cannot be destroyed since the TransactionStatus " + transactionStatusCollectionOrphanCheckTransactionStatus + " in its transactionStatusCollection field has a non-nullable configStatusType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Transaction> transactionCollection = configStatusType.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setStatus(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            em.remove(configStatusType);
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

    public List<ConfigStatusType> findConfigStatusTypeEntities() {
        return findConfigStatusTypeEntities(true, -1, -1);
    }

    public List<ConfigStatusType> findConfigStatusTypeEntities(int maxResults, int firstResult) {
        return findConfigStatusTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigStatusType> findConfigStatusTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigStatusType.class));
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

    public ConfigStatusType findConfigStatusType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigStatusType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigStatusTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigStatusType> rt = cq.from(ConfigStatusType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
