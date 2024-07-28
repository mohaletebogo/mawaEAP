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
import za.raretag.mawa.entities.ConfigContactType;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerContact;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class PartnerContactJpaController implements Serializable {

    public PartnerContactJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PartnerContact partnerContact) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigContactType type = partnerContact.getType();
            if (type != null) {
                type = em.getReference(type.getClass(), type.getId());
                partnerContact.setType(type);
            }
            Partner partnerNo = partnerContact.getPartnerNo();
            if (partnerNo != null) {
                partnerNo = em.getReference(partnerNo.getClass(), partnerNo.getPartnerNo());
                partnerContact.setPartnerNo(partnerNo);
            }
            em.persist(partnerContact);
            if (type != null) {
                type.getPartnerContactCollection().add(partnerContact);
                type = em.merge(type);
            }
            if (partnerNo != null) {
                partnerNo.getPartnerContactCollection().add(partnerContact);
                partnerNo = em.merge(partnerNo);
            }
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

    public void edit(PartnerContact partnerContact) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerContact persistentPartnerContact = em.find(PartnerContact.class, partnerContact.getContactId());
            ConfigContactType typeOld = persistentPartnerContact.getType();
            ConfigContactType typeNew = partnerContact.getType();
            Partner partnerNoOld = persistentPartnerContact.getPartnerNo();
            Partner partnerNoNew = partnerContact.getPartnerNo();
            if (typeNew != null) {
                typeNew = em.getReference(typeNew.getClass(), typeNew.getId());
                partnerContact.setType(typeNew);
            }
            if (partnerNoNew != null) {
                partnerNoNew = em.getReference(partnerNoNew.getClass(), partnerNoNew.getPartnerNo());
                partnerContact.setPartnerNo(partnerNoNew);
            }
            partnerContact = em.merge(partnerContact);
            if (typeOld != null && !typeOld.equals(typeNew)) {
                typeOld.getPartnerContactCollection().remove(partnerContact);
                typeOld = em.merge(typeOld);
            }
            if (typeNew != null && !typeNew.equals(typeOld)) {
                typeNew.getPartnerContactCollection().add(partnerContact);
                typeNew = em.merge(typeNew);
            }
            if (partnerNoOld != null && !partnerNoOld.equals(partnerNoNew)) {
                partnerNoOld.getPartnerContactCollection().remove(partnerContact);
                partnerNoOld = em.merge(partnerNoOld);
            }
            if (partnerNoNew != null && !partnerNoNew.equals(partnerNoOld)) {
                partnerNoNew.getPartnerContactCollection().add(partnerContact);
                partnerNoNew = em.merge(partnerNoNew);
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
                Integer id = partnerContact.getContactId();
                if (findPartnerContact(id) == null) {
                    throw new NonexistentEntityException("The partnerContact with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerContact partnerContact;
            try {
                partnerContact = em.getReference(PartnerContact.class, id);
                partnerContact.getContactId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partnerContact with id " + id + " no longer exists.", enfe);
            }
            ConfigContactType type = partnerContact.getType();
            if (type != null) {
                type.getPartnerContactCollection().remove(partnerContact);
                type = em.merge(type);
            }
            Partner partnerNo = partnerContact.getPartnerNo();
            if (partnerNo != null) {
                partnerNo.getPartnerContactCollection().remove(partnerContact);
                partnerNo = em.merge(partnerNo);
            }
            em.remove(partnerContact);
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

    public List<PartnerContact> findPartnerContactEntities() {
        return findPartnerContactEntities(true, -1, -1);
    }

    public List<PartnerContact> findPartnerContactEntities(int maxResults, int firstResult) {
        return findPartnerContactEntities(false, maxResults, firstResult);
    }

    private List<PartnerContact> findPartnerContactEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PartnerContact.class));
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

    public PartnerContact findPartnerContact(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PartnerContact.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartnerContactCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PartnerContact> rt = cq.from(PartnerContact.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
