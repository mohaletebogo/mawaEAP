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
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.ConfigRelationType;
import za.raretag.mawa.entities.PartnerRelation;
import za.raretag.mawa.entities.PartnerRelationPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class PartnerRelationJpaController implements Serializable {

    public PartnerRelationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PartnerRelation partnerRelation) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (partnerRelation.getPartnerRelationPK() == null) {
            partnerRelation.setPartnerRelationPK(new PartnerRelationPK());
        }
        partnerRelation.getPartnerRelationPK().setPartnerNo1(partnerRelation.getPartner().getPartnerNo());
        partnerRelation.getPartnerRelationPK().setPartnerNo2(partnerRelation.getPartner1().getPartnerNo());
        partnerRelation.getPartnerRelationPK().setRelationsType(partnerRelation.getConfigRelationType().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Partner partner = partnerRelation.getPartner();
            if (partner != null) {
                partner = em.getReference(partner.getClass(), partner.getPartnerNo());
                partnerRelation.setPartner(partner);
            }
            Partner partner1 = partnerRelation.getPartner1();
            if (partner1 != null) {
                partner1 = em.getReference(partner1.getClass(), partner1.getPartnerNo());
                partnerRelation.setPartner1(partner1);
            }
            ConfigRelationType configRelationType = partnerRelation.getConfigRelationType();
            if (configRelationType != null) {
                configRelationType = em.getReference(configRelationType.getClass(), configRelationType.getId());
                partnerRelation.setConfigRelationType(configRelationType);
            }
            em.persist(partnerRelation);
            if (partner != null) {
                partner.getPartnerRelationCollection().add(partnerRelation);
                partner = em.merge(partner);
            }
            if (partner1 != null) {
                partner1.getPartnerRelationCollection().add(partnerRelation);
                partner1 = em.merge(partner1);
            }
            if (configRelationType != null) {
                configRelationType.getPartnerRelationCollection().add(partnerRelation);
                configRelationType = em.merge(configRelationType);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPartnerRelation(partnerRelation.getPartnerRelationPK()) != null) {
                throw new PreexistingEntityException("PartnerRelation " + partnerRelation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PartnerRelation partnerRelation) throws NonexistentEntityException, RollbackFailureException, Exception {
        partnerRelation.getPartnerRelationPK().setPartnerNo1(partnerRelation.getPartner().getPartnerNo());
        partnerRelation.getPartnerRelationPK().setPartnerNo2(partnerRelation.getPartner1().getPartnerNo());
        partnerRelation.getPartnerRelationPK().setRelationsType(partnerRelation.getConfigRelationType().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerRelation persistentPartnerRelation = em.find(PartnerRelation.class, partnerRelation.getPartnerRelationPK());
            Partner partnerOld = persistentPartnerRelation.getPartner();
            Partner partnerNew = partnerRelation.getPartner();
            Partner partner1Old = persistentPartnerRelation.getPartner1();
            Partner partner1New = partnerRelation.getPartner1();
            ConfigRelationType configRelationTypeOld = persistentPartnerRelation.getConfigRelationType();
            ConfigRelationType configRelationTypeNew = partnerRelation.getConfigRelationType();
            if (partnerNew != null) {
                partnerNew = em.getReference(partnerNew.getClass(), partnerNew.getPartnerNo());
                partnerRelation.setPartner(partnerNew);
            }
            if (partner1New != null) {
                partner1New = em.getReference(partner1New.getClass(), partner1New.getPartnerNo());
                partnerRelation.setPartner1(partner1New);
            }
            if (configRelationTypeNew != null) {
                configRelationTypeNew = em.getReference(configRelationTypeNew.getClass(), configRelationTypeNew.getId());
                partnerRelation.setConfigRelationType(configRelationTypeNew);
            }
            partnerRelation = em.merge(partnerRelation);
            if (partnerOld != null && !partnerOld.equals(partnerNew)) {
                partnerOld.getPartnerRelationCollection().remove(partnerRelation);
                partnerOld = em.merge(partnerOld);
            }
            if (partnerNew != null && !partnerNew.equals(partnerOld)) {
                partnerNew.getPartnerRelationCollection().add(partnerRelation);
                partnerNew = em.merge(partnerNew);
            }
            if (partner1Old != null && !partner1Old.equals(partner1New)) {
                partner1Old.getPartnerRelationCollection().remove(partnerRelation);
                partner1Old = em.merge(partner1Old);
            }
            if (partner1New != null && !partner1New.equals(partner1Old)) {
                partner1New.getPartnerRelationCollection().add(partnerRelation);
                partner1New = em.merge(partner1New);
            }
            if (configRelationTypeOld != null && !configRelationTypeOld.equals(configRelationTypeNew)) {
                configRelationTypeOld.getPartnerRelationCollection().remove(partnerRelation);
                configRelationTypeOld = em.merge(configRelationTypeOld);
            }
            if (configRelationTypeNew != null && !configRelationTypeNew.equals(configRelationTypeOld)) {
                configRelationTypeNew.getPartnerRelationCollection().add(partnerRelation);
                configRelationTypeNew = em.merge(configRelationTypeNew);
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
                PartnerRelationPK id = partnerRelation.getPartnerRelationPK();
                if (findPartnerRelation(id) == null) {
                    throw new NonexistentEntityException("The partnerRelation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PartnerRelationPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerRelation partnerRelation;
            try {
                partnerRelation = em.getReference(PartnerRelation.class, id);
                partnerRelation.getPartnerRelationPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partnerRelation with id " + id + " no longer exists.", enfe);
            }
            Partner partner = partnerRelation.getPartner();
            if (partner != null) {
                partner.getPartnerRelationCollection().remove(partnerRelation);
                partner = em.merge(partner);
            }
            Partner partner1 = partnerRelation.getPartner1();
            if (partner1 != null) {
                partner1.getPartnerRelationCollection().remove(partnerRelation);
                partner1 = em.merge(partner1);
            }
            ConfigRelationType configRelationType = partnerRelation.getConfigRelationType();
            if (configRelationType != null) {
                configRelationType.getPartnerRelationCollection().remove(partnerRelation);
                configRelationType = em.merge(configRelationType);
            }
            em.remove(partnerRelation);
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

    public List<PartnerRelation> findPartnerRelationEntities() {
        return findPartnerRelationEntities(true, -1, -1);
    }

    public List<PartnerRelation> findPartnerRelationEntities(int maxResults, int firstResult) {
        return findPartnerRelationEntities(false, maxResults, firstResult);
    }

    private List<PartnerRelation> findPartnerRelationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PartnerRelation.class));
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

    public PartnerRelation findPartnerRelation(PartnerRelationPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PartnerRelation.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartnerRelationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PartnerRelation> rt = cq.from(PartnerRelation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
