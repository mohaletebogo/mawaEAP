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
import za.raretag.mawa.entities.ConfigIdentityType;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerIdentity;
import za.raretag.mawa.entities.PartnerIdentityPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class PartnerIdentityJpaController implements Serializable {

    public PartnerIdentityJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PartnerIdentity partnerIdentity) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (partnerIdentity.getPartnerIdentityPK() == null) {
            partnerIdentity.setPartnerIdentityPK(new PartnerIdentityPK());
        }
        partnerIdentity.getPartnerIdentityPK().setIdType(partnerIdentity.getConfigIdentityType().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigIdentityType configIdentityType = partnerIdentity.getConfigIdentityType();
            if (configIdentityType != null) {
                configIdentityType = em.getReference(configIdentityType.getClass(), configIdentityType.getId());
                partnerIdentity.setConfigIdentityType(configIdentityType);
            }
            Partner partnerNo = partnerIdentity.getPartnerNo();
            if (partnerNo != null) {
                partnerNo = em.getReference(partnerNo.getClass(), partnerNo.getPartnerNo());
                partnerIdentity.setPartnerNo(partnerNo);
            }
            em.persist(partnerIdentity);
            if (configIdentityType != null) {
                configIdentityType.getPartnerIdentityCollection().add(partnerIdentity);
                configIdentityType = em.merge(configIdentityType);
            }
            if (partnerNo != null) {
                partnerNo.getPartnerIdentityCollection().add(partnerIdentity);
                partnerNo = em.merge(partnerNo);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPartnerIdentity(partnerIdentity.getPartnerIdentityPK()) != null) {
                throw new PreexistingEntityException("PartnerIdentity " + partnerIdentity + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PartnerIdentity partnerIdentity) throws NonexistentEntityException, RollbackFailureException, Exception {
        partnerIdentity.getPartnerIdentityPK().setIdType(partnerIdentity.getConfigIdentityType().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerIdentity persistentPartnerIdentity = em.find(PartnerIdentity.class, partnerIdentity.getPartnerIdentityPK());
            ConfigIdentityType configIdentityTypeOld = persistentPartnerIdentity.getConfigIdentityType();
            ConfigIdentityType configIdentityTypeNew = partnerIdentity.getConfigIdentityType();
            Partner partnerNoOld = persistentPartnerIdentity.getPartnerNo();
            Partner partnerNoNew = partnerIdentity.getPartnerNo();
            if (configIdentityTypeNew != null) {
                configIdentityTypeNew = em.getReference(configIdentityTypeNew.getClass(), configIdentityTypeNew.getId());
                partnerIdentity.setConfigIdentityType(configIdentityTypeNew);
            }
            if (partnerNoNew != null) {
                partnerNoNew = em.getReference(partnerNoNew.getClass(), partnerNoNew.getPartnerNo());
                partnerIdentity.setPartnerNo(partnerNoNew);
            }
            partnerIdentity = em.merge(partnerIdentity);
            if (configIdentityTypeOld != null && !configIdentityTypeOld.equals(configIdentityTypeNew)) {
                configIdentityTypeOld.getPartnerIdentityCollection().remove(partnerIdentity);
                configIdentityTypeOld = em.merge(configIdentityTypeOld);
            }
            if (configIdentityTypeNew != null && !configIdentityTypeNew.equals(configIdentityTypeOld)) {
                configIdentityTypeNew.getPartnerIdentityCollection().add(partnerIdentity);
                configIdentityTypeNew = em.merge(configIdentityTypeNew);
            }
            if (partnerNoOld != null && !partnerNoOld.equals(partnerNoNew)) {
                partnerNoOld.getPartnerIdentityCollection().remove(partnerIdentity);
                partnerNoOld = em.merge(partnerNoOld);
            }
            if (partnerNoNew != null && !partnerNoNew.equals(partnerNoOld)) {
                partnerNoNew.getPartnerIdentityCollection().add(partnerIdentity);
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
                PartnerIdentityPK id = partnerIdentity.getPartnerIdentityPK();
                if (findPartnerIdentity(id) == null) {
                    throw new NonexistentEntityException("The partnerIdentity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PartnerIdentityPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerIdentity partnerIdentity;
            try {
                partnerIdentity = em.getReference(PartnerIdentity.class, id);
                partnerIdentity.getPartnerIdentityPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partnerIdentity with id " + id + " no longer exists.", enfe);
            }
            ConfigIdentityType configIdentityType = partnerIdentity.getConfigIdentityType();
            if (configIdentityType != null) {
                configIdentityType.getPartnerIdentityCollection().remove(partnerIdentity);
                configIdentityType = em.merge(configIdentityType);
            }
            Partner partnerNo = partnerIdentity.getPartnerNo();
            if (partnerNo != null) {
                partnerNo.getPartnerIdentityCollection().remove(partnerIdentity);
                partnerNo = em.merge(partnerNo);
            }
            em.remove(partnerIdentity);
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

    public List<PartnerIdentity> findPartnerIdentityEntities() {
        return findPartnerIdentityEntities(true, -1, -1);
    }

    public List<PartnerIdentity> findPartnerIdentityEntities(int maxResults, int firstResult) {
        return findPartnerIdentityEntities(false, maxResults, firstResult);
    }

    private List<PartnerIdentity> findPartnerIdentityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PartnerIdentity.class));
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

    public PartnerIdentity findPartnerIdentity(PartnerIdentityPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PartnerIdentity.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartnerIdentityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PartnerIdentity> rt = cq.from(PartnerIdentity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
