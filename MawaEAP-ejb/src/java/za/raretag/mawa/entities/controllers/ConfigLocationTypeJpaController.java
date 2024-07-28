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
import za.raretag.mawa.entities.TransactionLocation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigLocationType;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigLocationTypeJpaController implements Serializable {

    public ConfigLocationTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigLocationType configLocationType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configLocationType.getTransactionLocationCollection() == null) {
            configLocationType.setTransactionLocationCollection(new ArrayList<TransactionLocation>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionLocation> attachedTransactionLocationCollection = new ArrayList<TransactionLocation>();
            for (TransactionLocation transactionLocationCollectionTransactionLocationToAttach : configLocationType.getTransactionLocationCollection()) {
                transactionLocationCollectionTransactionLocationToAttach = em.getReference(transactionLocationCollectionTransactionLocationToAttach.getClass(), transactionLocationCollectionTransactionLocationToAttach.getTransactionLocationPK());
                attachedTransactionLocationCollection.add(transactionLocationCollectionTransactionLocationToAttach);
            }
            configLocationType.setTransactionLocationCollection(attachedTransactionLocationCollection);
            em.persist(configLocationType);
            for (TransactionLocation transactionLocationCollectionTransactionLocation : configLocationType.getTransactionLocationCollection()) {
                ConfigLocationType oldConfigLocationTypeOfTransactionLocationCollectionTransactionLocation = transactionLocationCollectionTransactionLocation.getConfigLocationType();
                transactionLocationCollectionTransactionLocation.setConfigLocationType(configLocationType);
                transactionLocationCollectionTransactionLocation = em.merge(transactionLocationCollectionTransactionLocation);
                if (oldConfigLocationTypeOfTransactionLocationCollectionTransactionLocation != null) {
                    oldConfigLocationTypeOfTransactionLocationCollectionTransactionLocation.getTransactionLocationCollection().remove(transactionLocationCollectionTransactionLocation);
                    oldConfigLocationTypeOfTransactionLocationCollectionTransactionLocation = em.merge(oldConfigLocationTypeOfTransactionLocationCollectionTransactionLocation);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigLocationType(configLocationType.getId()) != null) {
                throw new PreexistingEntityException("ConfigLocationType " + configLocationType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigLocationType configLocationType) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigLocationType persistentConfigLocationType = em.find(ConfigLocationType.class, configLocationType.getId());
            Collection<TransactionLocation> transactionLocationCollectionOld = persistentConfigLocationType.getTransactionLocationCollection();
            Collection<TransactionLocation> transactionLocationCollectionNew = configLocationType.getTransactionLocationCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionLocation transactionLocationCollectionOldTransactionLocation : transactionLocationCollectionOld) {
                if (!transactionLocationCollectionNew.contains(transactionLocationCollectionOldTransactionLocation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionLocation " + transactionLocationCollectionOldTransactionLocation + " since its configLocationType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionLocation> attachedTransactionLocationCollectionNew = new ArrayList<TransactionLocation>();
            for (TransactionLocation transactionLocationCollectionNewTransactionLocationToAttach : transactionLocationCollectionNew) {
                transactionLocationCollectionNewTransactionLocationToAttach = em.getReference(transactionLocationCollectionNewTransactionLocationToAttach.getClass(), transactionLocationCollectionNewTransactionLocationToAttach.getTransactionLocationPK());
                attachedTransactionLocationCollectionNew.add(transactionLocationCollectionNewTransactionLocationToAttach);
            }
            transactionLocationCollectionNew = attachedTransactionLocationCollectionNew;
            configLocationType.setTransactionLocationCollection(transactionLocationCollectionNew);
            configLocationType = em.merge(configLocationType);
            for (TransactionLocation transactionLocationCollectionNewTransactionLocation : transactionLocationCollectionNew) {
                if (!transactionLocationCollectionOld.contains(transactionLocationCollectionNewTransactionLocation)) {
                    ConfigLocationType oldConfigLocationTypeOfTransactionLocationCollectionNewTransactionLocation = transactionLocationCollectionNewTransactionLocation.getConfigLocationType();
                    transactionLocationCollectionNewTransactionLocation.setConfigLocationType(configLocationType);
                    transactionLocationCollectionNewTransactionLocation = em.merge(transactionLocationCollectionNewTransactionLocation);
                    if (oldConfigLocationTypeOfTransactionLocationCollectionNewTransactionLocation != null && !oldConfigLocationTypeOfTransactionLocationCollectionNewTransactionLocation.equals(configLocationType)) {
                        oldConfigLocationTypeOfTransactionLocationCollectionNewTransactionLocation.getTransactionLocationCollection().remove(transactionLocationCollectionNewTransactionLocation);
                        oldConfigLocationTypeOfTransactionLocationCollectionNewTransactionLocation = em.merge(oldConfigLocationTypeOfTransactionLocationCollectionNewTransactionLocation);
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
                String id = configLocationType.getId();
                if (findConfigLocationType(id) == null) {
                    throw new NonexistentEntityException("The configLocationType with id " + id + " no longer exists.");
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
            ConfigLocationType configLocationType;
            try {
                configLocationType = em.getReference(ConfigLocationType.class, id);
                configLocationType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configLocationType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionLocation> transactionLocationCollectionOrphanCheck = configLocationType.getTransactionLocationCollection();
            for (TransactionLocation transactionLocationCollectionOrphanCheckTransactionLocation : transactionLocationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigLocationType (" + configLocationType + ") cannot be destroyed since the TransactionLocation " + transactionLocationCollectionOrphanCheckTransactionLocation + " in its transactionLocationCollection field has a non-nullable configLocationType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configLocationType);
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

    public List<ConfigLocationType> findConfigLocationTypeEntities() {
        return findConfigLocationTypeEntities(true, -1, -1);
    }

    public List<ConfigLocationType> findConfigLocationTypeEntities(int maxResults, int firstResult) {
        return findConfigLocationTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigLocationType> findConfigLocationTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigLocationType.class));
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

    public ConfigLocationType findConfigLocationType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigLocationType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigLocationTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigLocationType> rt = cq.from(ConfigLocationType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
