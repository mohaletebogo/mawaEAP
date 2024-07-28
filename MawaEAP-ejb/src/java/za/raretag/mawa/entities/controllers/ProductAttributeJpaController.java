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
import za.raretag.mawa.entities.ConfigProductAttribute;
import za.raretag.mawa.entities.ProductAttribute;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ProductAttributeJpaController implements Serializable {

    public ProductAttributeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductAttribute productAttribute) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product productCode = productAttribute.getProductCode();
            if (productCode != null) {
                productCode = em.getReference(productCode.getClass(), productCode.getProductCode());
                productAttribute.setProductCode(productCode);
            }
            ConfigProductAttribute attributeType = productAttribute.getAttributeType();
            if (attributeType != null) {
                attributeType = em.getReference(attributeType.getClass(), attributeType.getId());
                productAttribute.setAttributeType(attributeType);
            }
            em.persist(productAttribute);
            if (productCode != null) {
                productCode.getProductAttributeCollection().add(productAttribute);
                productCode = em.merge(productCode);
            }
            if (attributeType != null) {
                attributeType.getProductAttributeCollection().add(productAttribute);
                attributeType = em.merge(attributeType);
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

    public void edit(ProductAttribute productAttribute) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProductAttribute persistentProductAttribute = em.find(ProductAttribute.class, productAttribute.getAttibuteId());
            Product productCodeOld = persistentProductAttribute.getProductCode();
            Product productCodeNew = productAttribute.getProductCode();
            ConfigProductAttribute attributeTypeOld = persistentProductAttribute.getAttributeType();
            ConfigProductAttribute attributeTypeNew = productAttribute.getAttributeType();
            if (productCodeNew != null) {
                productCodeNew = em.getReference(productCodeNew.getClass(), productCodeNew.getProductCode());
                productAttribute.setProductCode(productCodeNew);
            }
            if (attributeTypeNew != null) {
                attributeTypeNew = em.getReference(attributeTypeNew.getClass(), attributeTypeNew.getId());
                productAttribute.setAttributeType(attributeTypeNew);
            }
            productAttribute = em.merge(productAttribute);
            if (productCodeOld != null && !productCodeOld.equals(productCodeNew)) {
                productCodeOld.getProductAttributeCollection().remove(productAttribute);
                productCodeOld = em.merge(productCodeOld);
            }
            if (productCodeNew != null && !productCodeNew.equals(productCodeOld)) {
                productCodeNew.getProductAttributeCollection().add(productAttribute);
                productCodeNew = em.merge(productCodeNew);
            }
            if (attributeTypeOld != null && !attributeTypeOld.equals(attributeTypeNew)) {
                attributeTypeOld.getProductAttributeCollection().remove(productAttribute);
                attributeTypeOld = em.merge(attributeTypeOld);
            }
            if (attributeTypeNew != null && !attributeTypeNew.equals(attributeTypeOld)) {
                attributeTypeNew.getProductAttributeCollection().add(productAttribute);
                attributeTypeNew = em.merge(attributeTypeNew);
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
                Integer id = productAttribute.getAttibuteId();
                if (findProductAttribute(id) == null) {
                    throw new NonexistentEntityException("The productAttribute with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ProductAttribute productAttribute;
            try {
                productAttribute = em.getReference(ProductAttribute.class, id);
                productAttribute.getAttibuteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productAttribute with id " + id + " no longer exists.", enfe);
            }
            Product productCode = productAttribute.getProductCode();
            if (productCode != null) {
                productCode.getProductAttributeCollection().remove(productAttribute);
                productCode = em.merge(productCode);
            }
            ConfigProductAttribute attributeType = productAttribute.getAttributeType();
            if (attributeType != null) {
                attributeType.getProductAttributeCollection().remove(productAttribute);
                attributeType = em.merge(attributeType);
            }
            em.remove(productAttribute);
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

    public List<ProductAttribute> findProductAttributeEntities() {
        return findProductAttributeEntities(true, -1, -1);
    }

    public List<ProductAttribute> findProductAttributeEntities(int maxResults, int firstResult) {
        return findProductAttributeEntities(false, maxResults, firstResult);
    }

    private List<ProductAttribute> findProductAttributeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductAttribute.class));
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

    public ProductAttribute findProductAttribute(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductAttribute.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductAttributeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductAttribute> rt = cq.from(ProductAttribute.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
