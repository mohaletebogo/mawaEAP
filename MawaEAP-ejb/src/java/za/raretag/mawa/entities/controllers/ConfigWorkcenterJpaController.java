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
import za.raretag.mawa.entities.ConfigRoleWorkcenter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigWorkcenter;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigWorkcenterJpaController implements Serializable {

    public ConfigWorkcenterJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigWorkcenter configWorkcenter) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configWorkcenter.getConfigRoleWorkcenterCollection() == null) {
            configWorkcenter.setConfigRoleWorkcenterCollection(new ArrayList<ConfigRoleWorkcenter>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<ConfigRoleWorkcenter> attachedConfigRoleWorkcenterCollection = new ArrayList<ConfigRoleWorkcenter>();
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach : configWorkcenter.getConfigRoleWorkcenterCollection()) {
                configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach = em.getReference(configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach.getClass(), configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach.getConfigRoleWorkcenterPK());
                attachedConfigRoleWorkcenterCollection.add(configRoleWorkcenterCollectionConfigRoleWorkcenterToAttach);
            }
            configWorkcenter.setConfigRoleWorkcenterCollection(attachedConfigRoleWorkcenterCollection);
            em.persist(configWorkcenter);
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionConfigRoleWorkcenter : configWorkcenter.getConfigRoleWorkcenterCollection()) {
                ConfigWorkcenter oldConfigWorkcenterOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter = configRoleWorkcenterCollectionConfigRoleWorkcenter.getConfigWorkcenter();
                configRoleWorkcenterCollectionConfigRoleWorkcenter.setConfigWorkcenter(configWorkcenter);
                configRoleWorkcenterCollectionConfigRoleWorkcenter = em.merge(configRoleWorkcenterCollectionConfigRoleWorkcenter);
                if (oldConfigWorkcenterOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter != null) {
                    oldConfigWorkcenterOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter.getConfigRoleWorkcenterCollection().remove(configRoleWorkcenterCollectionConfigRoleWorkcenter);
                    oldConfigWorkcenterOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter = em.merge(oldConfigWorkcenterOfConfigRoleWorkcenterCollectionConfigRoleWorkcenter);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigWorkcenter(configWorkcenter.getId()) != null) {
                throw new PreexistingEntityException("ConfigWorkcenter " + configWorkcenter + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigWorkcenter configWorkcenter) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigWorkcenter persistentConfigWorkcenter = em.find(ConfigWorkcenter.class, configWorkcenter.getId());
            Collection<ConfigRoleWorkcenter> configRoleWorkcenterCollectionOld = persistentConfigWorkcenter.getConfigRoleWorkcenterCollection();
            Collection<ConfigRoleWorkcenter> configRoleWorkcenterCollectionNew = configWorkcenter.getConfigRoleWorkcenterCollection();
            List<String> illegalOrphanMessages = null;
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionOldConfigRoleWorkcenter : configRoleWorkcenterCollectionOld) {
                if (!configRoleWorkcenterCollectionNew.contains(configRoleWorkcenterCollectionOldConfigRoleWorkcenter)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ConfigRoleWorkcenter " + configRoleWorkcenterCollectionOldConfigRoleWorkcenter + " since its configWorkcenter field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ConfigRoleWorkcenter> attachedConfigRoleWorkcenterCollectionNew = new ArrayList<ConfigRoleWorkcenter>();
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach : configRoleWorkcenterCollectionNew) {
                configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach = em.getReference(configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach.getClass(), configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach.getConfigRoleWorkcenterPK());
                attachedConfigRoleWorkcenterCollectionNew.add(configRoleWorkcenterCollectionNewConfigRoleWorkcenterToAttach);
            }
            configRoleWorkcenterCollectionNew = attachedConfigRoleWorkcenterCollectionNew;
            configWorkcenter.setConfigRoleWorkcenterCollection(configRoleWorkcenterCollectionNew);
            configWorkcenter = em.merge(configWorkcenter);
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionNewConfigRoleWorkcenter : configRoleWorkcenterCollectionNew) {
                if (!configRoleWorkcenterCollectionOld.contains(configRoleWorkcenterCollectionNewConfigRoleWorkcenter)) {
                    ConfigWorkcenter oldConfigWorkcenterOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter = configRoleWorkcenterCollectionNewConfigRoleWorkcenter.getConfigWorkcenter();
                    configRoleWorkcenterCollectionNewConfigRoleWorkcenter.setConfigWorkcenter(configWorkcenter);
                    configRoleWorkcenterCollectionNewConfigRoleWorkcenter = em.merge(configRoleWorkcenterCollectionNewConfigRoleWorkcenter);
                    if (oldConfigWorkcenterOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter != null && !oldConfigWorkcenterOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter.equals(configWorkcenter)) {
                        oldConfigWorkcenterOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter.getConfigRoleWorkcenterCollection().remove(configRoleWorkcenterCollectionNewConfigRoleWorkcenter);
                        oldConfigWorkcenterOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter = em.merge(oldConfigWorkcenterOfConfigRoleWorkcenterCollectionNewConfigRoleWorkcenter);
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
                String id = configWorkcenter.getId();
                if (findConfigWorkcenter(id) == null) {
                    throw new NonexistentEntityException("The configWorkcenter with id " + id + " no longer exists.");
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
            ConfigWorkcenter configWorkcenter;
            try {
                configWorkcenter = em.getReference(ConfigWorkcenter.class, id);
                configWorkcenter.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configWorkcenter with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ConfigRoleWorkcenter> configRoleWorkcenterCollectionOrphanCheck = configWorkcenter.getConfigRoleWorkcenterCollection();
            for (ConfigRoleWorkcenter configRoleWorkcenterCollectionOrphanCheckConfigRoleWorkcenter : configRoleWorkcenterCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigWorkcenter (" + configWorkcenter + ") cannot be destroyed since the ConfigRoleWorkcenter " + configRoleWorkcenterCollectionOrphanCheckConfigRoleWorkcenter + " in its configRoleWorkcenterCollection field has a non-nullable configWorkcenter field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(configWorkcenter);
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

    public List<ConfigWorkcenter> findConfigWorkcenterEntities() {
        return findConfigWorkcenterEntities(true, -1, -1);
    }

    public List<ConfigWorkcenter> findConfigWorkcenterEntities(int maxResults, int firstResult) {
        return findConfigWorkcenterEntities(false, maxResults, firstResult);
    }

    private List<ConfigWorkcenter> findConfigWorkcenterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigWorkcenter.class));
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

    public ConfigWorkcenter findConfigWorkcenter(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigWorkcenter.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigWorkcenterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigWorkcenter> rt = cq.from(ConfigWorkcenter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
