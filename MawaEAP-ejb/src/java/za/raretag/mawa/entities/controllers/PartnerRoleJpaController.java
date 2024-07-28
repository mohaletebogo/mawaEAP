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
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerRole;
import za.raretag.mawa.entities.PartnerRolePK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class PartnerRoleJpaController implements Serializable {

    public PartnerRoleJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PartnerRole partnerRole) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (partnerRole.getPartnerRolePK() == null) {
            partnerRole.setPartnerRolePK(new PartnerRolePK());
        }
        partnerRole.getPartnerRolePK().setRoleId(partnerRole.getConfigRoleType().getId());
        partnerRole.getPartnerRolePK().setPartnerNo(partnerRole.getPartner().getPartnerNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigRoleType configRoleType = partnerRole.getConfigRoleType();
            if (configRoleType != null) {
                configRoleType = em.getReference(configRoleType.getClass(), configRoleType.getId());
                partnerRole.setConfigRoleType(configRoleType);
            }
            Partner partner = partnerRole.getPartner();
            if (partner != null) {
                partner = em.getReference(partner.getClass(), partner.getPartnerNo());
                partnerRole.setPartner(partner);
            }
            em.persist(partnerRole);
            if (configRoleType != null) {
                configRoleType.getPartnerRoleCollection().add(partnerRole);
                configRoleType = em.merge(configRoleType);
            }
            if (partner != null) {
                partner.getPartnerRoleCollection().add(partnerRole);
                partner = em.merge(partner);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPartnerRole(partnerRole.getPartnerRolePK()) != null) {
                throw new PreexistingEntityException("PartnerRole " + partnerRole + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PartnerRole partnerRole) throws NonexistentEntityException, RollbackFailureException, Exception {
        partnerRole.getPartnerRolePK().setRoleId(partnerRole.getConfigRoleType().getId());
        partnerRole.getPartnerRolePK().setPartnerNo(partnerRole.getPartner().getPartnerNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerRole persistentPartnerRole = em.find(PartnerRole.class, partnerRole.getPartnerRolePK());
            ConfigRoleType configRoleTypeOld = persistentPartnerRole.getConfigRoleType();
            ConfigRoleType configRoleTypeNew = partnerRole.getConfigRoleType();
            Partner partnerOld = persistentPartnerRole.getPartner();
            Partner partnerNew = partnerRole.getPartner();
            if (configRoleTypeNew != null) {
                configRoleTypeNew = em.getReference(configRoleTypeNew.getClass(), configRoleTypeNew.getId());
                partnerRole.setConfigRoleType(configRoleTypeNew);
            }
            if (partnerNew != null) {
                partnerNew = em.getReference(partnerNew.getClass(), partnerNew.getPartnerNo());
                partnerRole.setPartner(partnerNew);
            }
            partnerRole = em.merge(partnerRole);
            if (configRoleTypeOld != null && !configRoleTypeOld.equals(configRoleTypeNew)) {
                configRoleTypeOld.getPartnerRoleCollection().remove(partnerRole);
                configRoleTypeOld = em.merge(configRoleTypeOld);
            }
            if (configRoleTypeNew != null && !configRoleTypeNew.equals(configRoleTypeOld)) {
                configRoleTypeNew.getPartnerRoleCollection().add(partnerRole);
                configRoleTypeNew = em.merge(configRoleTypeNew);
            }
            if (partnerOld != null && !partnerOld.equals(partnerNew)) {
                partnerOld.getPartnerRoleCollection().remove(partnerRole);
                partnerOld = em.merge(partnerOld);
            }
            if (partnerNew != null && !partnerNew.equals(partnerOld)) {
                partnerNew.getPartnerRoleCollection().add(partnerRole);
                partnerNew = em.merge(partnerNew);
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
                PartnerRolePK id = partnerRole.getPartnerRolePK();
                if (findPartnerRole(id) == null) {
                    throw new NonexistentEntityException("The partnerRole with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PartnerRolePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerRole partnerRole;
            try {
                partnerRole = em.getReference(PartnerRole.class, id);
                partnerRole.getPartnerRolePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partnerRole with id " + id + " no longer exists.", enfe);
            }
            ConfigRoleType configRoleType = partnerRole.getConfigRoleType();
            if (configRoleType != null) {
                configRoleType.getPartnerRoleCollection().remove(partnerRole);
                configRoleType = em.merge(configRoleType);
            }
            Partner partner = partnerRole.getPartner();
            if (partner != null) {
                partner.getPartnerRoleCollection().remove(partnerRole);
                partner = em.merge(partner);
            }
            em.remove(partnerRole);
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

    public List<PartnerRole> findPartnerRoleEntities() {
        return findPartnerRoleEntities(true, -1, -1);
    }

    public List<PartnerRole> findPartnerRoleEntities(int maxResults, int firstResult) {
        return findPartnerRoleEntities(false, maxResults, firstResult);
    }

    private List<PartnerRole> findPartnerRoleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PartnerRole.class));
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

    public PartnerRole findPartnerRole(PartnerRolePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PartnerRole.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartnerRoleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PartnerRole> rt = cq.from(PartnerRole.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
