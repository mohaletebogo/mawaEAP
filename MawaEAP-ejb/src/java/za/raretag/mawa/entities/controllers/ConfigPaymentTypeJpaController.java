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
import za.raretag.mawa.entities.TransactionPayment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigPaymentType;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigPaymentTypeJpaController implements Serializable {

    public ConfigPaymentTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigPaymentType configPaymentType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configPaymentType.getTransactionPaymentCollection() == null) {
            configPaymentType.setTransactionPaymentCollection(new ArrayList<TransactionPayment>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionPayment> attachedTransactionPaymentCollection = new ArrayList<TransactionPayment>();
            for (TransactionPayment transactionPaymentCollectionTransactionPaymentToAttach : configPaymentType.getTransactionPaymentCollection()) {
                transactionPaymentCollectionTransactionPaymentToAttach = em.getReference(transactionPaymentCollectionTransactionPaymentToAttach.getClass(), transactionPaymentCollectionTransactionPaymentToAttach.getPaymentNo());
                attachedTransactionPaymentCollection.add(transactionPaymentCollectionTransactionPaymentToAttach);
            }
            configPaymentType.setTransactionPaymentCollection(attachedTransactionPaymentCollection);
            em.persist(configPaymentType);
            for (TransactionPayment transactionPaymentCollectionTransactionPayment : configPaymentType.getTransactionPaymentCollection()) {
                ConfigPaymentType oldPaymentTypeOfTransactionPaymentCollectionTransactionPayment = transactionPaymentCollectionTransactionPayment.getPaymentType();
                transactionPaymentCollectionTransactionPayment.setPaymentType(configPaymentType);
                transactionPaymentCollectionTransactionPayment = em.merge(transactionPaymentCollectionTransactionPayment);
                if (oldPaymentTypeOfTransactionPaymentCollectionTransactionPayment != null) {
                    oldPaymentTypeOfTransactionPaymentCollectionTransactionPayment.getTransactionPaymentCollection().remove(transactionPaymentCollectionTransactionPayment);
                    oldPaymentTypeOfTransactionPaymentCollectionTransactionPayment = em.merge(oldPaymentTypeOfTransactionPaymentCollectionTransactionPayment);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigPaymentType(configPaymentType.getId()) != null) {
                throw new PreexistingEntityException("ConfigPaymentType " + configPaymentType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigPaymentType configPaymentType) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigPaymentType persistentConfigPaymentType = em.find(ConfigPaymentType.class, configPaymentType.getId());
            Collection<TransactionPayment> transactionPaymentCollectionOld = persistentConfigPaymentType.getTransactionPaymentCollection();
            Collection<TransactionPayment> transactionPaymentCollectionNew = configPaymentType.getTransactionPaymentCollection();
            Collection<TransactionPayment> attachedTransactionPaymentCollectionNew = new ArrayList<TransactionPayment>();
            for (TransactionPayment transactionPaymentCollectionNewTransactionPaymentToAttach : transactionPaymentCollectionNew) {
                transactionPaymentCollectionNewTransactionPaymentToAttach = em.getReference(transactionPaymentCollectionNewTransactionPaymentToAttach.getClass(), transactionPaymentCollectionNewTransactionPaymentToAttach.getPaymentNo());
                attachedTransactionPaymentCollectionNew.add(transactionPaymentCollectionNewTransactionPaymentToAttach);
            }
            transactionPaymentCollectionNew = attachedTransactionPaymentCollectionNew;
            configPaymentType.setTransactionPaymentCollection(transactionPaymentCollectionNew);
            configPaymentType = em.merge(configPaymentType);
            for (TransactionPayment transactionPaymentCollectionOldTransactionPayment : transactionPaymentCollectionOld) {
                if (!transactionPaymentCollectionNew.contains(transactionPaymentCollectionOldTransactionPayment)) {
                    transactionPaymentCollectionOldTransactionPayment.setPaymentType(null);
                    transactionPaymentCollectionOldTransactionPayment = em.merge(transactionPaymentCollectionOldTransactionPayment);
                }
            }
            for (TransactionPayment transactionPaymentCollectionNewTransactionPayment : transactionPaymentCollectionNew) {
                if (!transactionPaymentCollectionOld.contains(transactionPaymentCollectionNewTransactionPayment)) {
                    ConfigPaymentType oldPaymentTypeOfTransactionPaymentCollectionNewTransactionPayment = transactionPaymentCollectionNewTransactionPayment.getPaymentType();
                    transactionPaymentCollectionNewTransactionPayment.setPaymentType(configPaymentType);
                    transactionPaymentCollectionNewTransactionPayment = em.merge(transactionPaymentCollectionNewTransactionPayment);
                    if (oldPaymentTypeOfTransactionPaymentCollectionNewTransactionPayment != null && !oldPaymentTypeOfTransactionPaymentCollectionNewTransactionPayment.equals(configPaymentType)) {
                        oldPaymentTypeOfTransactionPaymentCollectionNewTransactionPayment.getTransactionPaymentCollection().remove(transactionPaymentCollectionNewTransactionPayment);
                        oldPaymentTypeOfTransactionPaymentCollectionNewTransactionPayment = em.merge(oldPaymentTypeOfTransactionPaymentCollectionNewTransactionPayment);
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
                String id = configPaymentType.getId();
                if (findConfigPaymentType(id) == null) {
                    throw new NonexistentEntityException("The configPaymentType with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigPaymentType configPaymentType;
            try {
                configPaymentType = em.getReference(ConfigPaymentType.class, id);
                configPaymentType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configPaymentType with id " + id + " no longer exists.", enfe);
            }
            Collection<TransactionPayment> transactionPaymentCollection = configPaymentType.getTransactionPaymentCollection();
            for (TransactionPayment transactionPaymentCollectionTransactionPayment : transactionPaymentCollection) {
                transactionPaymentCollectionTransactionPayment.setPaymentType(null);
                transactionPaymentCollectionTransactionPayment = em.merge(transactionPaymentCollectionTransactionPayment);
            }
            em.remove(configPaymentType);
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

    public List<ConfigPaymentType> findConfigPaymentTypeEntities() {
        return findConfigPaymentTypeEntities(true, -1, -1);
    }

    public List<ConfigPaymentType> findConfigPaymentTypeEntities(int maxResults, int firstResult) {
        return findConfigPaymentTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigPaymentType> findConfigPaymentTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigPaymentType.class));
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

    public ConfigPaymentType findConfigPaymentType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigPaymentType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigPaymentTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigPaymentType> rt = cq.from(ConfigPaymentType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
