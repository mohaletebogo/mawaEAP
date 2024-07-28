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
import za.raretag.mawa.entities.ConfigSetting;
import za.raretag.mawa.entities.ConfigSettingPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigSettingJpaController implements Serializable {

    public ConfigSettingJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigSetting configSetting) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configSetting.getConfigSettingPK() == null) {
            configSetting.setConfigSettingPK(new ConfigSettingPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(configSetting);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigSetting(configSetting.getConfigSettingPK()) != null) {
                throw new PreexistingEntityException("ConfigSetting " + configSetting + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigSetting configSetting) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            configSetting = em.merge(configSetting);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ConfigSettingPK id = configSetting.getConfigSettingPK();
                if (findConfigSetting(id) == null) {
                    throw new NonexistentEntityException("The configSetting with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ConfigSettingPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigSetting configSetting;
            try {
                configSetting = em.getReference(ConfigSetting.class, id);
                configSetting.getConfigSettingPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configSetting with id " + id + " no longer exists.", enfe);
            }
            em.remove(configSetting);
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

    public List<ConfigSetting> findConfigSettingEntities() {
        return findConfigSettingEntities(true, -1, -1);
    }

    public List<ConfigSetting> findConfigSettingEntities(int maxResults, int firstResult) {
        return findConfigSettingEntities(false, maxResults, firstResult);
    }

    private List<ConfigSetting> findConfigSettingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigSetting.class));
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

    public ConfigSetting findConfigSetting(ConfigSettingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigSetting.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigSettingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigSetting> rt = cq.from(ConfigSetting.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
