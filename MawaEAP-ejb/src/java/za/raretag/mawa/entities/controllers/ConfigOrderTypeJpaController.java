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
import za.raretag.mawa.entities.ConfigOrderType;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigOrderTypeJpaController implements Serializable {

    public ConfigOrderTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigOrderType configOrderType) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(configOrderType);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigOrderType(configOrderType.getId()) != null) {
                throw new PreexistingEntityException("ConfigOrderType " + configOrderType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigOrderType configOrderType) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            configOrderType = em.merge(configOrderType);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = configOrderType.getId();
                if (findConfigOrderType(id) == null) {
                    throw new NonexistentEntityException("The configOrderType with id " + id + " no longer exists.");
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
            ConfigOrderType configOrderType;
            try {
                configOrderType = em.getReference(ConfigOrderType.class, id);
                configOrderType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configOrderType with id " + id + " no longer exists.", enfe);
            }
            em.remove(configOrderType);
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

    public List<ConfigOrderType> findConfigOrderTypeEntities() {
        return findConfigOrderTypeEntities(true, -1, -1);
    }

    public List<ConfigOrderType> findConfigOrderTypeEntities(int maxResults, int firstResult) {
        return findConfigOrderTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigOrderType> findConfigOrderTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigOrderType.class));
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

    public ConfigOrderType findConfigOrderType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigOrderType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigOrderTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigOrderType> rt = cq.from(ConfigOrderType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
