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
import za.raretag.mawa.entities.TransactionAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigAmountType;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigAmountTypeJpaController implements Serializable {

    public ConfigAmountTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigAmountType configAmountType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configAmountType.getTransactionAmountCollection() == null) {
            configAmountType.setTransactionAmountCollection(new ArrayList<TransactionAmount>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionAmount> attachedTransactionAmountCollection = new ArrayList<TransactionAmount>();
            for (TransactionAmount transactionAmountCollectionTransactionAmountToAttach : configAmountType.getTransactionAmountCollection()) {
                transactionAmountCollectionTransactionAmountToAttach = em.getReference(transactionAmountCollectionTransactionAmountToAttach.getClass(), transactionAmountCollectionTransactionAmountToAttach.getTransactionAmountPK());
                attachedTransactionAmountCollection.add(transactionAmountCollectionTransactionAmountToAttach);
            }
            configAmountType.setTransactionAmountCollection(attachedTransactionAmountCollection);
            em.persist(configAmountType);
            for (TransactionAmount transactionAmountCollectionTransactionAmount : configAmountType.getTransactionAmountCollection()) {
                ConfigAmountType oldConfigAmountTypeOfTransactionAmountCollectionTransactionAmount = transactionAmountCollectionTransactionAmount.getConfigAmountType();
                transactionAmountCollectionTransactionAmount.setConfigAmountType(configAmountType);
                transactionAmountCollectionTransactionAmount = em.merge(transactionAmountCollectionTransactionAmount);
                if (oldConfigAmountTypeOfTransactionAmountCollectionTransactionAmount != null) {
                    oldConfigAmountTypeOfTransactionAmountCollectionTransactionAmount.getTransactionAmountCollection().remove(transactionAmountCollectionTransactionAmount);
                    oldConfigAmountTypeOfTransactionAmountCollectionTransactionAmount = em.merge(oldConfigAmountTypeOfTransactionAmountCollectionTransactionAmount);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigAmountType(configAmountType.getId()) != null) {
                throw new PreexistingEntityException("ConfigAmountType " + configAmountType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigAmountType configAmountType) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigAmountType persistentConfigAmountType = em.find(ConfigAmountType.class, configAmountType.getId());
            Collection<TransactionAmount> transactionAmountCollectionOld = persistentConfigAmountType.getTransactionAmountCollection();
            Collection<TransactionAmount> transactionAmountCollectionNew = configAmountType.getTransactionAmountCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionAmount transactionAmountCollectionOldTransactionAmount : transactionAmountCollectionOld) {
                if (!transactionAmountCollectionNew.contains(transactionAmountCollectionOldTransactionAmount)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionAmount " + transactionAmountCollectionOldTransactionAmount + " since its configAmountType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionAmount> attachedTransactionAmountCollectionNew = new ArrayList<TransactionAmount>();
            for (TransactionAmount transactionAmountCollectionNewTransactionAmountToAttach : transactionAmountCollectionNew) {
                transactionAmountCollectionNewTransactionAmountToAttach = em.getReference(transactionAmountCollectionNewTransactionAmountToAttach.getClass(), transactionAmountCollectionNewTransactionAmountToAttach.getTransactionAmountPK());
                attachedTransactionAmountCollectionNew.add(transactionAmountCollectionNewTransactionAmountToAttach);
            }
            transactionAmountCollectionNew = attachedTransactionAmountCollectionNew;
            configAmountType.setTransactionAmountCollection(transactionAmountCollectionNew);
            configAmountType = em.merge(configAmountType);
            for (TransactionAmount transactionAmountCollectionNewTransactionAmount : transactionAmountCollectionNew) {
                if (!transactionAmountCollectionOld.contains(transactionAmountCollectionNewTransactionAmount)) {
                    ConfigAmountType oldConfigAmountTypeOfTransactionAmountCollectionNewTransactionAmount = transactionAmountCollectionNewTransactionAmount.getConfigAmountType();
                    transactionAmountCollectionNewTransactionAmount.setConfigAmountType(configAmountType);
                    transactionAmountCollectionNewTransactionAmount = em.merge(transactionAmountCollectionNewTransactionAmount);
                    if (oldConfigAmountTypeOfTransactionAmountCollectionNewTransactionAmount != null && !oldConfigAmountTypeOfTransactionAmountCollectionNewTransactionAmount.equals(configAmountType)) {
                        oldConfigAmountTypeOfTransactionAmountCollectionNewTransactionAmount.getTransactionAmountCollection().remove(transactionAmountCollectionNewTransactionAmount);
                        oldConfigAmountTypeOfTransactionAmountCollectionNewTransactionAmount = em.merge(oldConfigAmountTypeOfTransactionAmountCollectionNewTransactionAmount);
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
                String id = configAmountType.getId();
                if (findConfigAmountType(id) == null) {
                    throw new NonexistentEntityException("The configAmountType with id " + id + " no longer exists.");
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
            ConfigAmountType configAmountType;
            try {
                configAmountType = em.getReference(ConfigAmountType.class, id);
                configAmountType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configAmountType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionAmount> transactionAmountCollectionOrphanCheck = configAmountType.getTransactionAmountCollection();
            for (TransactionAmount transactionAmountCollectionOrphanCheckTransactionAmount : transactionAmountCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigAmountType (" + configAmountType + ") cannot be destroyed since the TransactionAmount " + transactionAmountCollectionOrphanCheckTransactionAmount + " in its transactionAmountCollection field has a non-nullable configAmountType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configAmountType);
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

    public List<ConfigAmountType> findConfigAmountTypeEntities() {
        return findConfigAmountTypeEntities(true, -1, -1);
    }

    public List<ConfigAmountType> findConfigAmountTypeEntities(int maxResults, int firstResult) {
        return findConfigAmountTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigAmountType> findConfigAmountTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigAmountType.class));
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

    public ConfigAmountType findConfigAmountType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigAmountType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigAmountTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigAmountType> rt = cq.from(ConfigAmountType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
