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
import za.raretag.mawa.entities.PartnerAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigPartnerAttributes;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigPartnerAttributesJpaController implements Serializable {

    public ConfigPartnerAttributesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigPartnerAttributes configPartnerAttributes) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configPartnerAttributes.getPartnerAttributeCollection() == null) {
            configPartnerAttributes.setPartnerAttributeCollection(new ArrayList<PartnerAttribute>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PartnerAttribute> attachedPartnerAttributeCollection = new ArrayList<PartnerAttribute>();
            for (PartnerAttribute partnerAttributeCollectionPartnerAttributeToAttach : configPartnerAttributes.getPartnerAttributeCollection()) {
                partnerAttributeCollectionPartnerAttributeToAttach = em.getReference(partnerAttributeCollectionPartnerAttributeToAttach.getClass(), partnerAttributeCollectionPartnerAttributeToAttach.getPartnerAttributePK());
                attachedPartnerAttributeCollection.add(partnerAttributeCollectionPartnerAttributeToAttach);
            }
            configPartnerAttributes.setPartnerAttributeCollection(attachedPartnerAttributeCollection);
            em.persist(configPartnerAttributes);
            for (PartnerAttribute partnerAttributeCollectionPartnerAttribute : configPartnerAttributes.getPartnerAttributeCollection()) {
                ConfigPartnerAttributes oldConfigPartnerAttributesOfPartnerAttributeCollectionPartnerAttribute = partnerAttributeCollectionPartnerAttribute.getConfigPartnerAttributes();
                partnerAttributeCollectionPartnerAttribute.setConfigPartnerAttributes(configPartnerAttributes);
                partnerAttributeCollectionPartnerAttribute = em.merge(partnerAttributeCollectionPartnerAttribute);
                if (oldConfigPartnerAttributesOfPartnerAttributeCollectionPartnerAttribute != null) {
                    oldConfigPartnerAttributesOfPartnerAttributeCollectionPartnerAttribute.getPartnerAttributeCollection().remove(partnerAttributeCollectionPartnerAttribute);
                    oldConfigPartnerAttributesOfPartnerAttributeCollectionPartnerAttribute = em.merge(oldConfigPartnerAttributesOfPartnerAttributeCollectionPartnerAttribute);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigPartnerAttributes(configPartnerAttributes.getId()) != null) {
                throw new PreexistingEntityException("ConfigPartnerAttributes " + configPartnerAttributes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigPartnerAttributes configPartnerAttributes) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigPartnerAttributes persistentConfigPartnerAttributes = em.find(ConfigPartnerAttributes.class, configPartnerAttributes.getId());
            Collection<PartnerAttribute> partnerAttributeCollectionOld = persistentConfigPartnerAttributes.getPartnerAttributeCollection();
            Collection<PartnerAttribute> partnerAttributeCollectionNew = configPartnerAttributes.getPartnerAttributeCollection();
            List<String> illegalOrphanMessages = null;
            for (PartnerAttribute partnerAttributeCollectionOldPartnerAttribute : partnerAttributeCollectionOld) {
                if (!partnerAttributeCollectionNew.contains(partnerAttributeCollectionOldPartnerAttribute)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PartnerAttribute " + partnerAttributeCollectionOldPartnerAttribute + " since its configPartnerAttributes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PartnerAttribute> attachedPartnerAttributeCollectionNew = new ArrayList<PartnerAttribute>();
            for (PartnerAttribute partnerAttributeCollectionNewPartnerAttributeToAttach : partnerAttributeCollectionNew) {
                partnerAttributeCollectionNewPartnerAttributeToAttach = em.getReference(partnerAttributeCollectionNewPartnerAttributeToAttach.getClass(), partnerAttributeCollectionNewPartnerAttributeToAttach.getPartnerAttributePK());
                attachedPartnerAttributeCollectionNew.add(partnerAttributeCollectionNewPartnerAttributeToAttach);
            }
            partnerAttributeCollectionNew = attachedPartnerAttributeCollectionNew;
            configPartnerAttributes.setPartnerAttributeCollection(partnerAttributeCollectionNew);
            configPartnerAttributes = em.merge(configPartnerAttributes);
            for (PartnerAttribute partnerAttributeCollectionNewPartnerAttribute : partnerAttributeCollectionNew) {
                if (!partnerAttributeCollectionOld.contains(partnerAttributeCollectionNewPartnerAttribute)) {
                    ConfigPartnerAttributes oldConfigPartnerAttributesOfPartnerAttributeCollectionNewPartnerAttribute = partnerAttributeCollectionNewPartnerAttribute.getConfigPartnerAttributes();
                    partnerAttributeCollectionNewPartnerAttribute.setConfigPartnerAttributes(configPartnerAttributes);
                    partnerAttributeCollectionNewPartnerAttribute = em.merge(partnerAttributeCollectionNewPartnerAttribute);
                    if (oldConfigPartnerAttributesOfPartnerAttributeCollectionNewPartnerAttribute != null && !oldConfigPartnerAttributesOfPartnerAttributeCollectionNewPartnerAttribute.equals(configPartnerAttributes)) {
                        oldConfigPartnerAttributesOfPartnerAttributeCollectionNewPartnerAttribute.getPartnerAttributeCollection().remove(partnerAttributeCollectionNewPartnerAttribute);
                        oldConfigPartnerAttributesOfPartnerAttributeCollectionNewPartnerAttribute = em.merge(oldConfigPartnerAttributesOfPartnerAttributeCollectionNewPartnerAttribute);
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
                String id = configPartnerAttributes.getId();
                if (findConfigPartnerAttributes(id) == null) {
                    throw new NonexistentEntityException("The configPartnerAttributes with id " + id + " no longer exists.");
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
            ConfigPartnerAttributes configPartnerAttributes;
            try {
                configPartnerAttributes = em.getReference(ConfigPartnerAttributes.class, id);
                configPartnerAttributes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configPartnerAttributes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PartnerAttribute> partnerAttributeCollectionOrphanCheck = configPartnerAttributes.getPartnerAttributeCollection();
            for (PartnerAttribute partnerAttributeCollectionOrphanCheckPartnerAttribute : partnerAttributeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigPartnerAttributes (" + configPartnerAttributes + ") cannot be destroyed since the PartnerAttribute " + partnerAttributeCollectionOrphanCheckPartnerAttribute + " in its partnerAttributeCollection field has a non-nullable configPartnerAttributes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configPartnerAttributes);
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

    public List<ConfigPartnerAttributes> findConfigPartnerAttributesEntities() {
        return findConfigPartnerAttributesEntities(true, -1, -1);
    }

    public List<ConfigPartnerAttributes> findConfigPartnerAttributesEntities(int maxResults, int firstResult) {
        return findConfigPartnerAttributesEntities(false, maxResults, firstResult);
    }

    private List<ConfigPartnerAttributes> findConfigPartnerAttributesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigPartnerAttributes.class));
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

    public ConfigPartnerAttributes findConfigPartnerAttributes(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigPartnerAttributes.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigPartnerAttributesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigPartnerAttributes> rt = cq.from(ConfigPartnerAttributes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
