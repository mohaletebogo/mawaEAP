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
import za.raretag.mawa.entities.ConfigProductCategory;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.Product;
import za.raretag.mawa.entities.TransactionItemPricing;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.TransactionItem;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionItemJpaController implements Serializable {

    public TransactionItemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionItem transactionItem) throws RollbackFailureException, Exception {
        if (transactionItem.getTransactionItemPricingCollection() == null) {
            transactionItem.setTransactionItemPricingCollection(new ArrayList<TransactionItemPricing>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigProductCategory itemCategory = transactionItem.getItemCategory();
            if (itemCategory != null) {
                itemCategory = em.getReference(itemCategory.getClass(), itemCategory.getId());
                transactionItem.setItemCategory(itemCategory);
            }
            Transaction transactionNo = transactionItem.getTransactionNo();
            if (transactionNo != null) {
                transactionNo = em.getReference(transactionNo.getClass(), transactionNo.getTransactionNo());
                transactionItem.setTransactionNo(transactionNo);
            }
            Product productId = transactionItem.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductCode());
                transactionItem.setProductId(productId);
            }
            Collection<TransactionItemPricing> attachedTransactionItemPricingCollection = new ArrayList<TransactionItemPricing>();
            for (TransactionItemPricing transactionItemPricingCollectionTransactionItemPricingToAttach : transactionItem.getTransactionItemPricingCollection()) {
                transactionItemPricingCollectionTransactionItemPricingToAttach = em.getReference(transactionItemPricingCollectionTransactionItemPricingToAttach.getClass(), transactionItemPricingCollectionTransactionItemPricingToAttach.getTransactionItemPricingPK());
                attachedTransactionItemPricingCollection.add(transactionItemPricingCollectionTransactionItemPricingToAttach);
            }
            transactionItem.setTransactionItemPricingCollection(attachedTransactionItemPricingCollection);
            em.persist(transactionItem);
            if (itemCategory != null) {
                itemCategory.getTransactionItemCollection().add(transactionItem);
                itemCategory = em.merge(itemCategory);
            }
            if (transactionNo != null) {
                transactionNo.getTransactionItemCollection().add(transactionItem);
                transactionNo = em.merge(transactionNo);
            }
            if (productId != null) {
                productId.getTransactionItemCollection().add(transactionItem);
                productId = em.merge(productId);
            }
            for (TransactionItemPricing transactionItemPricingCollectionTransactionItemPricing : transactionItem.getTransactionItemPricingCollection()) {
                TransactionItem oldTransactionItemOfTransactionItemPricingCollectionTransactionItemPricing = transactionItemPricingCollectionTransactionItemPricing.getTransactionItem();
                transactionItemPricingCollectionTransactionItemPricing.setTransactionItem(transactionItem);
                transactionItemPricingCollectionTransactionItemPricing = em.merge(transactionItemPricingCollectionTransactionItemPricing);
                if (oldTransactionItemOfTransactionItemPricingCollectionTransactionItemPricing != null) {
                    oldTransactionItemOfTransactionItemPricingCollectionTransactionItemPricing.getTransactionItemPricingCollection().remove(transactionItemPricingCollectionTransactionItemPricing);
                    oldTransactionItemOfTransactionItemPricingCollectionTransactionItemPricing = em.merge(oldTransactionItemOfTransactionItemPricingCollectionTransactionItemPricing);
                }
            }
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

    public void edit(TransactionItem transactionItem) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionItem persistentTransactionItem = em.find(TransactionItem.class, transactionItem.getItemId());
            ConfigProductCategory itemCategoryOld = persistentTransactionItem.getItemCategory();
            ConfigProductCategory itemCategoryNew = transactionItem.getItemCategory();
            Transaction transactionNoOld = persistentTransactionItem.getTransactionNo();
            Transaction transactionNoNew = transactionItem.getTransactionNo();
            Product productIdOld = persistentTransactionItem.getProductId();
            Product productIdNew = transactionItem.getProductId();
            Collection<TransactionItemPricing> transactionItemPricingCollectionOld = persistentTransactionItem.getTransactionItemPricingCollection();
            Collection<TransactionItemPricing> transactionItemPricingCollectionNew = transactionItem.getTransactionItemPricingCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionItemPricing transactionItemPricingCollectionOldTransactionItemPricing : transactionItemPricingCollectionOld) {
                if (!transactionItemPricingCollectionNew.contains(transactionItemPricingCollectionOldTransactionItemPricing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionItemPricing " + transactionItemPricingCollectionOldTransactionItemPricing + " since its transactionItem field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemCategoryNew != null) {
                itemCategoryNew = em.getReference(itemCategoryNew.getClass(), itemCategoryNew.getId());
                transactionItem.setItemCategory(itemCategoryNew);
            }
            if (transactionNoNew != null) {
                transactionNoNew = em.getReference(transactionNoNew.getClass(), transactionNoNew.getTransactionNo());
                transactionItem.setTransactionNo(transactionNoNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductCode());
                transactionItem.setProductId(productIdNew);
            }
            Collection<TransactionItemPricing> attachedTransactionItemPricingCollectionNew = new ArrayList<TransactionItemPricing>();
            for (TransactionItemPricing transactionItemPricingCollectionNewTransactionItemPricingToAttach : transactionItemPricingCollectionNew) {
                transactionItemPricingCollectionNewTransactionItemPricingToAttach = em.getReference(transactionItemPricingCollectionNewTransactionItemPricingToAttach.getClass(), transactionItemPricingCollectionNewTransactionItemPricingToAttach.getTransactionItemPricingPK());
                attachedTransactionItemPricingCollectionNew.add(transactionItemPricingCollectionNewTransactionItemPricingToAttach);
            }
            transactionItemPricingCollectionNew = attachedTransactionItemPricingCollectionNew;
            transactionItem.setTransactionItemPricingCollection(transactionItemPricingCollectionNew);
            transactionItem = em.merge(transactionItem);
            if (itemCategoryOld != null && !itemCategoryOld.equals(itemCategoryNew)) {
                itemCategoryOld.getTransactionItemCollection().remove(transactionItem);
                itemCategoryOld = em.merge(itemCategoryOld);
            }
            if (itemCategoryNew != null && !itemCategoryNew.equals(itemCategoryOld)) {
                itemCategoryNew.getTransactionItemCollection().add(transactionItem);
                itemCategoryNew = em.merge(itemCategoryNew);
            }
            if (transactionNoOld != null && !transactionNoOld.equals(transactionNoNew)) {
                transactionNoOld.getTransactionItemCollection().remove(transactionItem);
                transactionNoOld = em.merge(transactionNoOld);
            }
            if (transactionNoNew != null && !transactionNoNew.equals(transactionNoOld)) {
                transactionNoNew.getTransactionItemCollection().add(transactionItem);
                transactionNoNew = em.merge(transactionNoNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getTransactionItemCollection().remove(transactionItem);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getTransactionItemCollection().add(transactionItem);
                productIdNew = em.merge(productIdNew);
            }
            for (TransactionItemPricing transactionItemPricingCollectionNewTransactionItemPricing : transactionItemPricingCollectionNew) {
                if (!transactionItemPricingCollectionOld.contains(transactionItemPricingCollectionNewTransactionItemPricing)) {
                    TransactionItem oldTransactionItemOfTransactionItemPricingCollectionNewTransactionItemPricing = transactionItemPricingCollectionNewTransactionItemPricing.getTransactionItem();
                    transactionItemPricingCollectionNewTransactionItemPricing.setTransactionItem(transactionItem);
                    transactionItemPricingCollectionNewTransactionItemPricing = em.merge(transactionItemPricingCollectionNewTransactionItemPricing);
                    if (oldTransactionItemOfTransactionItemPricingCollectionNewTransactionItemPricing != null && !oldTransactionItemOfTransactionItemPricingCollectionNewTransactionItemPricing.equals(transactionItem)) {
                        oldTransactionItemOfTransactionItemPricingCollectionNewTransactionItemPricing.getTransactionItemPricingCollection().remove(transactionItemPricingCollectionNewTransactionItemPricing);
                        oldTransactionItemOfTransactionItemPricingCollectionNewTransactionItemPricing = em.merge(oldTransactionItemOfTransactionItemPricingCollectionNewTransactionItemPricing);
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
                Integer id = transactionItem.getItemId();
                if (findTransactionItem(id) == null) {
                    throw new NonexistentEntityException("The transactionItem with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionItem transactionItem;
            try {
                transactionItem = em.getReference(TransactionItem.class, id);
                transactionItem.getItemId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionItem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionItemPricing> transactionItemPricingCollectionOrphanCheck = transactionItem.getTransactionItemPricingCollection();
            for (TransactionItemPricing transactionItemPricingCollectionOrphanCheckTransactionItemPricing : transactionItemPricingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TransactionItem (" + transactionItem + ") cannot be destroyed since the TransactionItemPricing " + transactionItemPricingCollectionOrphanCheckTransactionItemPricing + " in its transactionItemPricingCollection field has a non-nullable transactionItem field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ConfigProductCategory itemCategory = transactionItem.getItemCategory();
            if (itemCategory != null) {
                itemCategory.getTransactionItemCollection().remove(transactionItem);
                itemCategory = em.merge(itemCategory);
            }
            Transaction transactionNo = transactionItem.getTransactionNo();
            if (transactionNo != null) {
                transactionNo.getTransactionItemCollection().remove(transactionItem);
                transactionNo = em.merge(transactionNo);
            }
            Product productId = transactionItem.getProductId();
            if (productId != null) {
                productId.getTransactionItemCollection().remove(transactionItem);
                productId = em.merge(productId);
            }
            em.remove(transactionItem);
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

    public List<TransactionItem> findTransactionItemEntities() {
        return findTransactionItemEntities(true, -1, -1);
    }

    public List<TransactionItem> findTransactionItemEntities(int maxResults, int firstResult) {
        return findTransactionItemEntities(false, maxResults, firstResult);
    }

    private List<TransactionItem> findTransactionItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionItem.class));
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

    public TransactionItem findTransactionItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionItem> rt = cq.from(TransactionItem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
