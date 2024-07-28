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
import za.raretag.mawa.entities.PartnerRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigRoleType;
import za.raretag.mawa.entities.ConfigRoleWorkcenter;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigRoleTypeJpaController implements Serializable {

    public ConfigRoleTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigRoleType configRoleType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configRoleType.getPartnerRoleCollection() == null) {
            configRoleType.setPartnerRoleCollection(new ArrayList<PartnerRole>());
        }
        if (configRoleType.getConfigRoleWorkcenterCollection() == null) {
            configRoleType.setConfigRoleWorkcenterCollection(new ArrayList<ConfigRoleWorkcenter>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PartnerRole> attachedPartnerRoleCollection = new ArrayList<PartnerRole>();
            for (PartnerRole partnerRoleCollectionPartnerRoleToAttach : configRoleType.getPartnerRoleCollection()) {
                partnerRoleCollectionPartnerRoleToAttach = em.getReference(partnerRoleCollectionPartnerRoleToAttach.getClass(), partnerRoleCollectionPartnerRoleToAttach.getPartnerRolePK());
                attachedPartnerRoleCollection.add(partnerRoleCollectionPartnerRoleToAttach);
            }
            configRoleType.setPartnerRoleCollection(attachedPartnerRoleCollection);
            Collection<ConfigRoleWorkcenter> attachedConfigRoleWorkcenterCollection = new ArrayList<ConfigRoleWorkcenter>();
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach : configRoleType.getConfigRoleWorkcenterCollection()) {
                configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach = em.getReference(configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach.getClass(), configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach.getConfigRoleWorkcenterPK());
                attachedConfigRoleWorkcenterCollection.add(configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach);
            }
            configRoleType.setConfigRoleWorkcenterCollection(attachedConfigRoleWorkcenterCollection);
            em.persist(configRoleType);
            for (PartnerRole partnerRoleCollectionPartnerRole : configRoleType.getPartnerRoleCollection()) {
                ConfigRoleType oldConfigRoleTypeOfPartnerRoleCollectionPartnerRole = partnerRoleCollectionPartnerRole.getConfigRoleType();
                partnerRoleCollectionPartnerRole.setConfigRoleType(configRoleType);
                partnerRoleCollectionPartnerRole = em.merge(partnerRoleCollectionPartnerRole);
                if (oldConfigRoleTypeOfPartnerRoleCollectionPartnerRole != null) {
                    oldConfigRoleTypeOfPartnerRoleCollectionPartnerRole.getPartnerRoleCollection().remove(partnerRoleCollectionPartnerRole);
                    oldConfigRoleTypeOfPartnerRoleCollectionPartnerRole = em.merge(oldConfigRoleTypeOfPartnerRoleCollectionPartnerRole);
                }
            }
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionConfigRoleWorkcenter : configRoleType.getConfigRoleWorkcenterCollection()) {
                ConfigRoleType oldConfigRoleTypeOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter = configRoleWorkcenterCollectionConfigRoleWorkcenter.getConfigRoleType();
                configRoleWorkcenterCollectionConfigRoleWorkcenter.setConfigRoleType(configRoleType);
                configRoleWorkcenterCollectionConfigRoleWorkcenter = em.merge(configRoleWorkcenterCollectionConfigRoleWorkcenter);
                if (oldConfigRoleTypeOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter != null) {
                    oldConfigRoleTypeOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter.getConfigRoleWorkcenterCollection().remove(configRoleWorkcenterCollectionConfigRoleWorkcenter);
                    oldConfigRoleTypeOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter = em.merge(oldConfigRoleTypeOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigRoleType(configRoleType.getId()) != null) {
                throw new PreexistingEntityException("ConfigRoleType " + configRoleType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigRoleType configRoleType) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigRoleType persistentConfigRoleType = em.find(ConfigRoleType.class, configRoleType.getId());
            Collection<PartnerRole> partnerRoleCollectionOld = persistentConfigRoleType.getPartnerRoleCollection();
            Collection<PartnerRole> partnerRoleCollectionNew = configRoleType.getPartnerRoleCollection();
            Collection<ConfigRoleWorkcenter> configRoleWorkcenterCollectionOld = persistentConfigRoleType.getConfigRoleWorkcenterCollection();
            Collection<ConfigRoleWorkcenter> configRoleWorkcenterCollectionNew = configRoleType.getConfigRoleWorkcenterCollection();
            List<String> illegalOrphanMessages = null;
            for (PartnerRole partnerRoleCollectionOldPartnerRole : partnerRoleCollectionOld) {
                if (!partnerRoleCollectionNew.contains(partnerRoleCollectionOldPartnerRole)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PartnerRole " + partnerRoleCollectionOldPartnerRole + " since its configRoleType field is not nullable.");
                }
            }
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionOldConfigRoleWorkcenter : configRoleWorkcenterCollectionOld) {
                if (!configRoleWorkcenterCollectionNew.contains(configRoleWorkcenterCollectionOldConfigRoleWorkcenter)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ConfigRoleWorkcenter " + configRoleWorkcenterCollectionOldConfigRoleWorkcenter + " since its configRoleType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PartnerRole> attachedPartnerRoleCollectionNew = new ArrayList<PartnerRole>();
            for (PartnerRole partnerRoleCollectionNewPartnerRoleToAttach : partnerRoleCollectionNew) {
                partnerRoleCollectionNewPartnerRoleToAttach = em.getReference(partnerRoleCollectionNewPartnerRoleToAttach.getClass(), partnerRoleCollectionNewPartnerRoleToAttach.getPartnerRolePK());
                attachedPartnerRoleCollectionNew.add(partnerRoleCollectionNewPartnerRoleToAttach);
            }
            partnerRoleCollectionNew = attachedPartnerRoleCollectionNew;
            configRoleType.setPartnerRoleCollection(partnerRoleCollectionNew);
            Collection<ConfigRoleWorkcenter> attachedConfigRoleWorkcenterCollectionNew = new ArrayList<ConfigRoleWorkcenter>();
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach : configRoleWorkcenterCollectionNew) {
                configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach = em.getReference(configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach.getClass(), configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach.getConfigRoleWorkcenterPK());
                attachedConfigRoleWorkcenterCollectionNew.add(configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach);
            }
            configRoleWorkcenterCollectionNew = attachedConfigRoleWorkcenterCollectionNew;
            configRoleType.setConfigRoleWorkcenterCollection(configRoleWorkcenterCollectionNew);
            configRoleType = em.merge(configRoleType);
            for (PartnerRole partnerRoleCollectionNewPartnerRole : partnerRoleCollectionNew) {
                if (!partnerRoleCollectionOld.contains(partnerRoleCollectionNewPartnerRole)) {
                    ConfigRoleType oldConfigRoleTypeOfPartnerRoleCollectionNewPartnerRole = partnerRoleCollectionNewPartnerRole.getConfigRoleType();
                    partnerRoleCollectionNewPartnerRole.setConfigRoleType(configRoleType);
                    partnerRoleCollectionNewPartnerRole = em.merge(partnerRoleCollectionNewPartnerRole);
                    if (oldConfigRoleTypeOfPartnerRoleCollectionNewPartnerRole != null && !oldConfigRoleTypeOfPartnerRoleCollectionNewPartnerRole.equals(configRoleType)) {
                        oldConfigRoleTypeOfPartnerRoleCollectionNewPartnerRole.getPartnerRoleCollection().remove(partnerRoleCollectionNewPartnerRole);
                        oldConfigRoleTypeOfPartnerRoleCollectionNewPartnerRole = em.merge(oldConfigRoleTypeOfPartnerRoleCollectionNewPartnerRole);
                    }
                }
            }
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionNewConfigRoleWorkcenter : configRoleWorkcenterCollectionNew) {
                if (!configRoleWorkcenterCollectionOld.contains(configRoleWorkcenterCollectionNewConfigRoleWorkcenter)) {
                    ConfigRoleType oldConfigRoleTypeOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter = configRoleWorkcenterCollectionNewConfigRoleWorkcenter.getConfigRoleType();
                    configRoleWorkcenterCollectionNewConfigRoleWorkcenter.setConfigRoleType(configRoleType);
                    configRoleWorkcenterCollectionNewConfigRoleWorkcenter = em.merge(configRoleWorkcenterCollectionNewConfigRoleWorkcenter);
                    if (oldConfigRoleTypeOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter != null && !oldConfigRoleTypeOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter.equals(configRoleType)) {
                        oldConfigRoleTypeOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter.getConfigRoleWorkcenterCollection().remove(configRoleWorkcenterCollectionNewConfigRoleWorkcenter);
                        oldConfigRoleTypeOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter = em.merge(oldConfigRoleTypeOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter);
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
                String id = configRoleType.getId();
                if (findConfigRoleType(id) == null) {
                    throw new NonexistentEntityException("The configRoleType with id " + id + " no longer exists.");
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
            ConfigRoleType configRoleType;
            try {
                configRoleType = em.getReference(ConfigRoleType.class, id);
                configRoleType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configRoleType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PartnerRole> partnerRoleCollectionOrphanCheck = configRoleType.getPartnerRoleCollection();
            for (PartnerRole partnerRoleCollectionOrphanCheckPartnerRole : partnerRoleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigRoleType (" + configRoleType + ") cannot be destroyed since the PartnerRole " + partnerRoleCollectionOrphanCheckPartnerRole + " in its partnerRoleCollection field has a non-nullable configRoleType field.");
            }
            Collection<ConfigRoleWorkcenter> configRoleWorkcenterCollectionOrphanCheck = configRoleType.getConfigRoleWorkcenterCollection();
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionOrphanCheckConfigRoleWorkcenter : configRoleWorkcenterCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigRoleType (" + configRoleType + ") cannot be destroyed since the ConfigRoleWorkcenter " + configRoleWorkcenterCollectionOrphanCheckConfigRoleWorkcenter + " in its configRoleWorkcenterCollection field has a non-nullable configRoleType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configRoleType);
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

    public List<ConfigRoleType> findConfigRoleTypeEntities() {
        return findConfigRoleTypeEntities(true, -1, -1);
    }

    public List<ConfigRoleType> findConfigRoleTypeEntities(int maxResults, int firstResult) {
        return findConfigRoleTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigRoleType> findConfigRoleTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigRoleType.class));
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

    public ConfigRoleType findConfigRoleType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigRoleType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigRoleTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigRoleType> rt = cq.from(ConfigRoleType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
