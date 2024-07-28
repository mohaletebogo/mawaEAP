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
import za.raretag.mawa.entities.PartnerIdentity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigIdentityType;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigIdentityTypeJpaController implements Serializable {

    public ConfigIdentityTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigIdentityType configIdentityType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configIdentityType.getPartnerIdentityCollection() == null) {
            configIdentityType.setPartnerIdentityCollection(new ArrayList<PartnerIdentity>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PartnerIdentity> attachedPartnerIdentityCollection = new ArrayList<PartnerIdentity>();
            for (PartnerIdentity partnerIdentityCollectionPartnerIdentityToAttach : configIdentityType.getPartnerIdentityCollection()) {
                partnerIdentityCollectionPartnerIdentityToAttach = em.getReference(partnerIdentityCollectionPartnerIdentityToAttach.getClass(), partnerIdentityCollectionPartnerIdentityToAttach.getPartnerIdentityPK());
                attachedPartnerIdentityCollection.add(partnerIdentityCollectionPartnerIdentityToAttach);
            }
            configIdentityType.setPartnerIdentityCollection(attachedPartnerIdentityCollection);
            em.persist(configIdentityType);
            for (PartnerIdentity partnerIdentityCollectionPartnerIdentity : configIdentityType.getPartnerIdentityCollection()) {
                ConfigIdentityType oldConfigIdentityTypeOfPartnerIdentityCollectionPartnerIdentity = partnerIdentityCollectionPartnerIdentity.getConfigIdentityType();
                partnerIdentityCollectionPartnerIdentity.setConfigIdentityType(configIdentityType);
                partnerIdentityCollectionPartnerIdentity = em.merge(partnerIdentityCollectionPartnerIdentity);
                if (oldConfigIdentityTypeOfPartnerIdentityCollectionPartnerIdentity != null) {
                    oldConfigIdentityTypeOfPartnerIdentityCollectionPartnerIdentity.getPartnerIdentityCollection().remove(partnerIdentityCollectionPartnerIdentity);
                    oldConfigIdentityTypeOfPartnerIdentityCollectionPartnerIdentity = em.merge(oldConfigIdentityTypeOfPartnerIdentityCollectionPartnerIdentity);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigIdentityType(configIdentityType.getId()) != null) {
                throw new PreexistingEntityException("ConfigIdentityType " + configIdentityType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigIdentityType configIdentityType) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigIdentityType persistentConfigIdentityType = em.find(ConfigIdentityType.class, configIdentityType.getId());
            Collection<PartnerIdentity> partnerIdentityCollectionOld = persistentConfigIdentityType.getPartnerIdentityCollection();
            Collection<PartnerIdentity> partnerIdentityCollectionNew = configIdentityType.getPartnerIdentityCollection();
            List<String> illegalOrphanMessages = null;
            for (PartnerIdentity partnerIdentityCollectionOldPartnerIdentity : partnerIdentityCollectionOld) {
                if (!partnerIdentityCollectionNew.contains(partnerIdentityCollectionOldPartnerIdentity)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PartnerIdentity " + partnerIdentityCollectionOldPartnerIdentity + " since its configIdentityType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PartnerIdentity> attachedPartnerIdentityCollectionNew = new ArrayList<PartnerIdentity>();
            for (PartnerIdentity partnerIdentityCollectionNewPartnerIdentityToAttach : partnerIdentityCollectionNew) {
                partnerIdentityCollectionNewPartnerIdentityToAttach = em.getReference(partnerIdentityCollectionNewPartnerIdentityToAttach.getClass(), partnerIdentityCollectionNewPartnerIdentityToAttach.getPartnerIdentityPK());
                attachedPartnerIdentityCollectionNew.add(partnerIdentityCollectionNewPartnerIdentityToAttach);
            }
            partnerIdentityCollectionNew = attachedPartnerIdentityCollectionNew;
            configIdentityType.setPartnerIdentityCollection(partnerIdentityCollectionNew);
            configIdentityType = em.merge(configIdentityType);
            for (PartnerIdentity partnerIdentityCollectionNewPartnerIdentity : partnerIdentityCollectionNew) {
                if (!partnerIdentityCollectionOld.contains(partnerIdentityCollectionNewPartnerIdentity)) {
                    ConfigIdentityType oldConfigIdentityTypeOfPartnerIdentityCollectionNewPartnerIdentity = partnerIdentityCollectionNewPartnerIdentity.getConfigIdentityType();
                    partnerIdentityCollectionNewPartnerIdentity.setConfigIdentityType(configIdentityType);
                    partnerIdentityCollectionNewPartnerIdentity = em.merge(partnerIdentityCollectionNewPartnerIdentity);
                    if (oldConfigIdentityTypeOfPartnerIdentityCollectionNewPartnerIdentity != null && !oldConfigIdentityTypeOfPartnerIdentityCollectionNewPartnerIdentity.equals(configIdentityType)) {
                        oldConfigIdentityTypeOfPartnerIdentityCollectionNewPartnerIdentity.getPartnerIdentityCollection().remove(partnerIdentityCollectionNewPartnerIdentity);
                        oldConfigIdentityTypeOfPartnerIdentityCollectionNewPartnerIdentity = em.merge(oldConfigIdentityTypeOfPartnerIdentityCollectionNewPartnerIdentity);
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
                String id = configIdentityType.getId();
                if (findConfigIdentityType(id) == null) {
                    throw new NonexistentEntityException("The configIdentityType with id " + id + " no longer exists.");
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
            ConfigIdentityType configIdentityType;
            try {
                configIdentityType = em.getReference(ConfigIdentityType.class, id);
                configIdentityType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configIdentityType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PartnerIdentity> partnerIdentityCollectionOrphanCheck = configIdentityType.getPartnerIdentityCollection();
            for (PartnerIdentity partnerIdentityCollectionOrphanCheckPartnerIdentity : partnerIdentityCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigIdentityType (" + configIdentityType + ") cannot be destroyed since the PartnerIdentity " + partnerIdentityCollectionOrphanCheckPartnerIdentity + " in its partnerIdentityCollection field has a non-nullable configIdentityType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configIdentityType);
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

    public List<ConfigIdentityType> findConfigIdentityTypeEntities() {
        return findConfigIdentityTypeEntities(true, -1, -1);
    }

    public List<ConfigIdentityType> findConfigIdentityTypeEntities(int maxResults, int firstResult) {
        return findConfigIdentityTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigIdentityType> findConfigIdentityTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigIdentityType.class));
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

    public ConfigIdentityType findConfigIdentityType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigIdentityType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigIdentityTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigIdentityType> rt = cq.from(ConfigIdentityType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
