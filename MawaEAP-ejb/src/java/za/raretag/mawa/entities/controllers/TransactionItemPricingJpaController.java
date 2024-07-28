/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.TransactionItem;
import za.raretag.mawa.entities.ConfigProductPricing;
import za.raretag.mawa.entities.TransactionItemPricing;
import za.raretag.mawa.entities.TransactionItemPricingPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionItemPricingJpaController implements Serializable {

    public TransactionItemPricingJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionItemPricing transactionItemPricing) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transactionItemPricing.getTransactionItemPricingPK() == null) {
            transactionItemPricing.setTransactionItemPricingPK(new TransactionItemPricingPK());
        }
        transactionItemPricing.getTransactionItemPricingPK().setItemId(transactionItemPricing.getTransactionItem().getItemId());
        transactionItemPricing.getTransactionItemPricingPK().setPricingType(transactionItemPricing.getConfigProductPricing().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionItem transactionItem = transactionItemPricing.getTransactionItem();
            if (transactionItem != null) {
                transactionItem = em.getReference(transactionItem.getClass(), transactionItem.getItemId());
                transactionItemPricing.setTransactionItem(transactionItem);
            }
            ConfigProductPricing configProductPricing = transactionItemPricing.getConfigProductPricing();
            if (configProductPricing != null) {
                configProductPricing = em.getReference(configProductPricing.getClass(), configProductPricing.getId());
                transactionItemPricing.setConfigProductPricing(configProductPricing);
            }
            em.persist(transactionItemPricing);
            if (transactionItem != null) {
                transactionItem.getTransactionItemPricingCollection().add(transactionItemPricing);
                transactionItem = em.merge(transactionItem);
            }
            if (configProductPricing != null) {
                configProductPricing.getTransactionItemPricingCollection().add(transactionItemPricing);
                configProductPricing = em.merge(configProductPricing);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransactionItemPricing(transactionItemPricing.getTransactionItemPricingPK()) != null) {
                throw new PreexistingEntityException("TransactionItemPricing " + transactionItemPricing + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionItemPricing transactionItemPricing) throws NonexistentEntityException, RollbackFailureException, Exception {
        transactionItemPricing.getTransactionItemPricingPK().setItemId(transactionItemPricing.getTransactionItem().getItemId());
        transactionItemPricing.getTransactionItemPricingPK().setPricingType(transactionItemPricing.getConfigProductPricing().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionItemPricing persistentTransactionItemPricing = em.find(TransactionItemPricing.class, transactionItemPricing.getTransactionItemPricingPK());
            TransactionItem transactionItemOld = persistentTransactionItemPricing.getTransactionItem();
            TransactionItem transactionItemNew = transactionItemPricing.getTransactionItem();
            ConfigProductPricing configProductPricingOld = persistentTransactionItemPricing.getConfigProductPricing();
            ConfigProductPricing configProductPricingNew = transactionItemPricing.getConfigProductPricing();
            if (transactionItemNew != null) {
                transactionItemNew = em.getReference(transactionItemNew.getClass(), transactionItemNew.getItemId());
                transactionItemPricing.setTransactionItem(transactionItemNew);
            }
            if (configProductPricingNew != null) {
                configProductPricingNew = em.getReference(configProductPricingNew.getClass(), configProductPricingNew.getId());
                transactionItemPricing.setConfigProductPricing(configProductPricingNew);
            }
            transactionItemPricing = em.merge(transactionItemPricing);
            if (transactionItemOld != null && !transactionItemOld.equals(transactionItemNew)) {
                transactionItemOld.getTransactionItemPricingCollection().remove(transactionItemPricing);
                transactionItemOld = em.merge(transactionItemOld);
            }
            if (transactionItemNew != null && !transactionItemNew.equals(transactionItemOld)) {
                transactionItemNew.getTransactionItemPricingCollection().add(transactionItemPricing);
                transactionItemNew = em.merge(transactionItemNew);
            }
            if (configProductPricingOld != null && !configProductPricingOld.equals(configProductPricingNew)) {
                configProductPricingOld.getTransactionItemPricingCollection().remove(transactionItemPricing);
                configProductPricingOld = em.merge(configProductPricingOld);
            }
            if (configProductPricingNew != null && !configProductPricingNew.equals(configProductPricingOld)) {
                configProductPricingNew.getTransactionItemPricingCollection().add(transactionItemPricing);
                configProductPricingNew = em.merge(configProductPricingNew);
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
                TransactionItemPricingPK id = transactionItemPricing.getTransactionItemPricingPK();
                if (findTransactionItemPricing(id) == null) {
                    throw new NonexistentEntityException("The transactionItemPricing with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TransactionItemPricingPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionItemPricing transactionItemPricing;
            try {
                transactionItemPricing = em.getReference(TransactionItemPricing.class, id);
                transactionItemPricing.getTransactionItemPricingPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionItemPricing with id " + id + " no longer exists.", enfe);
            }
            TransactionItem transactionItem = transactionItemPricing.getTransactionItem();
            if (transactionItem != null) {
                transactionItem.getTransactionItemPricingCollection().remove(transactionItemPricing);
                transactionItem = em.merge(transactionItem);
            }
            ConfigProductPricing configProductPricing = transactionItemPricing.getConfigProductPricing();
            if (configProductPricing != null) {
                configProductPricing.getTransactionItemPricingCollection().remove(transactionItemPricing);
                configProductPricing = em.merge(configProductPricing);
            }
            em.remove(transactionItemPricing);
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

    public List<TransactionItemPricing> findTransactionItemPricingEntities() {
        return findTransactionItemPricingEntities(true, -1, -1);
    }

    public List<TransactionItemPricing> findTransactionItemPricingEntities(int maxResults, int firstResult) {
        return findTransactionItemPricingEntities(false, maxResults, firstResult);
    }

    private List<TransactionItemPricing> findTransactionItemPricingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionItemPricing.class));
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

    public TransactionItemPricing findTransactionItemPricing(TransactionItemPricingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionItemPricing.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionItemPricingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionItemPricing> rt = cq.from(TransactionItemPricing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
