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
import za.raretag.mawa.entities.ObjectStatus;
import za.raretag.mawa.entities.ObjectStatusPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ObjectStatusJpaController implements Serializable {

    public ObjectStatusJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ObjectStatus objectStatus) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (objectStatus.getObjectStatusPK() == null) {
            objectStatus.setObjectStatusPK(new ObjectStatusPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(objectStatus);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findObjectStatus(objectStatus.getObjectStatusPK()) != null) {
                throw new PreexistingEntityException("ObjectStatus " + objectStatus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ObjectStatus objectStatus) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            objectStatus = em.merge(objectStatus);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ObjectStatusPK id = objectStatus.getObjectStatusPK();
                if (findObjectStatus(id) == null) {
                    throw new NonexistentEntityException("The objectStatus with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ObjectStatusPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ObjectStatus objectStatus;
            try {
                objectStatus = em.getReference(ObjectStatus.class, id);
                objectStatus.getObjectStatusPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objectStatus with id " + id + " no longer exists.", enfe);
            }
            em.remove(objectStatus);
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

    public List<ObjectStatus> findObjectStatusEntities() {
        return findObjectStatusEntities(true, -1, -1);
    }

    public List<ObjectStatus> findObjectStatusEntities(int maxResults, int firstResult) {
        return findObjectStatusEntities(false, maxResults, firstResult);
    }

    private List<ObjectStatus> findObjectStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ObjectStatus.class));
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

    public ObjectStatus findObjectStatus(ObjectStatusPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ObjectStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjectStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ObjectStatus> rt = cq.from(ObjectStatus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
