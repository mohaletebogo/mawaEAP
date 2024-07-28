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
import za.raretag.mawa.entities.PartnerContact;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigContactType;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigContactTypeJpaController implements Serializable {

    public ConfigContactTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigContactType configContactType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configContactType.getPartnerContactCollection() == null) {
            configContactType.setPartnerContactCollection(new ArrayList<PartnerContact>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PartnerContact> attachedPartnerContactCollection = new ArrayList<PartnerContact>();
            for (PartnerContact partnerContactCollectionPartnerContactToAttach : configContactType.getPartnerContactCollection()) {
                partnerContactCollectionPartnerContactToAttach = em.getReference(partnerContactCollectionPartnerContactToAttach.getClass(), partnerContactCollectionPartnerContactToAttach.getContactId());
                attachedPartnerContactCollection.add(partnerContactCollectionPartnerContactToAttach);
            }
            configContactType.setPartnerContactCollection(attachedPartnerContactCollection);
            em.persist(configContactType);
            for (PartnerContact partnerContactCollectionPartnerContact : configContactType.getPartnerContactCollection()) {
                ConfigContactType oldTypeOfPartnerContactCollectionPartnerContact = partnerContactCollectionPartnerContact.getType();
                partnerContactCollectionPartnerContact.setType(configContactType);
                partnerContactCollectionPartnerContact = em.merge(partnerContactCollectionPartnerContact);
                if (oldTypeOfPartnerContactCollectionPartnerContact != null) {
                    oldTypeOfPartnerContactCollectionPartnerContact.getPartnerContactCollection().remove(partnerContactCollectionPartnerContact);
                    oldTypeOfPartnerContactCollectionPartnerContact = em.merge(oldTypeOfPartnerContactCollectionPartnerContact);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigContactType(configContactType.getId()) != null) {
                throw new PreexistingEntityException("ConfigContactType " + configContactType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigContactType configContactType) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigContactType persistentConfigContactType = em.find(ConfigContactType.class, configContactType.getId());
            Collection<PartnerContact> partnerContactCollectionOld = persistentConfigContactType.getPartnerContactCollection();
            Collection<PartnerContact> partnerContactCollectionNew = configContactType.getPartnerContactCollection();
            Collection<PartnerContact> attachedPartnerContactCollectionNew = new ArrayList<PartnerContact>();
            for (PartnerContact partnerContactCollectionNewPartnerContactToAttach : partnerContactCollectionNew) {
                partnerContactCollectionNewPartnerContactToAttach = em.getReference(partnerContactCollectionNewPartnerContactToAttach.getClass(), partnerContactCollectionNewPartnerContactToAttach.getContactId());
                attachedPartnerContactCollectionNew.add(partnerContactCollectionNewPartnerContactToAttach);
            }
            partnerContactCollectionNew = attachedPartnerContactCollectionNew;
            configContactType.setPartnerContactCollection(partnerContactCollectionNew);
            configContactType = em.merge(configContactType);
            for (PartnerContact partnerContactCollectionOldPartnerContact : partnerContactCollectionOld) {
                if (!partnerContactCollectionNew.contains(partnerContactCollectionOldPartnerContact)) {
                    partnerContactCollectionOldPartnerContact.setType(null);
                    partnerContactCollectionOldPartnerContact = em.merge(partnerContactCollectionOldPartnerContact);
                }
            }
            for (PartnerContact partnerContactCollectionNewPartnerContact : partnerContactCollectionNew) {
                if (!partnerContactCollectionOld.contains(partnerContactCollectionNewPartnerContact)) {
                    ConfigContactType oldTypeOfPartnerContactCollectionNewPartnerContact = partnerContactCollectionNewPartnerContact.getType();
                    partnerContactCollectionNewPartnerContact.setType(configContactType);
                    partnerContactCollectionNewPartnerContact = em.merge(partnerContactCollectionNewPartnerContact);
                    if (oldTypeOfPartnerContactCollectionNewPartnerContact != null && !oldTypeOfPartnerContactCollectionNewPartnerContact.equals(configContactType)) {
                        oldTypeOfPartnerContactCollectionNewPartnerContact.getPartnerContactCollection().remove(partnerContactCollectionNewPartnerContact);
                        oldTypeOfPartnerContactCollectionNewPartnerContact = em.merge(oldTypeOfPartnerContactCollectionNewPartnerContact);
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
                String id = configContactType.getId();
                if (findConfigContactType(id) == null) {
                    throw new NonexistentEntityException("The configContactType with id " + id + " no longer exists.");
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
            ConfigContactType configContactType;
            try {
                configContactType = em.getReference(ConfigContactType.class, id);
                configContactType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configContactType with id " + id + " no longer exists.", enfe);
            }
            Collection<PartnerContact> partnerContactCollection = configContactType.getPartnerContactCollection();
            for (PartnerContact partnerContactCollectionPartnerContact : partnerContactCollection) {
                partnerContactCollectionPartnerContact.setType(null);
                partnerContactCollectionPartnerContact = em.merge(partnerContactCollectionPartnerContact);
            }
            em.remove(configContactType);
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

    public List<ConfigContactType> findConfigContactTypeEntities() {
        return findConfigContactTypeEntities(true, -1, -1);
    }

    public List<ConfigContactType> findConfigContactTypeEntities(int maxResults, int firstResult) {
        return findConfigContactTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigContactType> findConfigContactTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigContactType.class));
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

    public ConfigContactType findConfigContactType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigContactType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigContactTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigContactType> rt = cq.from(ConfigContactType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
