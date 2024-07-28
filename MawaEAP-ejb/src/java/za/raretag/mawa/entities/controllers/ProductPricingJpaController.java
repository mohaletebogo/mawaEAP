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
import za.raretag.mawa.entities.Product;
import za.raretag.mawa.entities.ConfigProductPricing;
import za.raretag.mawa.entities.ProductPricing;
import za.raretag.mawa.entities.ProductPricingPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ProductPricingJpaController implements Serializable {

    public ProductPricingJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductPricing productPricing) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (productPricing.getProductPricingPK() == null) {
            productPricing.setProductPricingPK(new ProductPricingPK());
        }
        productPricing.getProductPricingPK().setPricingType(productPricing.getConfigProductPricing().getId());
        productPricing.getProductPricingPK().setProductCode(productPricing.getProduct().getProductCode());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product product = productPricing.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProductCode());
                productPricing.setProduct(product);
            }
            ConfigProductPricing configProductPricing = productPricing.getConfigProductPricing();
            if (configProductPricing != null) {
                configProductPricing = em.getReference(configProductPricing.getClass(), configProductPricing.getId());
                productPricing.setConfigProductPricing(configProductPricing);
            }
            em.persist(productPricing);
            if (product != null) {
                product.getProductPricingCollection().add(productPricing);
                product = em.merge(product);
            }
            if (configProductPricing != null) {
                configProductPricing.getProductPricingCollection().add(productPricing);
                configProductPricing = em.merge(configProductPricing);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProductPricing(productPricing.getProductPricingPK()) != null) {
                throw new PreexistingEntityException("ProductPricing " + productPricing + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductPricing productPricing) throws NonexistentEntityException, RollbackFailureException, Exception {
        productPricing.getProductPricingPK().setPricingType(productPricing.getConfigProductPricing().getId());
        productPricing.getProductPricingPK().setProductCode(productPricing.getProduct().getProductCode());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProductPricing persistentProductPricing = em.find(ProductPricing.class, productPricing.getProductPricingPK());
            Product productOld = persistentProductPricing.getProduct();
            Product productNew = productPricing.getProduct();
            ConfigProductPricing configProductPricingOld = persistentProductPricing.getConfigProductPricing();
            ConfigProductPricing configProductPricingNew = productPricing.getConfigProductPricing();
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProductCode());
                productPricing.setProduct(productNew);
            }
            if (configProductPricingNew != null) {
                configProductPricingNew = em.getReference(configProductPricingNew.getClass(), configProductPricingNew.getId());
                productPricing.setConfigProductPricing(configProductPricingNew);
            }
            productPricing = em.merge(productPricing);
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getProductPricingCollection().remove(productPricing);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getProductPricingCollection().add(productPricing);
                productNew = em.merge(productNew);
            }
            if (configProductPricingOld != null && !configProductPricingOld.equals(configProductPricingNew)) {
                configProductPricingOld.getProductPricingCollection().remove(productPricing);
                configProductPricingOld = em.merge(configProductPricingOld);
            }
            if (configProductPricingNew != null && !configProductPricingNew.equals(configProductPricingOld)) {
                configProductPricingNew.getProductPricingCollection().add(productPricing);
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
                ProductPricingPK id = productPricing.getProductPricingPK();
                if (findProductPricing(id) == null) {
                    throw new NonexistentEntityException("The productPricing with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProductPricingPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProductPricing productPricing;
            try {
                productPricing = em.getReference(ProductPricing.class, id);
                productPricing.getProductPricingPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productPricing with id " + id + " no longer exists.", enfe);
            }
            Product product = productPricing.getProduct();
            if (product != null) {
                product.getProductPricingCollection().remove(productPricing);
                product = em.merge(product);
            }
            ConfigProductPricing configProductPricing = productPricing.getConfigProductPricing();
            if (configProductPricing != null) {
                configProductPricing.getProductPricingCollection().remove(productPricing);
                configProductPricing = em.merge(configProductPricing);
            }
            em.remove(productPricing);
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

    public List<ProductPricing> findProductPricingEntities() {
        return findProductPricingEntities(true, -1, -1);
    }

    public List<ProductPricing> findProductPricingEntities(int maxResults, int firstResult) {
        return findProductPricingEntities(false, maxResults, firstResult);
    }

    private List<ProductPricing> findProductPricingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductPricing.class));
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

    public ProductPricing findProductPricing(ProductPricingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductPricing.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductPricingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductPricing> rt = cq.from(ProductPricing.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
