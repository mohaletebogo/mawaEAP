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
import za.raretag.mawa.entities.Product;
import za.raretag.mawa.entities.TransactionItem;
import za.raretag.mawa.entities.ProductPricing;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (product.getProductAttributeCollection() == null) {
            product.setProductAttributeCollection(new ArrayList<ProductAttribute>());
        }
        if (product.getTransactionItemCollection() == null) {
            product.setTransactionItemCollection(new ArrayList<TransactionItem>());
        }
        if (product.getProductPricingCollection() == null) {
            product.setProductPricingCollection(new ArrayList<ProductPricing>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<ProductAttribute> attachedProductAttributeCollection = new ArrayList<ProductAttribute>();
            for (ProductAttribute productAttributeCollectionProductAttributeToAttach : product.getProductAttributeCollection()) {
                productAttributeCollectionProductAttributeToAttach = em.getReference(productAttributeCollectionProductAttributeToAttach.getClass(), productAttributeCollectionProductAttributeToAttach.getAttibuteId());
                attachedProductAttributeCollection.add(productAttributeCollectionProductAttributeToAttach);
            }
            product.setProductAttributeCollection(attachedProductAttributeCollection);
            Collection<TransactionItem> attachedTransactionItemCollection = new ArrayList<TransactionItem>();
            for (TransactionItem transactionItemCollectionTransactionItemToAttach : product.getTransactionItemCollection()) {
                transactionItemCollectionTransactionItemToAttach = em.getReference(transactionItemCollectionTransactionItemToAttach.getClass(), transactionItemCollectionTransactionItemToAttach.getItemId());
                attachedTransactionItemCollection.add(transactionItemCollectionTransactionItemToAttach);
            }
            product.setTransactionItemCollection(attachedTransactionItemCollection);
            Collection<ProductPricing> attachedProductPricingCollection = new ArrayList<ProductPricing>();
            for (ProductPricing productPricingCollectionProductPricingToAttach : product.getProductPricingCollection()) {
                productPricingCollectionProductPricingToAttach = em.getReference(productPricingCollectionProductPricingToAttach.getClass(), productPricingCollectionProductPricingToAttach.getProductPricingPK());
                attachedProductPricingCollection.add(productPricingCollectionProductPricingToAttach);
            }
            product.setProductPricingCollection(attachedProductPricingCollection);
            em.persist(product);
            for (ProductAttribute productAttributeCollectionProductAttribute : product.getProductAttributeCollection()) {
                Product oldProductCodeOfProductAttributeCollectionProductAttribute = productAttributeCollectionProductAttribute.getProductCode();
                productAttributeCollectionProductAttribute.setProductCode(product);
                productAttributeCollectionProductAttribute = em.merge(productAttributeCollectionProductAttribute);
                if (oldProductCodeOfProductAttributeCollectionProductAttribute != null) {
                    oldProductCodeOfProductAttributeCollectionProductAttribute.getProductAttributeCollection().remove(productAttributeCollectionProductAttribute);
                    oldProductCodeOfProductAttributeCollectionProductAttribute = em.merge(oldProductCodeOfProductAttributeCollectionProductAttribute);
                }
            }
            for (TransactionItem transactionItemCollectionTransactionItem : product.getTransactionItemCollection()) {
                Product oldProductIdOfTransactionItemCollectionTransactionItem = transactionItemCollectionTransactionItem.getProductId();
                transactionItemCollectionTransactionItem.setProductId(product);
                transactionItemCollectionTransactionItem = em.merge(transactionItemCollectionTransactionItem);
                if (oldProductIdOfTransactionItemCollectionTransactionItem != null) {
                    oldProductIdOfTransactionItemCollectionTransactionItem.getTransactionItemCollection().remove(transactionItemCollectionTransactionItem);
                    oldProductIdOfTransactionItemCollectionTransactionItem = em.merge(oldProductIdOfTransactionItemCollectionTransactionItem);
                }
            }
            for (ProductPricing productPricingCollectionProductPricing : product.getProductPricingCollection()) {
                Product oldProductOfProductPricingCollectionProductPricing = productPricingCollectionProductPricing.getProduct();
                productPricingCollectionProductPricing.setProduct(product);
                productPricingCollectionProductPricing = em.merge(productPricingCollectionProductPricing);
                if (oldProductOfProductPricingCollectionProductPricing != null) {
                    oldProductOfProductPricingCollectionProductPricing.getProductPricingCollection().remove(productPricingCollectionProductPricing);
                    oldProductOfProductPricingCollectionProductPricing = em.merge(oldProductOfProductPricingCollectionProductPricing);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProduct(product.getProductCode()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product persistentProduct = em.find(Product.class, product.getProductCode());
            Collection<ProductAttribute> productAttributeCollectionOld = persistentProduct.getProductAttributeCollection();
            Collection<ProductAttribute> productAttributeCollectionNew = product.getProductAttributeCollection();
            Collection<TransactionItem> transactionItemCollectionOld = persistentProduct.getTransactionItemCollection();
            Collection<TransactionItem> transactionItemCollectionNew = product.getTransactionItemCollection();
            Collection<ProductPricing> productPricingCollectionOld = persistentProduct.getProductPricingCollection();
            Collection<ProductPricing> productPricingCollectionNew = product.getProductPricingCollection();
            List<String> illegalOrphanMessages = null;
            for (ProductPricing productPricingCollectionOldProductPricing : productPricingCollectionOld) {
                if (!productPricingCollectionNew.contains(productPricingCollectionOldProductPricing)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductPricing " + productPricingCollectionOldProductPricing + " since its product field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ProductAttribute> attachedProductAttributeCollectionNew = new ArrayList<ProductAttribute>();
            for (ProductAttribute productAttributeCollectionNewProductAttributeToAttach : productAttributeCollectionNew) {
                productAttributeCollectionNewProductAttributeToAttach = em.getReference(productAttributeCollectionNewProductAttributeToAttach.getClass(), productAttributeCollectionNewProductAttributeToAttach.getAttibuteId());
                attachedProductAttributeCollectionNew.add(productAttributeCollectionNewProductAttributeToAttach);
            }
            productAttributeCollectionNew = attachedProductAttributeCollectionNew;
            product.setProductAttributeCollection(productAttributeCollectionNew);
            Collection<TransactionItem> attachedTransactionItemCollectionNew = new ArrayList<TransactionItem>();
            for (TransactionItem transactionItemCollectionNewTransactionItemToAttach : transactionItemCollectionNew) {
                transactionItemCollectionNewTransactionItemToAttach = em.getReference(transactionItemCollectionNewTransactionItemToAttach.getClass(), transactionItemCollectionNewTransactionItemToAttach.getItemId());
                attachedTransactionItemCollectionNew.add(transactionItemCollectionNewTransactionItemToAttach);
            }
            transactionItemCollectionNew = attachedTransactionItemCollectionNew;
            product.setTransactionItemCollection(transactionItemCollectionNew);
            Collection<ProductPricing> attachedProductPricingCollectionNew = new ArrayList<ProductPricing>();
            for (ProductPricing productPricingCollectionNewProductPricingToAttach : productPricingCollectionNew) {
                productPricingCollectionNewProductPricingToAttach = em.getReference(productPricingCollectionNewProductPricingToAttach.getClass(), productPricingCollectionNewProductPricingToAttach.getProductPricingPK());
                attachedProductPricingCollectionNew.add(productPricingCollectionNewProductPricingToAttach);
            }
            productPricingCollectionNew = attachedProductPricingCollectionNew;
            product.setProductPricingCollection(productPricingCollectionNew);
            product = em.merge(product);
            for (ProductAttribute productAttributeCollectionOldProductAttribute : productAttributeCollectionOld) {
                if (!productAttributeCollectionNew.contains(productAttributeCollectionOldProductAttribute)) {
                    productAttributeCollectionOldProductAttribute.setProductCode(null);
                    productAttributeCollectionOldProductAttribute = em.merge(productAttributeCollectionOldProductAttribute);
                }
            }
            for (ProductAttribute productAttributeCollectionNewProductAttribute : productAttributeCollectionNew) {
                if (!productAttributeCollectionOld.contains(productAttributeCollectionNewProductAttribute)) {
                    Product oldProductCodeOfProductAttributeCollectionNewProductAttribute = productAttributeCollectionNewProductAttribute.getProductCode();
                    productAttributeCollectionNewProductAttribute.setProductCode(product);
                    productAttributeCollectionNewProductAttribute = em.merge(productAttributeCollectionNewProductAttribute);
                    if (oldProductCodeOfProductAttributeCollectionNewProductAttribute != null && !oldProductCodeOfProductAttributeCollectionNewProductAttribute.equals(product)) {
                        oldProductCodeOfProductAttributeCollectionNewProductAttribute.getProductAttributeCollection().remove(productAttributeCollectionNewProductAttribute);
                        oldProductCodeOfProductAttributeCollectionNewProductAttribute = em.merge(oldProductCodeOfProductAttributeCollectionNewProductAttribute);
                    }
                }
            }
            for (TransactionItem transactionItemCollectionOldTransactionItem : transactionItemCollectionOld) {
                if (!transactionItemCollectionNew.contains(transactionItemCollectionOldTransactionItem)) {
                    transactionItemCollectionOldTransactionItem.setProductId(null);
                    transactionItemCollectionOldTransactionItem = em.merge(transactionItemCollectionOldTransactionItem);
                }
            }
            for (TransactionItem transactionItemCollectionNewTransactionItem : transactionItemCollectionNew) {
                if (!transactionItemCollectionOld.contains(transactionItemCollectionNewTransactionItem)) {
                    Product oldProductIdOfTransactionItemCollectionNewTransactionItem = transactionItemCollectionNewTransactionItem.getProductId();
                    transactionItemCollectionNewTransactionItem.setProductId(product);
                    transactionItemCollectionNewTransactionItem = em.merge(transactionItemCollectionNewTransactionItem);
                    if (oldProductIdOfTransactionItemCollectionNewTransactionItem != null && !oldProductIdOfTransactionItemCollectionNewTransactionItem.equals(product)) {
                        oldProductIdOfTransactionItemCollectionNewTransactionItem.getTransactionItemCollection().remove(transactionItemCollectionNewTransactionItem);
                        oldProductIdOfTransactionItemCollectionNewTransactionItem = em.merge(oldProductIdOfTransactionItemCollectionNewTransactionItem);
                    }
                }
            }
            for (ProductPricing productPricingCollectionNewProductPricing : productPricingCollectionNew) {
                if (!productPricingCollectionOld.contains(productPricingCollectionNewProductPricing)) {
                    Product oldProductOfProductPricingCollectionNewProductPricing = productPricingCollectionNewProductPricing.getProduct();
                    productPricingCollectionNewProductPricing.setProduct(product);
                    productPricingCollectionNewProductPricing = em.merge(productPricingCollectionNewProductPricing);
                    if (oldProductOfProductPricingCollectionNewProductPricing != null && !oldProductOfProductPricingCollectionNewProductPricing.equals(product)) {
                        oldProductOfProductPricingCollectionNewProductPricing.getProductPricingCollection().remove(productPricingCollectionNewProductPricing);
                        oldProductOfProductPricingCollectionNewProductPricing = em.merge(oldProductOfProductPricingCollectionNewProductPricing);
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
                String id = product.getProductCode();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProductPricing> productPricingCollectionOrphanCheck = product.getProductPricingCollection();
            for (ProductPricing productPricingCollectionOrphanCheckProductPricing : productPricingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the ProductPricing " + productPricingCollectionOrphanCheckProductPricing + " in its productPricingCollection field has a non-nullable product field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ProductAttribute> productAttributeCollection = product.getProductAttributeCollection();
            for (ProductAttribute productAttributeCollectionProductAttribute : productAttributeCollection) {
                productAttributeCollectionProductAttribute.setProductCode(null);
                productAttributeCollectionProductAttribute = em.merge(productAttributeCollectionProductAttribute);
            }
            Collection<TransactionItem> transactionItemCollection = product.getTransactionItemCollection();
            for (TransactionItem transactionItemCollectionTransactionItem : transactionItemCollection) {
                transactionItemCollectionTransactionItem.setProductId(null);
                transactionItemCollectionTransactionItem = em.merge(transactionItemCollectionTransactionItem);
            }
            em.remove(product);
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

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
