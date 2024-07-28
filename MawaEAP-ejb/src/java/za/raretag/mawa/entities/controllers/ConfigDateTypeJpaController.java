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
import za.raretag.mawa.entities.TransactionDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigDateType;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigDateTypeJpaController implements Serializable {

    public ConfigDateTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigDateType configDateType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configDateType.getTransactionDateCollection() == null) {
            configDateType.setTransactionDateCollection(new ArrayList<TransactionDate>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionDate> attachedTransactionDateCollection = new ArrayList<TransactionDate>();
            for (TransactionDate transactionDateCollectionTransactionDateToAttach : configDateType.getTransactionDateCollection()) {
                transactionDateCollectionTransactionDateToAttach = em.getReference(transactionDateCollectionTransactionDateToAttach.getClass(), transactionDateCollectionTransactionDateToAttach.getTransactionDatePK());
                attachedTransactionDateCollection.add(transactionDateCollectionTransactionDateToAttach);
            }
            configDateType.setTransactionDateCollection(attachedTransactionDateCollection);
            em.persist(configDateType);
            for (TransactionDate transactionDateCollectionTransactionDate : configDateType.getTransactionDateCollection()) {
                ConfigDateType oldConfigDateTypeOfTransactionDateCollectionTransactionDate = transactionDateCollectionTransactionDate.getConfigDateType();
                transactionDateCollectionTransactionDate.setConfigDateType(configDateType);
                transactionDateCollectionTransactionDate = em.merge(transactionDateCollectionTransactionDate);
                if (oldConfigDateTypeOfTransactionDateCollectionTransactionDate != null) {
                    oldConfigDateTypeOfTransactionDateCollectionTransactionDate.getTransactionDateCollection().remove(transactionDateCollectionTransactionDate);
                    oldConfigDateTypeOfTransactionDateCollectionTransactionDate = em.merge(oldConfigDateTypeOfTransactionDateCollectionTransactionDate);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigDateType(configDateType.getId()) != null) {
                throw new PreexistingEntityException("ConfigDateType " + configDateType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigDateType configDateType) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigDateType persistentConfigDateType = em.find(ConfigDateType.class, configDateType.getId());
            Collection<TransactionDate> transactionDateCollectionOld = persistentConfigDateType.getTransactionDateCollection();
            Collection<TransactionDate> transactionDateCollectionNew = configDateType.getTransactionDateCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionDate transactionDateCollectionOldTransactionDate : transactionDateCollectionOld) {
                if (!transactionDateCollectionNew.contains(transactionDateCollectionOldTransactionDate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionDate " + transactionDateCollectionOldTransactionDate + " since its configDateType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionDate> attachedTransactionDateCollectionNew = new ArrayList<TransactionDate>();
            for (TransactionDate transactionDateCollectionNewTransactionDateToAttach : transactionDateCollectionNew) {
                transactionDateCollectionNewTransactionDateToAttach = em.getReference(transactionDateCollectionNewTransactionDateToAttach.getClass(), transactionDateCollectionNewTransactionDateToAttach.getTransactionDatePK());
                attachedTransactionDateCollectionNew.add(transactionDateCollectionNewTransactionDateToAttach);
            }
            transactionDateCollectionNew = attachedTransactionDateCollectionNew;
            configDateType.setTransactionDateCollection(transactionDateCollectionNew);
            configDateType = em.merge(configDateType);
            for (TransactionDate transactionDateCollectionNewTransactionDate : transactionDateCollectionNew) {
                if (!transactionDateCollectionOld.contains(transactionDateCollectionNewTransactionDate)) {
                    ConfigDateType oldConfigDateTypeOfTransactionDateCollectionNewTransactionDate = transactionDateCollectionNewTransactionDate.getConfigDateType();
                    transactionDateCollectionNewTransactionDate.setConfigDateType(configDateType);
                    transactionDateCollectionNewTransactionDate = em.merge(transactionDateCollectionNewTransactionDate);
                    if (oldConfigDateTypeOfTransactionDateCollectionNewTransactionDate != null && !oldConfigDateTypeOfTransactionDateCollectionNewTransactionDate.equals(configDateType)) {
                        oldConfigDateTypeOfTransactionDateCollectionNewTransactionDate.getTransactionDateCollection().remove(transactionDateCollectionNewTransactionDate);
                        oldConfigDateTypeOfTransactionDateCollectionNewTransactionDate = em.merge(oldConfigDateTypeOfTransactionDateCollectionNewTransactionDate);
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
                String id = configDateType.getId();
                if (findConfigDateType(id) == null) {
                    throw new NonexistentEntityException("The configDateType with id " + id + " no longer exists.");
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
            ConfigDateType configDateType;
            try {
                configDateType = em.getReference(ConfigDateType.class, id);
                configDateType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configDateType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionDate> transactionDateCollectionOrphanCheck = configDateType.getTransactionDateCollection();
            for (TransactionDate transactionDateCollectionOrphanCheckTransactionDate : transactionDateCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigDateType (" + configDateType + ") cannot be destroyed since the TransactionDate " + transactionDateCollectionOrphanCheckTransactionDate + " in its transactionDateCollection field has a non-nullable configDateType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configDateType);
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

    public List<ConfigDateType> findConfigDateTypeEntities() {
        return findConfigDateTypeEntities(true, -1, -1);
    }

    public List<ConfigDateType> findConfigDateTypeEntities(int maxResults, int firstResult) {
        return findConfigDateTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigDateType> findConfigDateTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigDateType.class));
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

    public ConfigDateType findConfigDateType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigDateType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigDateTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigDateType> rt = cq.from(ConfigDateType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
