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
import za.raretag.mawa.entities.TransactionPartner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigPartnerFunction;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigPartnerFunctionJpaController implements Serializable {

    public ConfigPartnerFunctionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigPartnerFunction configPartnerFunction) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configPartnerFunction.getTransactionPartnerCollection() == null) {
            configPartnerFunction.setTransactionPartnerCollection(new ArrayList<TransactionPartner>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionPartner> attachedTransactionPartnerCollection = new ArrayList<TransactionPartner>();
            for (TransactionPartner transactionPartnerCollectionTransactionPartnerToAttach : configPartnerFunction.getTransactionPartnerCollection()) {
                transactionPartnerCollectionTransactionPartnerToAttach = em.getReference(transactionPartnerCollectionTransactionPartnerToAttach.getClass(), transactionPartnerCollectionTransactionPartnerToAttach.getTransactionPartnerPK());
                attachedTransactionPartnerCollection.add(transactionPartnerCollectionTransactionPartnerToAttach);
            }
            configPartnerFunction.setTransactionPartnerCollection(attachedTransactionPartnerCollection);
            em.persist(configPartnerFunction);
            for (TransactionPartner transactionPartnerCollectionTransactionPartner : configPartnerFunction.getTransactionPartnerCollection()) {
                ConfigPartnerFunction oldConfigPartnerFunctionOfTransactionPartnerCollectionTransactionPartner = transactionPartnerCollectionTransactionPartner.getConfigPartnerFunction();
                transactionPartnerCollectionTransactionPartner.setConfigPartnerFunction(configPartnerFunction);
                transactionPartnerCollectionTransactionPartner = em.merge(transactionPartnerCollectionTransactionPartner);
                if (oldConfigPartnerFunctionOfTransactionPartnerCollectionTransactionPartner != null) {
                    oldConfigPartnerFunctionOfTransactionPartnerCollectionTransactionPartner.getTransactionPartnerCollection().remove(transactionPartnerCollectionTransactionPartner);
                    oldConfigPartnerFunctionOfTransactionPartnerCollectionTransactionPartner = em.merge(oldConfigPartnerFunctionOfTransactionPartnerCollectionTransactionPartner);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigPartnerFunction(configPartnerFunction.getId()) != null) {
                throw new PreexistingEntityException("ConfigPartnerFunction " + configPartnerFunction + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigPartnerFunction configPartnerFunction) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigPartnerFunction persistentConfigPartnerFunction = em.find(ConfigPartnerFunction.class, configPartnerFunction.getId());
            Collection<TransactionPartner> transactionPartnerCollectionOld = persistentConfigPartnerFunction.getTransactionPartnerCollection();
            Collection<TransactionPartner> transactionPartnerCollectionNew = configPartnerFunction.getTransactionPartnerCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionPartner transactionPartnerCollectionOldTransactionPartner : transactionPartnerCollectionOld) {
                if (!transactionPartnerCollectionNew.contains(transactionPartnerCollectionOldTransactionPartner)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionPartner " + transactionPartnerCollectionOldTransactionPartner + " since its configPartnerFunction field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionPartner> attachedTransactionPartnerCollectionNew = new ArrayList<TransactionPartner>();
            for (TransactionPartner transactionPartnerCollectionNewTransactionPartnerToAttach : transactionPartnerCollectionNew) {
                transactionPartnerCollectionNewTransactionPartnerToAttach = em.getReference(transactionPartnerCollectionNewTransactionPartnerToAttach.getClass(), transactionPartnerCollectionNewTransactionPartnerToAttach.getTransactionPartnerPK());
                attachedTransactionPartnerCollectionNew.add(transactionPartnerCollectionNewTransactionPartnerToAttach);
            }
            transactionPartnerCollectionNew = attachedTransactionPartnerCollectionNew;
            configPartnerFunction.setTransactionPartnerCollection(transactionPartnerCollectionNew);
            configPartnerFunction = em.merge(configPartnerFunction);
            for (TransactionPartner transactionPartnerCollectionNewTransactionPartner : transactionPartnerCollectionNew) {
                if (!transactionPartnerCollectionOld.contains(transactionPartnerCollectionNewTransactionPartner)) {
                    ConfigPartnerFunction oldConfigPartnerFunctionOfTransactionPartnerCollectionNewTransactionPartner = transactionPartnerCollectionNewTransactionPartner.getConfigPartnerFunction();
                    transactionPartnerCollectionNewTransactionPartner.setConfigPartnerFunction(configPartnerFunction);
                    transactionPartnerCollectionNewTransactionPartner = em.merge(transactionPartnerCollectionNewTransactionPartner);
                    if (oldConfigPartnerFunctionOfTransactionPartnerCollectionNewTransactionPartner != null && !oldConfigPartnerFunctionOfTransactionPartnerCollectionNewTransactionPartner.equals(configPartnerFunction)) {
                        oldConfigPartnerFunctionOfTransactionPartnerCollectionNewTransactionPartner.getTransactionPartnerCollection().remove(transactionPartnerCollectionNewTransactionPartner);
                        oldConfigPartnerFunctionOfTransactionPartnerCollectionNewTransactionPartner = em.merge(oldConfigPartnerFunctionOfTransactionPartnerCollectionNewTransactionPartner);
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
                String id = configPartnerFunction.getId();
                if (findConfigPartnerFunction(id) == null) {
                    throw new NonexistentEntityException("The configPartnerFunction with id " + id + " no longer exists.");
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
            ConfigPartnerFunction configPartnerFunction;
            try {
                configPartnerFunction = em.getReference(ConfigPartnerFunction.class, id);
                configPartnerFunction.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configPartnerFunction with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionPartner> transactionPartnerCollectionOrphanCheck = configPartnerFunction.getTransactionPartnerCollection();
            for (TransactionPartner transactionPartnerCollectionOrphanCheckTransactionPartner : transactionPartnerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigPartnerFunction (" + configPartnerFunction + ") cannot be destroyed since the TransactionPartner " + transactionPartnerCollectionOrphanCheckTransactionPartner + " in its transactionPartnerCollection field has a non-nullable configPartnerFunction field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configPartnerFunction);
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

    public List<ConfigPartnerFunction> findConfigPartnerFunctionEntities() {
        return findConfigPartnerFunctionEntities(true, -1, -1);
    }

    public List<ConfigPartnerFunction> findConfigPartnerFunctionEntities(int maxResults, int firstResult) {
        return findConfigPartnerFunctionEntities(false, maxResults, firstResult);
    }

    private List<ConfigPartnerFunction> findConfigPartnerFunctionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigPartnerFunction.class));
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

    public ConfigPartnerFunction findConfigPartnerFunction(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigPartnerFunction.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigPartnerFunctionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigPartnerFunction> rt = cq.from(ConfigPartnerFunction.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
