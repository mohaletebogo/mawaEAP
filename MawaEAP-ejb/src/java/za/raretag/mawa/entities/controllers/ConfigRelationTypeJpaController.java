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
import za.raretag.mawa.entities.PartnerRelation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigRelationType;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigRelationTypeJpaController implements Serializable {

    public ConfigRelationTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigRelationType configRelationType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configRelationType.getPartnerRelationCollection() == null) {
            configRelationType.setPartnerRelationCollection(new ArrayList<PartnerRelation>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PartnerRelation> attachedPartnerRelationCollection = new ArrayList<PartnerRelation>();
            for (PartnerRelation partnerRelationCollectionPartnerRelationToAttach : configRelationType.getPartnerRelationCollection()) {
                partnerRelationCollectionPartnerRelationToAttach = em.getReference(partnerRelationCollectionPartnerRelationToAttach.getClass(), partnerRelationCollectionPartnerRelationToAttach.getPartnerRelationPK());
                attachedPartnerRelationCollection.add(partnerRelationCollectionPartnerRelationToAttach);
            }
            configRelationType.setPartnerRelationCollection(attachedPartnerRelationCollection);
            em.persist(configRelationType);
            for (PartnerRelation partnerRelationCollectionPartnerRelation : configRelationType.getPartnerRelationCollection()) {
                ConfigRelationType oldConfigRelationTypeOfPartnerRelationCollectionPartnerRelation = partnerRelationCollectionPartnerRelation.getConfigRelationType();
                partnerRelationCollectionPartnerRelation.setConfigRelationType(configRelationType);
                partnerRelationCollectionPartnerRelation = em.merge(partnerRelationCollectionPartnerRelation);
                if (oldConfigRelationTypeOfPartnerRelationCollectionPartnerRelation != null) {
                    oldConfigRelationTypeOfPartnerRelationCollectionPartnerRelation.getPartnerRelationCollection().remove(partnerRelationCollectionPartnerRelation);
                    oldConfigRelationTypeOfPartnerRelationCollectionPartnerRelation = em.merge(oldConfigRelationTypeOfPartnerRelationCollectionPartnerRelation);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigRelationType(configRelationType.getId()) != null) {
                throw new PreexistingEntityException("ConfigRelationType " + configRelationType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigRelationType configRelationType) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigRelationType persistentConfigRelationType = em.find(ConfigRelationType.class, configRelationType.getId());
            Collection<PartnerRelation> partnerRelationCollectionOld = persistentConfigRelationType.getPartnerRelationCollection();
            Collection<PartnerRelation> partnerRelationCollectionNew = configRelationType.getPartnerRelationCollection();
            List<String> illegalOrphanMessages = null;
            for (PartnerRelation partnerRelationCollectionOldPartnerRelation : partnerRelationCollectionOld) {
                if (!partnerRelationCollectionNew.contains(partnerRelationCollectionOldPartnerRelation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PartnerRelation " + partnerRelationCollectionOldPartnerRelation + " since its configRelationType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PartnerRelation> attachedPartnerRelationCollectionNew = new ArrayList<PartnerRelation>();
            for (PartnerRelation partnerRelationCollectionNewPartnerRelationToAttach : partnerRelationCollectionNew) {
                partnerRelationCollectionNewPartnerRelationToAttach = em.getReference(partnerRelationCollectionNewPartnerRelationToAttach.getClass(), partnerRelationCollectionNewPartnerRelationToAttach.getPartnerRelationPK());
                attachedPartnerRelationCollectionNew.add(partnerRelationCollectionNewPartnerRelationToAttach);
            }
            partnerRelationCollectionNew = attachedPartnerRelationCollectionNew;
            configRelationType.setPartnerRelationCollection(partnerRelationCollectionNew);
            configRelationType = em.merge(configRelationType);
            for (PartnerRelation partnerRelationCollectionNewPartnerRelation : partnerRelationCollectionNew) {
                if (!partnerRelationCollectionOld.contains(partnerRelationCollectionNewPartnerRelation)) {
                    ConfigRelationType oldConfigRelationTypeOfPartnerRelationCollectionNewPartnerRelation = partnerRelationCollectionNewPartnerRelation.getConfigRelationType();
                    partnerRelationCollectionNewPartnerRelation.setConfigRelationType(configRelationType);
                    partnerRelationCollectionNewPartnerRelation = em.merge(partnerRelationCollectionNewPartnerRelation);
                    if (oldConfigRelationTypeOfPartnerRelationCollectionNewPartnerRelation != null && !oldConfigRelationTypeOfPartnerRelationCollectionNewPartnerRelation.equals(configRelationType)) {
                        oldConfigRelationTypeOfPartnerRelationCollectionNewPartnerRelation.getPartnerRelationCollection().remove(partnerRelationCollectionNewPartnerRelation);
                        oldConfigRelationTypeOfPartnerRelationCollectionNewPartnerRelation = em.merge(oldConfigRelationTypeOfPartnerRelationCollectionNewPartnerRelation);
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
                String id = configRelationType.getId();
                if (findConfigRelationType(id) == null) {
                    throw new NonexistentEntityException("The configRelationType with id " + id + " no longer exists.");
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
            ConfigRelationType configRelationType;
            try {
                configRelationType = em.getReference(ConfigRelationType.class, id);
                configRelationType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configRelationType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PartnerRelation> partnerRelationCollectionOrphanCheck = configRelationType.getPartnerRelationCollection();
            for (PartnerRelation partnerRelationCollectionOrphanCheckPartnerRelation : partnerRelationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigRelationType (" + configRelationType + ") cannot be destroyed since the PartnerRelation " + partnerRelationCollectionOrphanCheckPartnerRelation + " in its partnerRelationCollection field has a non-nullable configRelationType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configRelationType);
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

    public List<ConfigRelationType> findConfigRelationTypeEntities() {
        return findConfigRelationTypeEntities(true, -1, -1);
    }

    public List<ConfigRelationType> findConfigRelationTypeEntities(int maxResults, int firstResult) {
        return findConfigRelationTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigRelationType> findConfigRelationTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigRelationType.class));
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

    public ConfigRelationType findConfigRelationType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigRelationType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigRelationTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigRelationType> rt = cq.from(ConfigRelationType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
