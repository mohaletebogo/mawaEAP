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
import za.raretag.mawa.entities.CheckOut;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.ConfigSalesArea;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class CheckOutJpaController implements Serializable {

    public CheckOutJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CheckOut checkOut) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Partner createdBy = checkOut.getCreatedBy();
            if (createdBy != null) {
                createdBy = em.getReference(createdBy.getClass(), createdBy.getPartnerNo());
                checkOut.setCreatedBy(createdBy);
            }
            Partner receivedBy = checkOut.getReceivedBy();
            if (receivedBy != null) {
                receivedBy = em.getReference(receivedBy.getClass(), receivedBy.getPartnerNo());
                checkOut.setReceivedBy(receivedBy);
            }
            ConfigSalesArea salesArea = checkOut.getSalesArea();
            if (salesArea != null) {
                salesArea = em.getReference(salesArea.getClass(), salesArea.getId());
                checkOut.setSalesArea(salesArea);
            }
            em.persist(checkOut);
            if (createdBy != null) {
                createdBy.getCheckOutCollection().add(checkOut);
                createdBy = em.merge(createdBy);
            }
            if (receivedBy != null) {
                receivedBy.getCheckOutCollection().add(checkOut);
                receivedBy = em.merge(receivedBy);
            }
            if (salesArea != null) {
                salesArea.getCheckOutCollection().add(checkOut);
                salesArea = em.merge(salesArea);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCheckOut(checkOut.getCheckingId()) != null) {
                throw new PreexistingEntityException("CheckOut " + checkOut + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CheckOut checkOut) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CheckOut persistentCheckOut = em.find(CheckOut.class, checkOut.getCheckingId());
            Partner createdByOld = persistentCheckOut.getCreatedBy();
            Partner createdByNew = checkOut.getCreatedBy();
            Partner receivedByOld = persistentCheckOut.getReceivedBy();
            Partner receivedByNew = checkOut.getReceivedBy();
            ConfigSalesArea salesAreaOld = persistentCheckOut.getSalesArea();
            ConfigSalesArea salesAreaNew = checkOut.getSalesArea();
            if (createdByNew != null) {
                createdByNew = em.getReference(createdByNew.getClass(), createdByNew.getPartnerNo());
                checkOut.setCreatedBy(createdByNew);
            }
            if (receivedByNew != null) {
                receivedByNew = em.getReference(receivedByNew.getClass(), receivedByNew.getPartnerNo());
                checkOut.setReceivedBy(receivedByNew);
            }
            if (salesAreaNew != null) {
                salesAreaNew = em.getReference(salesAreaNew.getClass(), salesAreaNew.getId());
                checkOut.setSalesArea(salesAreaNew);
            }
            checkOut = em.merge(checkOut);
            if (createdByOld != null && !createdByOld.equals(createdByNew)) {
                createdByOld.getCheckOutCollection().remove(checkOut);
                createdByOld = em.merge(createdByOld);
            }
            if (createdByNew != null && !createdByNew.equals(createdByOld)) {
                createdByNew.getCheckOutCollection().add(checkOut);
                createdByNew = em.merge(createdByNew);
            }
            if (receivedByOld != null && !receivedByOld.equals(receivedByNew)) {
                receivedByOld.getCheckOutCollection().remove(checkOut);
                receivedByOld = em.merge(receivedByOld);
            }
            if (receivedByNew != null && !receivedByNew.equals(receivedByOld)) {
                receivedByNew.getCheckOutCollection().add(checkOut);
                receivedByNew = em.merge(receivedByNew);
            }
            if (salesAreaOld != null && !salesAreaOld.equals(salesAreaNew)) {
                salesAreaOld.getCheckOutCollection().remove(checkOut);
                salesAreaOld = em.merge(salesAreaOld);
            }
            if (salesAreaNew != null && !salesAreaNew.equals(salesAreaOld)) {
                salesAreaNew.getCheckOutCollection().add(checkOut);
                salesAreaNew = em.merge(salesAreaNew);
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
                String id = checkOut.getCheckingId();
                if (findCheckOut(id) == null) {
                    throw new NonexistentEntityException("The checkOut with id " + id + " no longer exists.");
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
            CheckOut checkOut;
            try {
                checkOut = em.getReference(CheckOut.class, id);
                checkOut.getCheckingId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The checkOut with id " + id + " no longer exists.", enfe);
            }
            Partner createdBy = checkOut.getCreatedBy();
            if (createdBy != null) {
                createdBy.getCheckOutCollection().remove(checkOut);
                createdBy = em.merge(createdBy);
            }
            Partner receivedBy = checkOut.getReceivedBy();
            if (receivedBy != null) {
                receivedBy.getCheckOutCollection().remove(checkOut);
                receivedBy = em.merge(receivedBy);
            }
            ConfigSalesArea salesArea = checkOut.getSalesArea();
            if (salesArea != null) {
                salesArea.getCheckOutCollection().remove(checkOut);
                salesArea = em.merge(salesArea);
            }
            em.remove(checkOut);
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

    public List<CheckOut> findCheckOutEntities() {
        return findCheckOutEntities(true, -1, -1);
    }

    public List<CheckOut> findCheckOutEntities(int maxResults, int firstResult) {
        return findCheckOutEntities(false, maxResults, firstResult);
    }

    private List<CheckOut> findCheckOutEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CheckOut.class));
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

    public CheckOut findCheckOut(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CheckOut.class, id);
        } finally {
            em.close();
        }
    }

    public int getCheckOutCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CheckOut> rt = cq.from(CheckOut.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
