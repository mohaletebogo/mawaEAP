/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.entities.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import za.raretag.mawa.entities.PartnerAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigAddressType;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigAddressTypeJpaController implements Serializable {

    public ConfigAddressTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigAddressType configAddressType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configAddressType.getPartnerAddressCollection() == null) {
            configAddressType.setPartnerAddressCollection(new ArrayList<PartnerAddress>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PartnerAddress> attachedPartnerAddressCollection = new ArrayList<PartnerAddress>();
            for (PartnerAddress partnerAddressCollectionPartnerAddressToAttach : configAddressType.getPartnerAddressCollection()) {
                partnerAddressCollectionPartnerAddressToAttach = em.getReference(partnerAddressCollectionPartnerAddressToAttach.getClass(), partnerAddressCollectionPartnerAddressToAttach.getAddressId());
                attachedPartnerAddressCollection.add(partnerAddressCollectionPartnerAddressToAttach);
            }
            configAddressType.setPartnerAddressCollection(attachedPartnerAddressCollection);
            em.persist(configAddressType);
            for (PartnerAddress partnerAddressCollectionPartnerAddress : configAddressType.getPartnerAddressCollection()) {
                ConfigAddressType oldAddressTypeOfPartnerAddressCollectionPartnerAddress = partnerAddressCollectionPartnerAddress.getAddressType();
                partnerAddressCollectionPartnerAddress.setAddressType(configAddressType);
                partnerAddressCollectionPartnerAddress = em.merge(partnerAddressCollectionPartnerAddress);
                if (oldAddressTypeOfPartnerAddressCollectionPartnerAddress != null) {
                    oldAddressTypeOfPartnerAddressCollectionPartnerAddress.getPartnerAddressCollection().remove(partnerAddressCollectionPartnerAddress);
                    oldAddressTypeOfPartnerAddressCollectionPartnerAddress = em.merge(oldAddressTypeOfPartnerAddressCollectionPartnerAddress);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigAddressType(configAddressType.getId()) != null) {
                throw new PreexistingEntityException("ConfigAddressType " + configAddressType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigAddressType configAddressType) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigAddressType persistentConfigAddressType = em.find(ConfigAddressType.class, configAddressType.getId());
            Collection<PartnerAddress> partnerAddressCollectionOld = persistentConfigAddressType.getPartnerAddressCollection();
            Collection<PartnerAddress> partnerAddressCollectionNew = configAddressType.getPartnerAddressCollection();
            Collection<PartnerAddress> attachedPartnerAddressCollectionNew = new ArrayList<PartnerAddress>();
            for (PartnerAddress partnerAddressCollectionNewPartnerAddressToAttach : partnerAddressCollectionNew) {
                partnerAddressCollectionNewPartnerAddressToAttach = em.getReference(partnerAddressCollectionNewPartnerAddressToAttach.getClass(), partnerAddressCollectionNewPartnerAddressToAttach.getAddressId());
                attachedPartnerAddressCollectionNew.add(partnerAddressCollectionNewPartnerAddressToAttach);
            }
            partnerAddressCollectionNew = attachedPartnerAddressCollectionNew;
            configAddressType.setPartnerAddressCollection(partnerAddressCollectionNew);
            configAddressType = em.merge(configAddressType);
            for (PartnerAddress partnerAddressCollectionOldPartnerAddress : partnerAddressCollectionOld) {
                if (!partnerAddressCollectionNew.contains(partnerAddressCollectionOldPartnerAddress)) {
                    partnerAddressCollectionOldPartnerAddress.setAddressType(null);
                    partnerAddressCollectionOldPartnerAddress = em.merge(partnerAddressCollectionOldPartnerAddress);
                }
            }
            for (PartnerAddress partnerAddressCollectionNewPartnerAddress : partnerAddressCollectionNew) {
                if (!partnerAddressCollectionOld.contains(partnerAddressCollectionNewPartnerAddress)) {
                    ConfigAddressType oldAddressTypeOfPartnerAddressCollectionNewPartnerAddress = partnerAddressCollectionNewPartnerAddress.getAddressType();
                    partnerAddressCollectionNewPartnerAddress.setAddressType(configAddressType);
                    partnerAddressCollectionNewPartnerAddress = em.merge(partnerAddressCollectionNewPartnerAddress);
                    if (oldAddressTypeOfPartnerAddressCollectionNewPartnerAddress != null && !oldAddressTypeOfPartnerAddressCollectionNewPartnerAddress.equals(configAddressType)) {
                        oldAddressTypeOfPartnerAddressCollectionNewPartnerAddress.getPartnerAddressCollection().remove(partnerAddressCollectionNewPartnerAddress);
                        oldAddressTypeOfPartnerAddressCollectionNewPartnerAddress = em.merge(oldAddressTypeOfPartnerAddressCollectionNewPartnerAddress);
                    }
                }
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
                String id = configAddressType.getId();
                if (findConfigAddressType(id) == null) {
                    throw new NonexistentEntityException("The configAddressType with id " + id + " no longer exists.");
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
            ConfigAddressType configAddressType;
            try {
                configAddressType = em.getReference(ConfigAddressType.class, id);
                configAddressType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configAddressType with id " + id + " no longer exists.", enfe);
            }
            Collection<PartnerAddress> partnerAddressCollection = configAddressType.getPartnerAddressCollection();
            for (PartnerAddress partnerAddressCollectionPartnerAddress : partnerAddressCollection) {
                partnerAddressCollectionPartnerAddress.setAddressType(null);
                partnerAddressCollectionPartnerAddress = em.merge(partnerAddressCollectionPartnerAddress);
            }
            em.remove(configAddressType);
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

    public List<ConfigAddressType> findConfigAddressTypeEntities() {
        return findConfigAddressTypeEntities(true, -1, -1);
    }

    public List<ConfigAddressType> findConfigAddressTypeEntities(int maxResults, int firstResult) {
        return findConfigAddressTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigAddressType> findConfigAddressTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigAddressType.class));
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

    public ConfigAddressType findConfigAddressType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigAddressType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigAddressTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigAddressType> rt = cq.from(ConfigAddressType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
