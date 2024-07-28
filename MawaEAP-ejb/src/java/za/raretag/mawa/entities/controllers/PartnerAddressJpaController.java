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
import za.raretag.mawa.entities.ConfigAddressType;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerAddress;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class PartnerAddressJpaController implements Serializable {

    public PartnerAddressJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PartnerAddress partnerAddress) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigAddressType addressType = partnerAddress.getAddressType();
            if (addressType != null) {
                addressType = em.getReference(addressType.getClass(), addressType.getId());
                partnerAddress.setAddressType(addressType);
            }
            Partner partnerNo = partnerAddress.getPartnerNo();
            if (partnerNo != null) {
                partnerNo = em.getReference(partnerNo.getClass(), partnerNo.getPartnerNo());
                partnerAddress.setPartnerNo(partnerNo);
            }
            em.persist(partnerAddress);
            if (addressType != null) {
                addressType.getPartnerAddressCollection().add(partnerAddress);
                addressType = em.merge(addressType);
            }
            if (partnerNo != null) {
                partnerNo.getPartnerAddressCollection().add(partnerAddress);
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

    public void edit(PartnerAddress partnerAddress) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PartnerAddress persistentPartnerAddress = em.find(PartnerAddress.class, partnerAddress.getAddressId());
            ConfigAddressType addressTypeOld = persistentPartnerAddress.getAddressType();
            ConfigAddressType addressTypeNew = partnerAddress.getAddressType();
            Partner partnerNoOld = persistentPartnerAddress.getPartnerNo();
            Partner partnerNoNew = partnerAddress.getPartnerNo();
            if (addressTypeNew != null) {
                addressTypeNew = em.getReference(addressTypeNew.getClass(), addressTypeNew.getId());
                partnerAddress.setAddressType(addressTypeNew);
            }
            if (partnerNoNew != null) {
                partnerNoNew = em.getReference(partnerNoNew.getClass(), partnerNoNew.getPartnerNo());
                partnerAddress.setPartnerNo(partnerNoNew);
            }
            partnerAddress = em.merge(partnerAddress);
            if (addressTypeOld != null && !addressTypeOld.equals(addressTypeNew)) {
                addressTypeOld.getPartnerAddressCollection().remove(partnerAddress);
                addressTypeOld = em.merge(addressTypeOld);
            }
            if (addressTypeNew != null && !addressTypeNew.equals(addressTypeOld)) {
                addressTypeNew.getPartnerAddressCollection().add(partnerAddress);
                addressTypeNew = em.merge(addressTypeNew);
            }
            if (partnerNoOld != null && !partnerNoOld.equals(partnerNoNew)) {
                partnerNoOld.getPartnerAddressCollection().remove(partnerAddress);
                partnerNoOld = em.merge(partnerNoOld);
            }
            if (partnerNoNew != null && !partnerNoNew.equals(partnerNoOld)) {
                partnerNoNew.getPartnerAddressCollection().add(partnerAddress);
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
                Integer id = partnerAddress.getAddressId();
                if (findPartnerAddress(id) == null) {
                    throw new NonexistentEntityException("The partnerAddress with id " + id + " no longer exists.");
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
            PartnerAddress partnerAddress;
            try {
                partnerAddress = em.getReference(PartnerAddress.class, id);
                partnerAddress.getAddressId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partnerAddress with id " + id + " no longer exists.", enfe);
            }
            ConfigAddressType addressType = partnerAddress.getAddressType();
            if (addressType != null) {
                addressType.getPartnerAddressCollection().remove(partnerAddress);
                addressType = em.merge(addressType);
            }
            Partner partnerNo = partnerAddress.getPartnerNo();
            if (partnerNo != null) {
                partnerNo.getPartnerAddressCollection().remove(partnerAddress);
                partnerNo = em.merge(partnerNo);
            }
            em.remove(partnerAddress);
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

    public List<PartnerAddress> findPartnerAddressEntities() {
        return findPartnerAddressEntities(true, -1, -1);
    }

    public List<PartnerAddress> findPartnerAddressEntities(int maxResults, int firstResult) {
        return findPartnerAddressEntities(false, maxResults, firstResult);
    }

    private List<PartnerAddress> findPartnerAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PartnerAddress.class));
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

    public PartnerAddress findPartnerAddress(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PartnerAddress.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartnerAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PartnerAddress> rt = cq.from(PartnerAddress.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
