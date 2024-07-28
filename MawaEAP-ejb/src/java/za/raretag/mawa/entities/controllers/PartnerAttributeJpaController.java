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
import za.raretag.mawa.entities.ConfigPartnerAttributes;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerAttribute;
import za.raretag.mawa.entities.PartnerAttributePK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class PartnerAttributeJpaController implements Serializable {

    public PartnerAttributeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PartnerAttribute partnerAttribute) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (partnerAttribute.getPartnerAttributePK() == null) {
            partnerAttribute.setPartnerAttributePK(new PartnerAttributePK());
        }
        partnerAttribute.getPartnerAttributePK().setPartnerNo(partnerAttribute.getPartner().getPartnerNo());
        partnerAttribute.getPartnerAttributePK().setAttribute(partnerAttribute.getConfigPartnerAttributes().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigPartnerAttributes configPartnerAttributes = partnerAttribute.getConfigPartnerAttributes();
            if (configPartnerAttributes != null) {
                configPartnerAttributes = em.getReference(configPartnerAttributes.getClass(), configPartnerAttributes.getId());
                partnerAttribute.setConfigPartnerAttributes(configPartnerAttributes);
            }
            Partner partner = partnerAttribute.getPartner();
            if (partner != null) {
                partner = em.getReference(partner.getClass(), partner.getPartnerNo());
                partnerAttribute.setPartner(partner);
            }
            em.persist(partnerAttribute);
            if (configPartnerAttributes != null) {
                configPartnerAttributes.getPartnerAttributeCollection().add(partnerAttribute);
                configPartnerAttributes = em.merge(configPartnerAttributes);
            }
            if (partner != null) {
                partner.getPartnerAttributeCollection().add(partnerAttribute);
                partner = em.merge(partner);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPartnerAttribute(partnerAttribute.getPartnerAttributePK()) != null) {
                throw new PreexistingEntityException("PartnerAttribute " + partnerAttribute + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PartnerAttribute partnerAttribute) throws NonexistentEntityException, RollbackFailureException, Exception {
        partnerAttribute.getPartnerAttributePK().setPartnerNo(partnerAttribute.getPartner().getPartnerNo());
        partnerAttribute.getPartnerAttributePK().setAttribute(partnerAttribute.getConfigPartnerAttributes().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerAttribute persistentPartnerAttribute = em.find(PartnerAttribute.class, partnerAttribute.getPartnerAttributePK());
            ConfigPartnerAttributes configPartnerAttributesOld = persistentPartnerAttribute.getConfigPartnerAttributes();
            ConfigPartnerAttributes configPartnerAttributesNew = partnerAttribute.getConfigPartnerAttributes();
            Partner partnerOld = persistentPartnerAttribute.getPartner();
            Partner partnerNew = partnerAttribute.getPartner();
            if (configPartnerAttributesNew != null) {
                configPartnerAttributesNew = em.getReference(configPartnerAttributesNew.getClass(), configPartnerAttributesNew.getId());
                partnerAttribute.setConfigPartnerAttributes(configPartnerAttributesNew);
            }
            if (partnerNew != null) {
                partnerNew = em.getReference(partnerNew.getClass(), partnerNew.getPartnerNo());
                partnerAttribute.setPartner(partnerNew);
            }
            partnerAttribute = em.merge(partnerAttribute);
            if (configPartnerAttributesOld != null && !configPartnerAttributesOld.equals(configPartnerAttributesNew)) {
                configPartnerAttributesOld.getPartnerAttributeCollection().remove(partnerAttribute);
                configPartnerAttributesOld = em.merge(configPartnerAttributesOld);
            }
            if (configPartnerAttributesNew != null && !configPartnerAttributesNew.equals(configPartnerAttributesOld)) {
                configPartnerAttributesNew.getPartnerAttributeCollection().add(partnerAttribute);
                configPartnerAttributesNew = em.merge(configPartnerAttributesNew);
            }
            if (partnerOld != null && !partnerOld.equals(partnerNew)) {
                partnerOld.getPartnerAttributeCollection().remove(partnerAttribute);
                partnerOld = em.merge(partnerOld);
            }
            if (partnerNew != null && !partnerNew.equals(partnerOld)) {
                partnerNew.getPartnerAttributeCollection().add(partnerAttribute);
                partnerNew = em.merge(partnerNew);
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
                PartnerAttributePK id = partnerAttribute.getPartnerAttributePK();
                if (findPartnerAttribute(id) == null) {
                    throw new NonexistentEntityException("The partnerAttribute with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PartnerAttributePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerAttribute partnerAttribute;
            try {
                partnerAttribute = em.getReference(PartnerAttribute.class, id);
                partnerAttribute.getPartnerAttributePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partnerAttribute with id " + id + " no longer exists.", enfe);
            }
            ConfigPartnerAttributes configPartnerAttributes = partnerAttribute.getConfigPartnerAttributes();
            if (configPartnerAttributes != null) {
                configPartnerAttributes.getPartnerAttributeCollection().remove(partnerAttribute);
                configPartnerAttributes = em.merge(configPartnerAttributes);
            }
            Partner partner = partnerAttribute.getPartner();
            if (partner != null) {
                partner.getPartnerAttributeCollection().remove(partnerAttribute);
                partner = em.merge(partner);
            }
            em.remove(partnerAttribute);
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

    public List<PartnerAttribute> findPartnerAttributeEntities() {
        return findPartnerAttributeEntities(true, -1, -1);
    }

    public List<PartnerAttribute> findPartnerAttributeEntities(int maxResults, int firstResult) {
        return findPartnerAttributeEntities(false, maxResults, firstResult);
    }

    private List<PartnerAttribute> findPartnerAttributeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PartnerAttribute.class));
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

    public PartnerAttribute findPartnerAttribute(PartnerAttributePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PartnerAttribute.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartnerAttributeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PartnerAttribute> rt = cq.from(PartnerAttribute.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
