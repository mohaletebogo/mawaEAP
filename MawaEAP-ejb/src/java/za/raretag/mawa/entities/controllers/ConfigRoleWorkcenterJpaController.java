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
import za.raretag.mawa.entities.ConfigRoleType;
import za.raretag.mawa.entities.ConfigRoleWorkcenter;
import za.raretag.mawa.entities.ConfigRoleWorkcenterPK;
import za.raretag.mawa.entities.ConfigWorkcenter;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigRoleWorkcenterJpaController implements Serializable {

    public ConfigRoleWorkcenterJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigRoleWorkcenter configRoleWorkcenter) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configRoleWorkcenter.getConfigRoleWorkcenterPK() == null) {
            configRoleWorkcenter.setConfigRoleWorkcenterPK(new ConfigRoleWorkcenterPK());
        }
        configRoleWorkcenter.getConfigRoleWorkcenterPK().setRoleType(configRoleWorkcenter.getConfigRoleType().getId());
        configRoleWorkcenter.getConfigRoleWorkcenterPK().setWorkcenter(configRoleWorkcenter.getConfigWorkcenter().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigRoleType configRoleType = configRoleWorkcenter.getConfigRoleType();
            if (configRoleType != null) {
                configRoleType = em.getReference(configRoleType.getClass(), configRoleType.getId());
                configRoleWorkcenter.setConfigRoleType(configRoleType);
            }
            ConfigWorkcenter configWorkcenter = configRoleWorkcenter.getConfigWorkcenter();
            if (configWorkcenter != null) {
                configWorkcenter = em.getReference(configWorkcenter.getClass(), configWorkcenter.getId());
                configRoleWorkcenter.setConfigWorkcenter(configWorkcenter);
            }
            em.persist(configRoleWorkcenter);
            if (configRoleType != null) {
                configRoleType.getConfigRoleWorkcenterCollection().add(configRoleWorkcenter);
                configRoleType = em.merge(configRoleType);
            }
            if (configWorkcenter != null) {
                configWorkcenter.getConfigRoleWorkcenterCollection().add(configRoleWorkcenter);
                configWorkcenter = em.merge(configWorkcenter);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigRoleWorkcenter(configRoleWorkcenter.getConfigRoleWorkcenterPK()) != null) {
                throw new PreexistingEntityException("ConfigRoleWorkcenter " + configRoleWorkcenter + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigRoleWorkcenter configRoleWorkcenter) throws NonexistentEntityException, RollbackFailureException, Exception {
        configRoleWorkcenter.getConfigRoleWorkcenterPK().setRoleType(configRoleWorkcenter.getConfigRoleType().getId());
        configRoleWorkcenter.getConfigRoleWorkcenterPK().setWorkcenter(configRoleWorkcenter.getConfigWorkcenter().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigRoleWorkcenter persistentConfigRoleWorkcenter = em.find(ConfigRoleWorkcenter.class, configRoleWorkcenter.getConfigRoleWorkcenterPK());
            ConfigRoleType configRoleTypeOld = persistentConfigRoleWorkcenter.getConfigRoleType();
            ConfigRoleType configRoleTypeNew = configRoleWorkcenter.getConfigRoleType();
            ConfigWorkcenter configWorkcenterOld = persistentConfigRoleWorkcenter.getConfigWorkcenter();
            ConfigWorkcenter configWorkcenterNew = configRoleWorkcenter.getConfigWorkcenter();
            if (configRoleTypeNew != null) {
                configRoleTypeNew = em.getReference(configRoleTypeNew.getClass(), configRoleTypeNew.getId());
                configRoleWorkcenter.setConfigRoleType(configRoleTypeNew);
            }
            if (configWorkcenterNew != null) {
                configWorkcenterNew = em.getReference(configWorkcenterNew.getClass(), configWorkcenterNew.getId());
                configRoleWorkcenter.setConfigWorkcenter(configWorkcenterNew);
            }
            configRoleWorkcenter = em.merge(configRoleWorkcenter);
            if (configRoleTypeOld != null && !configRoleTypeOld.equals(configRoleTypeNew)) {
                configRoleTypeOld.getConfigRoleWorkcenterCollection().remove(configRoleWorkcenter);
                configRoleTypeOld = em.merge(configRoleTypeOld);
            }
            if (configRoleTypeNew != null && !configRoleTypeNew.equals(configRoleTypeOld)) {
                configRoleTypeNew.getConfigRoleWorkcenterCollection().add(configRoleWorkcenter);
                configRoleTypeNew = em.merge(configRoleTypeNew);
            }
            if (configWorkcenterOld != null && !configWorkcenterOld.equals(configWorkcenterNew)) {
                configWorkcenterOld.getConfigRoleWorkcenterCollection().remove(configRoleWorkcenter);
                configWorkcenterOld = em.merge(configWorkcenterOld);
            }
            if (configWorkcenterNew != null && !configWorkcenterNew.equals(configWorkcenterOld)) {
                configWorkcenterNew.getConfigRoleWorkcenterCollection().add(configRoleWorkcenter);
                configWorkcenterNew = em.merge(configWorkcenterNew);
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
                ConfigRoleWorkcenterPK id = configRoleWorkcenter.getConfigRoleWorkcenterPK();
                if (findConfigRoleWorkcenter(id) == null) {
                    throw new NonexistentEntityException("The configRoleWorkcenter with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ConfigRoleWorkcenterPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigRoleWorkcenter configRoleWorkcenter;
            try {
                configRoleWorkcenter = em.getReference(ConfigRoleWorkcenter.class, id);
                configRoleWorkcenter.getConfigRoleWorkcenterPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configRoleWorkcenter with id " + id + " no longer exists.", enfe);
            }
            ConfigRoleType configRoleType = configRoleWorkcenter.getConfigRoleType();
            if (configRoleType != null) {
                configRoleType.getConfigRoleWorkcenterCollection().remove(configRoleWorkcenter);
                configRoleType = em.merge(configRoleType);
            }
            ConfigWorkcenter configWorkcenter = configRoleWorkcenter.getConfigWorkcenter();
            if (configWorkcenter != null) {
                configWorkcenter.getConfigRoleWorkcenterCollection().remove(configRoleWorkcenter);
                configWorkcenter = em.merge(configWorkcenter);
            }
            em.remove(configRoleWorkcenter);
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

    public List<ConfigRoleWorkcenter> findConfigRoleWorkcenterEntities() {
        return findConfigRoleWorkcenterEntities(true, -1, -1);
    }

    public List<ConfigRoleWorkcenter> findConfigRoleWorkcenterEntities(int maxResults, int firstResult) {
        return findConfigRoleWorkcenterEntities(false, maxResults, firstResult);
    }

    private List<ConfigRoleWorkcenter> findConfigRoleWorkcenterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigRoleWorkcenter.class));
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

    public ConfigRoleWorkcenter findConfigRoleWorkcenter(ConfigRoleWorkcenterPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigRoleWorkcenter.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigRoleWorkcenterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigRoleWorkcenter> rt = cq.from(ConfigRoleWorkcenter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
