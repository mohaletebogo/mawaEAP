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
import za.raretag.mawa.entities.TransactionItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigProductCategory;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigProductCategoryJpaController implements Serializable {

    public ConfigProductCategoryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigProductCategory configProductCategory) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configProductCategory.getTransactionItemCollection() == null) {
            configProductCategory.setTransactionItemCollection(new ArrayList<TransactionItem>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionItem> attachedTransactionItemCollection = new ArrayList<TransactionItem>();
            for (TransactionItem transactionItemCollectionTransactionItemToAttach : configProductCategory.getTransactionItemCollection()) {
                transactionItemCollectionTransactionItemToAttach = em.getReference(transactionItemCollectionTransactionItemToAttach.getClass(), transactionItemCollectionTransactionItemToAttach.getItemId());
                attachedTransactionItemCollection.add(transactionItemCollectionTransactionItemToAttach);
            }
            configProductCategory.setTransactionItemCollection(attachedTransactionItemCollection);
            em.persist(configProductCategory);
            for (TransactionItem transactionItemCollectionTransactionItem : configProductCategory.getTransactionItemCollection()) {
                ConfigProductCategory oldItemCategoryOfTransactionItemCollectionTransactionItem = transactionItemCollectionTransactionItem.getItemCategory();
                transactionItemCollectionTransactionItem.setItemCategory(configProductCategory);
                transactionItemCollectionTransactionItem = em.merge(transactionItemCollectionTransactionItem);
                if (oldItemCategoryOfTransactionItemCollectionTransactionItem != null) {
                    oldItemCategoryOfTransactionItemCollectionTransactionItem.getTransactionItemCollection().remove(transactionItemCollectionTransactionItem);
                    oldItemCategoryOfTransactionItemCollectionTransactionItem = em.merge(oldItemCategoryOfTransactionItemCollectionTransactionItem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigProductCategory(configProductCategory.getId()) != null) {
                throw new PreexistingEntityException("ConfigProductCategory " + configProductCategory + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigProductCategory configProductCategory) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigProductCategory persistentConfigProductCategory = em.find(ConfigProductCategory.class, configProductCategory.getId());
            Collection<TransactionItem> transactionItemCollectionOld = persistentConfigProductCategory.getTransactionItemCollection();
            Collection<TransactionItem> transactionItemCollectionNew = configProductCategory.getTransactionItemCollection();
            Collection<TransactionItem> attachedTransactionItemCollectionNew = new ArrayList<TransactionItem>();
            for (TransactionItem transactionItemCollectionNewTransactionItemToAttach : transactionItemCollectionNew) {
                transactionItemCollectionNewTransactionItemToAttach = em.getReference(transactionItemCollectionNewTransactionItemToAttach.getClass(), transactionItemCollectionNewTransactionItemToAttach.getItemId());
                attachedTransactionItemCollectionNew.add(transactionItemCollectionNewTransactionItemToAttach);
            }
            transactionItemCollectionNew = attachedTransactionItemCollectionNew;
            configProductCategory.setTransactionItemCollection(transactionItemCollectionNew);
            configProductCategory = em.merge(configProductCategory);
            for (TransactionItem transactionItemCollectionOldTransactionItem : transactionItemCollectionOld) {
                if (!transactionItemCollectionNew.contains(transactionItemCollectionOldTransactionItem)) {
                    transactionItemCollectionOldTransactionItem.setItemCategory(null);
                    transactionItemCollectionOldTransactionItem = em.merge(transactionItemCollectionOldTransactionItem);
                }
            }
            for (TransactionItem transactionItemCollectionNewTransactionItem : transactionItemCollectionNew) {
                if (!transactionItemCollectionOld.contains(transactionItemCollectionNewTransactionItem)) {
                    ConfigProductCategory oldItemCategoryOfTransactionItemCollectionNewTransactionItem = transactionItemCollectionNewTransactionItem.getItemCategory();
                    transactionItemCollectionNewTransactionItem.setItemCategory(configProductCategory);
                    transactionItemCollectionNewTransactionItem = em.merge(transactionItemCollectionNewTransactionItem);
                    if (oldItemCategoryOfTransactionItemCollectionNewTransactionItem != null && !oldItemCategoryOfTransactionItemCollectionNewTransactionItem.equals(configProductCategory)) {
                        oldItemCategoryOfTransactionItemCollectionNewTransactionItem.getTransactionItemCollection().remove(transactionItemCollectionNewTransactionItem);
                        oldItemCategoryOfTransactionItemCollectionNewTransactionItem = em.merge(oldItemCategoryOfTransactionItemCollectionNewTransactionItem);
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
                String id = configProductCategory.getId();
                if (findConfigProductCategory(id) == null) {
                    throw new NonexistentEntityException("The configProductCategory with id " + id + " no longer exists.");
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
            ConfigProductCategory configProductCategory;
            try {
                configProductCategory = em.getReference(ConfigProductCategory.class, id);
                configProductCategory.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configProductCategory with id " + id + " no longer exists.", enfe);
            }
            Collection<TransactionItem> transactionItemCollection = configProductCategory.getTransactionItemCollection();
            for (TransactionItem transactionItemCollectionTransactionItem : transactionItemCollection) {
                transactionItemCollectionTransactionItem.setItemCategory(null);
                transactionItemCollectionTransactionItem = em.merge(transactionItemCollectionTransactionItem);
            }
            em.remove(configProductCategory);
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

    public List<ConfigProductCategory> findConfigProductCategoryEntities() {
        return findConfigProductCategoryEntities(true, -1, -1);
    }

    public List<ConfigProductCategory> findConfigProductCategoryEntities(int maxResults, int firstResult) {
        return findConfigProductCategoryEntities(false, maxResults, firstResult);
    }

    private List<ConfigProductCategory> findConfigProductCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigProductCategory.class));
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

    public ConfigProductCategory findConfigProductCategory(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigProductCategory.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigProductCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigProductCategory> rt = cq.from(ConfigProductCategory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
