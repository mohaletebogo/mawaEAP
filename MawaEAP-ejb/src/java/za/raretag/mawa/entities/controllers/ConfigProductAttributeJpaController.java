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
import za.raretag.mawa.entities.ProductAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigProductAttribute;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigProductAttributeJpaController implements Serializable {

    public ConfigProductAttributeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigProductAttribute configProductAttribute) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configProductAttribute.getProductAttributeCollection() == null) {
            configProductAttribute.setProductAttributeCollection(new ArrayList<ProductAttribute>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<ProductAttribute> attachedProductAttributeCollection = new ArrayList<ProductAttribute>();
            for (ProductAttribute productAttributeCollectionProductAttributeToAttach : configProductAttribute.getProductAttributeCollection()) {
                productAttributeCollectionProductAttributeToAttach = em.getReference(productAttributeCollectionProductAttributeToAttach.getClass(), productAttributeCollectionProductAttributeToAttach.getAttibuteId());
                attachedProductAttributeCollection.add(productAttributeCollectionProductAttributeToAttach);
            }
            configProductAttribute.setProductAttributeCollection(attachedProductAttributeCollection);
            em.persist(configProductAttribute);
            for (ProductAttribute productAttributeCollectionProductAttribute : configProductAttribute.getProductAttributeCollection()) {
                ConfigProductAttribute oldAttributeTypeOfProductAttributeCollectionProductAttribute = productAttributeCollectionProductAttribute.getAttributeType();
                productAttributeCollectionProductAttribute.setAttributeType(configProductAttribute);
                productAttributeCollectionProductAttribute = em.merge(productAttributeCollectionProductAttribute);
                if (oldAttributeTypeOfProductAttributeCollectionProductAttribute != null) {
                    oldAttributeTypeOfProductAttributeCollectionProductAttribute.getProductAttributeCollection().remove(productAttributeCollectionProductAttribute);
                    oldAttributeTypeOfProductAttributeCollectionProductAttribute = em.merge(oldAttributeTypeOfProductAttributeCollectionProductAttribute);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigProductAttribute(configProductAttribute.getId()) != null) {
                throw new PreexistingEntityException("ConfigProductAttribute " + configProductAttribute + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigProductAttribute configProductAttribute) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigProductAttribute persistentConfigProductAttribute = em.find(ConfigProductAttribute.class, configProductAttribute.getId());
            Collection<ProductAttribute> productAttributeCollectionOld = persistentConfigProductAttribute.getProductAttributeCollection();
            Collection<ProductAttribute> productAttributeCollectionNew = configProductAttribute.getProductAttributeCollection();
            Collection<ProductAttribute> attachedProductAttributeCollectionNew = new ArrayList<ProductAttribute>();
            for (ProductAttribute productAttributeCollectionNewProductAttributeToAttach : productAttributeCollectionNew) {
                productAttributeCollectionNewProductAttributeToAttach = em.getReference(productAttributeCollectionNewProductAttributeToAttach.getClass(), productAttributeCollectionNewProductAttributeToAttach.getAttibuteId());
                attachedProductAttributeCollectionNew.add(productAttributeCollectionNewProductAttributeToAttach);
            }
            productAttributeCollectionNew = attachedProductAttributeCollectionNew;
            configProductAttribute.setProductAttributeCollection(productAttributeCollectionNew);
            configProductAttribute = em.merge(configProductAttribute);
            for (ProductAttribute productAttributeCollectionOldProductAttribute : productAttributeCollectionOld) {
                if (!productAttributeCollectionNew.contains(productAttributeCollectionOldProductAttribute)) {
                    productAttributeCollectionOldProductAttribute.setAttributeType(null);
                    productAttributeCollectionOldProductAttribute = em.merge(productAttributeCollectionOldProductAttribute);
                }
            }
            for (ProductAttribute productAttributeCollectionNewProductAttribute : productAttributeCollectionNew) {
                if (!productAttributeCollectionOld.contains(productAttributeCollectionNewProductAttribute)) {
                    ConfigProductAttribute oldAttributeTypeOfProductAttributeCollectionNewProductAttribute = productAttributeCollectionNewProductAttribute.getAttributeType();
                    productAttributeCollectionNewProductAttribute.setAttributeType(configProductAttribute);
                    productAttributeCollectionNewProductAttribute = em.merge(productAttributeCollectionNewProductAttribute);
                    if (oldAttributeTypeOfProductAttributeCollectionNewProductAttribute != null && !oldAttributeTypeOfProductAttributeCollectionNewProductAttribute.equals(configProductAttribute)) {
                        oldAttributeTypeOfProductAttributeCollectionNewProductAttribute.getProductAttributeCollection().remove(productAttributeCollectionNewProductAttribute);
                        oldAttributeTypeOfProductAttributeCollectionNewProductAttribute = em.merge(oldAttributeTypeOfProductAttributeCollectionNewProductAttribute);
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
                String id = configProductAttribute.getId();
                if (findConfigProductAttribute(id) == null) {
                    throw new NonexistentEntityException("The configProductAttribute with id " + id + " no longer exists.");
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
            ConfigProductAttribute configProductAttribute;
            try {
                configProductAttribute = em.getReference(ConfigProductAttribute.class, id);
                configProductAttribute.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configProductAttribute with id " + id + " no longer exists.", enfe);
            }
            Collection<ProductAttribute> productAttributeCollection = configProductAttribute.getProductAttributeCollection();
            for (ProductAttribute productAttributeCollectionProductAttribute : productAttributeCollection) {
                productAttributeCollectionProductAttribute.setAttributeType(null);
                productAttributeCollectionProductAttribute = em.merge(productAttributeCollectionProductAttribute);
            }
            em.remove(configProductAttribute);
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

    public List<ConfigProductAttribute> findConfigProductAttributeEntities() {
        return findConfigProductAttributeEntities(true, -1, -1);
    }

    public List<ConfigProductAttribute> findConfigProductAttributeEntities(int maxResults, int firstResult) {
        return findConfigProductAttributeEntities(false, maxResults, firstResult);
    }

    private List<ConfigProductAttribute> findConfigProductAttributeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigProductAttribute.class));
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

    public ConfigProductAttribute findConfigProductAttribute(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigProductAttribute.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigProductAttributeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigProductAttribute> rt = cq.from(ConfigProductAttribute.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
