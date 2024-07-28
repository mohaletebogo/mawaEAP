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
import za.raretag.mawa.entities.CheckOut;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigSalesArea;
import za.raretag.mawa.entities.TransactionLocation;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigSalesAreaJpaController implements Serializable {

    public ConfigSalesAreaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigSalesArea configSalesArea) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configSalesArea.getCheckOutCollection() == null) {
            configSalesArea.setCheckOutCollection(new ArrayList<CheckOut>());
        }
        if (configSalesArea.getTransactionLocationCollection() == null) {
            configSalesArea.setTransactionLocationCollection(new ArrayList<TransactionLocation>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<CheckOut> attachedCheckOutCollection = new ArrayList<CheckOut>();
            for (CheckOut checkOutCollectionCheckOutToAttach : configSalesArea.getCheckOutCollection()) {
                checkOutCollectionCheckOutToAttach = em.getReference(checkOutCollectionCheckOutToAttach.getClass(), checkOutCollectionCheckOutToAttach.getCheckingId());
                attachedCheckOutCollection.add(checkOutCollectionCheckOutToAttach);
            }
            configSalesArea.setCheckOutCollection(attachedCheckOutCollection);
            Collection<TransactionLocation> attachedTransactionLocationCollection = new ArrayList<TransactionLocation>();
            for (TransactionLocation transactionLocationCollectionTransactionLocationToAttach : configSalesArea.getTransactionLocationCollection()) {
                transactionLocationCollectionTransactionLocationToAttach = em.getReference(transactionLocationCollectionTransactionLocationToAttach.getClass(), transactionLocationCollectionTransactionLocationToAttach.getTransactionLocationPK());
                attachedTransactionLocationCollection.add(transactionLocationCollectionTransactionLocationToAttach);
            }
            configSalesArea.setTransactionLocationCollection(attachedTransactionLocationCollection);
            em.persist(configSalesArea);
            for (CheckOut checkOutCollectionCheckOut : configSalesArea.getCheckOutCollection()) {
                ConfigSalesArea oldSalesAreaOfCheckOutCollectionCheckOut = checkOutCollectionCheckOut.getSalesArea();
                checkOutCollectionCheckOut.setSalesArea(configSalesArea);
                checkOutCollectionCheckOut = em.merge(checkOutCollectionCheckOut);
                if (oldSalesAreaOfCheckOutCollectionCheckOut != null) {
                    oldSalesAreaOfCheckOutCollectionCheckOut.getCheckOutCollection().remove(checkOutCollectionCheckOut);
                    oldSalesAreaOfCheckOutCollectionCheckOut = em.merge(oldSalesAreaOfCheckOutCollectionCheckOut);
                }
            }
            for (TransactionLocation transactionLocationCollectionTransactionLocation : configSalesArea.getTransactionLocationCollection()) {
                ConfigSalesArea oldConfigSalesAreaOfTransactionLocationCollectionTransactionLocation = transactionLocationCollectionTransactionLocation.getConfigSalesArea();
                transactionLocationCollectionTransactionLocation.setConfigSalesArea(configSalesArea);
                transactionLocationCollectionTransactionLocation = em.merge(transactionLocationCollectionTransactionLocation);
                if (oldConfigSalesAreaOfTransactionLocationCollectionTransactionLocation != null) {
                    oldConfigSalesAreaOfTransactionLocationCollectionTransactionLocation.getTransactionLocationCollection().remove(transactionLocationCollectionTransactionLocation);
                    oldConfigSalesAreaOfTransactionLocationCollectionTransactionLocation = em.merge(oldConfigSalesAreaOfTransactionLocationCollectionTransactionLocation);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigSalesArea(configSalesArea.getId()) != null) {
                throw new PreexistingEntityException("ConfigSalesArea " + configSalesArea + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigSalesArea configSalesArea) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigSalesArea persistentConfigSalesArea = em.find(ConfigSalesArea.class, configSalesArea.getId());
            Collection<CheckOut> checkOutCollectionOld = persistentConfigSalesArea.getCheckOutCollection();
            Collection<CheckOut> checkOutCollectionNew = configSalesArea.getCheckOutCollection();
            Collection<TransactionLocation> transactionLocationCollectionOld = persistentConfigSalesArea.getTransactionLocationCollection();
            Collection<TransactionLocation> transactionLocationCollectionNew = configSalesArea.getTransactionLocationCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionLocation transactionLocationCollectionOldTransactionLocation : transactionLocationCollectionOld) {
                if (!transactionLocationCollectionNew.contains(transactionLocationCollectionOldTransactionLocation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionLocation " + transactionLocationCollectionOldTransactionLocation + " since its configSalesArea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CheckOut> attachedCheckOutCollectionNew = new ArrayList<CheckOut>();
            for (CheckOut checkOutCollectionNewCheckOutToAttach : checkOutCollectionNew) {
                checkOutCollectionNewCheckOutToAttach = em.getReference(checkOutCollectionNewCheckOutToAttach.getClass(), checkOutCollectionNewCheckOutToAttach.getCheckingId());
                attachedCheckOutCollectionNew.add(checkOutCollectionNewCheckOutToAttach);
            }
            checkOutCollectionNew = attachedCheckOutCollectionNew;
            configSalesArea.setCheckOutCollection(checkOutCollectionNew);
            Collection<TransactionLocation> attachedTransactionLocationCollectionNew = new ArrayList<TransactionLocation>();
            for (TransactionLocation transactionLocationCollectionNewTransactionLocationToAttach : transactionLocationCollectionNew) {
                transactionLocationCollectionNewTransactionLocationToAttach = em.getReference(transactionLocationCollectionNewTransactionLocationToAttach.getClass(), transactionLocationCollectionNewTransactionLocationToAttach.getTransactionLocationPK());
                attachedTransactionLocationCollectionNew.add(transactionLocationCollectionNewTransactionLocationToAttach);
            }
            transactionLocationCollectionNew = attachedTransactionLocationCollectionNew;
            configSalesArea.setTransactionLocationCollection(transactionLocationCollectionNew);
            configSalesArea = em.merge(configSalesArea);
            for (CheckOut checkOutCollectionOldCheckOut : checkOutCollectionOld) {
                if (!checkOutCollectionNew.contains(checkOutCollectionOldCheckOut)) {
                    checkOutCollectionOldCheckOut.setSalesArea(null);
                    checkOutCollectionOldCheckOut = em.merge(checkOutCollectionOldCheckOut);
                }
            }
            for (CheckOut checkOutCollectionNewCheckOut : checkOutCollectionNew) {
                if (!checkOutCollectionOld.contains(checkOutCollectionNewCheckOut)) {
                    ConfigSalesArea oldSalesAreaOfCheckOutCollectionNewCheckOut = checkOutCollectionNewCheckOut.getSalesArea();
                    checkOutCollectionNewCheckOut.setSalesArea(configSalesArea);
                    checkOutCollectionNewCheckOut = em.merge(checkOutCollectionNewCheckOut);
                    if (oldSalesAreaOfCheckOutCollectionNewCheckOut != null && !oldSalesAreaOfCheckOutCollectionNewCheckOut.equals(configSalesArea)) {
                        oldSalesAreaOfCheckOutCollectionNewCheckOut.getCheckOutCollection().remove(checkOutCollectionNewCheckOut);
                        oldSalesAreaOfCheckOutCollectionNewCheckOut = em.merge(oldSalesAreaOfCheckOutCollectionNewCheckOut);
                    }
                }
            }
            for (TransactionLocation transactionLocationCollectionNewTransactionLocation : transactionLocationCollectionNew) {
                if (!transactionLocationCollectionOld.contains(transactionLocationCollectionNewTransactionLocation)) {
                    ConfigSalesArea oldConfigSalesAreaOfTransactionLocationCollectionNewTransactionLocation = transactionLocationCollectionNewTransactionLocation.getConfigSalesArea();
                    transactionLocationCollectionNewTransactionLocation.setConfigSalesArea(configSalesArea);
                    transactionLocationCollectionNewTransactionLocation = em.merge(transactionLocationCollectionNewTransactionLocation);
                    if (oldConfigSalesAreaOfTransactionLocationCollectionNewTransactionLocation != null && !oldConfigSalesAreaOfTransactionLocationCollectionNewTransactionLocation.equals(configSalesArea)) {
                        oldConfigSalesAreaOfTransactionLocationCollectionNewTransactionLocation.getTransactionLocationCollection().remove(transactionLocationCollectionNewTransactionLocation);
                        oldConfigSalesAreaOfTransactionLocationCollectionNewTransactionLocation = em.merge(oldConfigSalesAreaOfTransactionLocationCollectionNewTransactionLocation);
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
                String id = configSalesArea.getId();
                if (findConfigSalesArea(id) == null) {
                    throw new NonexistentEntityException("The configSalesArea with id " + id + " no longer exists.");
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
            ConfigSalesArea configSalesArea;
            try {
                configSalesArea = em.getReference(ConfigSalesArea.class, id);
                configSalesArea.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configSalesArea with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionLocation> transactionLocationCollectionOrphanCheck = configSalesArea.getTransactionLocationCollection();
            for (TransactionLocation transactionLocationCollectionOrphanCheckTransactionLocation : transactionLocationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigSalesArea (" + configSalesArea + ") cannot be destroyed since the TransactionLocation " + transactionLocationCollectionOrphanCheckTransactionLocation + " in its transactionLocationCollection field has a non-nullable configSalesArea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CheckOut> checkOutCollection = configSalesArea.getCheckOutCollection();
            for (CheckOut checkOutCollectionCheckOut : checkOutCollection) {
                checkOutCollectionCheckOut.setSalesArea(null);
                checkOutCollectionCheckOut = em.merge(checkOutCollectionCheckOut);
            }
            em.remove(configSalesArea);
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

    public List<ConfigSalesArea> findConfigSalesAreaEntities() {
        return findConfigSalesAreaEntities(true, -1, -1);
    }

    public List<ConfigSalesArea> findConfigSalesAreaEntities(int maxResults, int firstResult) {
        return findConfigSalesAreaEntities(false, maxResults, firstResult);
    }

    private List<ConfigSalesArea> findConfigSalesAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigSalesArea.class));
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

    public ConfigSalesArea findConfigSalesArea(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigSalesArea.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigSalesAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigSalesArea> rt = cq.from(ConfigSalesArea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
