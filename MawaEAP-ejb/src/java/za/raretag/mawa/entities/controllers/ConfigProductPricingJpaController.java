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
import za.raretag.mawa.entities.TransactionItemPricing;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigProductPricing;
import za.raretag.mawa.entities.ProductPricing;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigProductPricingJpaController implements Serializable {

    public ConfigProductPricingJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigProductPricing configProductPricing) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configProductPricing.getTransactionItemPricingCollection() == null) {
            configProductPricing.setTransactionItemPricingCollection(new ArrayList<TransactionItemPricing>());
        }
        if (configProductPricing.getProductPricingCollection() == null) {
            configProductPricing.setProductPricingCollection(new ArrayList<ProductPricing>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionItemPricing> attachedTransactionItemPricingCollection = new ArrayList<TransactionItemPricing>();
            for (TransactionItemPricing transactionItemPricingCollectionTransactionItemPricingToAttach : configProductPricing.getTransactionItemPricingCollection()) {
                transactionItemPricingCollectionTransactionItemPricingToAttach = em.getReference(transactionItemPricingCollectionTransactionItemPricingToAttach.getClass(), transactionItemPricingCollectionTransactionItemPricingToAttach.getTransactionItemPricingPK());
                attachedTransactionItemPricingCollection.add(transactionItemPricingCollectionTransactionItemPricingToAttach);
            }
            configProductPricing.setTransactionItemPricingCollection(attachedTransactionItemPricingCollection);
            Collection<ProductPricing> attachedProductPricingCollection = new ArrayList<ProductPricing>();
            for (ProductPricing productPricingCollectionProductPricingToAttach : configProductPricing.getProductPricingCollection()) {
                productPricingCollectionProductPricingToAttach = em.getReference(productPricingCollectionProductPricingToAttach.getClass(), productPricingCollectionProductPricingToAttach.getProductPricingPK());
                attachedProductPricingCollection.add(productPricingCollectionProductPricingToAttach);
            }
            configProductPricing.setProductPricingCollection(attachedProductPricingCollection);
            em.persist(configProductPricing);
            for (TransactionItemPricing transactionItemPricingCollectionTransactionItemPricing : configProductPricing.getTransactionItemPricingCollection()) {
                ConfigProductPricing oldConfigProductPricingOfTransactionItemPricingCollectionTransactionItemPricing = transactionItemPricingCollectionTransactionItemPricing.getConfigProductPricing();
                transactionItemPricingCollectionTransactionItemPricing.setConfigProductPricing(configProductPricing);
                transactionItemPricingCollectionTransactionItemPricing = em.merge(transactionItemPricingCollectionTransactionItemPricing);
                if (oldConfigProductPricingOfTransactionItemPricingCollectionTransactionItemPricing != null) {
                    oldConfigProductPricingOfTransactionItemPricingCollectionTransactionItemPricing.getTransactionItemPricingCollection().remove(transactionItemPricingCollectionTransactionItemPricing);
                    oldConfigProductPricingOfTransactionItemPricingCollectionTransactionItemPricing = em.merge(oldConfigProductPricingOfTransactionItemPricingCollectionTransactionItemPricing);
                }
            }
            for (ProductPricing productPricingCollectionProductPricing : configProductPricing.getProductPricingCollection()) {
                ConfigProductPricing oldConfigProductPricingOfProductPricingCollectionProductPricing = productPricingCollectionProductPricing.getConfigProductPricing();
                productPricingCollectionProductPricing.setConfigProductPricing(configProductPricing);
                productPricingCollectionProductPricing = em.merge(productPricingCollectionProductPricing);
                if (oldConfigProductPricingOfProductPricingCollectionProductPricing != null) {
                    oldConfigProductPricingOfProductPricingCollectionProductPricing.getProductPricingCollection().remove(productPricingCollectionProductPricing);
                    oldConfigProductPricingOfProductPricingCollectionProductPricing = em.merge(oldConfigProductPricingOfProductPricingCollectionProductPricing);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigProductPricing(configProductPricing.getId()) != null) {
                throw new PreexistingEntityException("ConfigProductPricing " + configProductPricing + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigProductPricing configProductPricing) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigProductPricing persistentConfigProductPricing = em.find(ConfigProductPricing.class, configProductPricing.getId());
            Collection<TransactionItemPricing> transactionItemPricingCollectionOld = persistentConfigProductPricing.getTransactionItemPricingCollection();
            Collection<TransactionItemPricing> transactionItemPricingCollectionNew = configProductPricing.getTransactionItemPricingCollection();
            Collection<ProductPricing> productPricingCollectionOld = persistentConfigProductPricing.getProductPricingCollection();
            Collection<ProductPricing> productPricingCollectionNew = configProductPricing.getProductPricingCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionItemPricing transactionItemPricingCollectionOldTransactionItemPricing : transactionItemPricingCollectionOld) {
                if (!transactionItemPricingCollectionNew.contains(transactionItemPricingCollectionOldTransactionItemPricing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionItemPricing " + transactionItemPricingCollectionOldTransactionItemPricing + " since its configProductPricing field is not nullable.");
                }
            }
            for (ProductPricing productPricingCollectionOldProductPricing : productPricingCollectionOld) {
                if (!productPricingCollectionNew.contains(productPricingCollectionOldProductPricing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductPricing " + productPricingCollectionOldProductPricing + " since its configProductPricing field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionItemPricing> attachedTransactionItemPricingCollectionNew = new ArrayList<TransactionItemPricing>();
            for (TransactionItemPricing transactionItemPricingCollectionNewTransactionItemPricingToAttach : transactionItemPricingCollectionNew) {
                transactionItemPricingCollectionNewTransactionItemPricingToAttach = em.getReference(transactionItemPricingCollectionNewTransactionItemPricingToAttach.getClass(), transactionItemPricingCollectionNewTransactionItemPricingToAttach.getTransactionItemPricingPK());
                attachedTransactionItemPricingCollectionNew.add(transactionItemPricingCollectionNewTransactionItemPricingToAttach);
            }
            transactionItemPricingCollectionNew = attachedTransactionItemPricingCollectionNew;
            configProductPricing.setTransactionItemPricingCollection(transactionItemPricingCollectionNew);
            Collection<ProductPricing> attachedProductPricingCollectionNew = new ArrayList<ProductPricing>();
            for (ProductPricing productPricingCollectionNewProductPricingToAttach : productPricingCollectionNew) {
                productPricingCollectionNewProductPricingToAttach = em.getReference(productPricingCollectionNewProductPricingToAttach.getClass(), productPricingCollectionNewProductPricingToAttach.getProductPricingPK());
                attachedProductPricingCollectionNew.add(productPricingCollectionNewProductPricingToAttach);
            }
            productPricingCollectionNew = attachedProductPricingCollectionNew;
            configProductPricing.setProductPricingCollection(productPricingCollectionNew);
            configProductPricing = em.merge(configProductPricing);
            for (TransactionItemPricing transactionItemPricingCollectionNewTransactionItemPricing : transactionItemPricingCollectionNew) {
                if (!transactionItemPricingCollectionOld.contains(transactionItemPricingCollectionNewTransactionItemPricing)) {
                    ConfigProductPricing oldConfigProductPricingOfTransactionItemPricingCollectionNewTransactionItemPricing = transactionItemPricingCollectionNewTransactionItemPricing.getConfigProductPricing();
                    transactionItemPricingCollectionNewTransactionItemPricing.setConfigProductPricing(configProductPricing);
                    transactionItemPricingCollectionNewTransactionItemPricing = em.merge(transactionItemPricingCollectionNewTransactionItemPricing);
                    if (oldConfigProductPricingOfTransactionItemPricingCollectionNewTransactionItemPricing != null && !oldConfigProductPricingOfTransactionItemPricingCollectionNewTransactionItemPricing.equals(configProductPricing)) {
                        oldConfigProductPricingOfTransactionItemPricingCollectionNewTransactionItemPricing.getTransactionItemPricingCollection().remove(transactionItemPricingCollectionNewTransactionItemPricing);
                        oldConfigProductPricingOfTransactionItemPricingCollectionNewTransactionItemPricing = em.merge(oldConfigProductPricingOfTransactionItemPricingCollectionNewTransactionItemPricing);
                    }
                }
            }
            for (ProductPricing productPricingCollectionNewProductPricing : productPricingCollectionNew) {
                if (!productPricingCollectionOld.contains(productPricingCollectionNewProductPricing)) {
                    ConfigProductPricing oldConfigProductPricingOfProductPricingCollectionNewProductPricing = productPricingCollectionNewProductPricing.getConfigProductPricing();
                    productPricingCollectionNewProductPricing.setConfigProductPricing(configProductPricing);
                    productPricingCollectionNewProductPricing = em.merge(productPricingCollectionNewProductPricing);
                    if (oldConfigProductPricingOfProductPricingCollectionNewProductPricing != null && !oldConfigProductPricingOfProductPricingCollectionNewProductPricing.equals(configProductPricing)) {
                        oldConfigProductPricingOfProductPricingCollectionNewProductPricing.getProductPricingCollection().remove(productPricingCollectionNewProductPricing);
                        oldConfigProductPricingOfProductPricingCollectionNewProductPricing = em.merge(oldConfigProductPricingOfProductPricingCollectionNewProductPricing);
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
                String id = configProductPricing.getId();
                if (findConfigProductPricing(id) == null) {
                    throw new NonexistentEntityException("The configProductPricing with id " + id + " no longer exists.");
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
            ConfigProductPricing configProductPricing;
            try {
                configProductPricing = em.getReference(ConfigProductPricing.class, id);
                configProductPricing.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configProductPricing with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionItemPricing> transactionItemPricingCollectionOrphanCheck = configProductPricing.getTransactionItemPricingCollection();
            for (TransactionItemPricing transactionItemPricingCollectionOrphanCheckTransactionItemPricing : transactionItemPricingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigProductPricing (" + configProductPricing + ") cannot be destroyed since the TransactionItemPricing " + transactionItemPricingCollectionOrphanCheckTransactionItemPricing + " in its transactionItemPricingCollection field has a non-nullable configProductPricing field.");
            }
            Collection<ProductPricing> productPricingCollectionOrphanCheck = configProductPricing.getProductPricingCollection();
            for (ProductPricing productPricingCollectionOrphanCheckProductPricing : productPricingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigProductPricing (" + configProductPricing + ") cannot be destroyed since the ProductPricing " + productPricingCollectionOrphanCheckProductPricing + " in its productPricingCollection field has a non-nullable configProductPricing field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configProductPricing);
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

    public List<ConfigProductPricing> findConfigProductPricingEntities() {
        return findConfigProductPricingEntities(true, -1, -1);
    }

    public List<ConfigProductPricing> findConfigProductPricingEntities(int maxResults, int firstResult) {
        return findConfigProductPricingEntities(false, maxResults, firstResult);
    }

    private List<ConfigProductPricing> findConfigProductPricingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigProductPricing.class));
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

    public ConfigProductPricing findConfigProductPricing(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigProductPricing.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigProductPricingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigProductPricing> rt = cq.from(ConfigProductPricing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
