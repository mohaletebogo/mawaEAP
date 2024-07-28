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
import za.raretag.mawa.entities.TransactionDocument;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigDocumentType;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigDocumentTypeJpaController implements Serializable {

    public ConfigDocumentTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigDocumentType configDocumentType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configDocumentType.getTransactionDocumentCollection() == null) {
            configDocumentType.setTransactionDocumentCollection(new ArrayList<TransactionDocument>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionDocument> attachedTransactionDocumentCollection = new ArrayList<TransactionDocument>();
            for (TransactionDocument transactionDocumentCollectionTransactionDocumentToAttach : configDocumentType.getTransactionDocumentCollection()) {
                transactionDocumentCollectionTransactionDocumentToAttach = em.getReference(transactionDocumentCollectionTransactionDocumentToAttach.getClass(), transactionDocumentCollectionTransactionDocumentToAttach.getTransactionNo());
                attachedTransactionDocumentCollection.add(transactionDocumentCollectionTransactionDocumentToAttach);
            }
            configDocumentType.setTransactionDocumentCollection(attachedTransactionDocumentCollection);
            em.persist(configDocumentType);
            for (TransactionDocument transactionDocumentCollectionTransactionDocument : configDocumentType.getTransactionDocumentCollection()) {
                ConfigDocumentType oldDocumentTypeOfTransactionDocumentCollectionTransactionDocument = transactionDocumentCollectionTransactionDocument.getDocumentType();
                transactionDocumentCollectionTransactionDocument.setDocumentType(configDocumentType);
                transactionDocumentCollectionTransactionDocument = em.merge(transactionDocumentCollectionTransactionDocument);
                if (oldDocumentTypeOfTransactionDocumentCollectionTransactionDocument != null) {
                    oldDocumentTypeOfTransactionDocumentCollectionTransactionDocument.getTransactionDocumentCollection().remove(transactionDocumentCollectionTransactionDocument);
                    oldDocumentTypeOfTransactionDocumentCollectionTransactionDocument = em.merge(oldDocumentTypeOfTransactionDocumentCollectionTransactionDocument);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigDocumentType(configDocumentType.getId()) != null) {
                throw new PreexistingEntityException("ConfigDocumentType " + configDocumentType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigDocumentType configDocumentType) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigDocumentType persistentConfigDocumentType = em.find(ConfigDocumentType.class, configDocumentType.getId());
            Collection<TransactionDocument> transactionDocumentCollectionOld = persistentConfigDocumentType.getTransactionDocumentCollection();
            Collection<TransactionDocument> transactionDocumentCollectionNew = configDocumentType.getTransactionDocumentCollection();
            Collection<TransactionDocument> attachedTransactionDocumentCollectionNew = new ArrayList<TransactionDocument>();
            for (TransactionDocument transactionDocumentCollectionNewTransactionDocumentToAttach : transactionDocumentCollectionNew) {
                transactionDocumentCollectionNewTransactionDocumentToAttach = em.getReference(transactionDocumentCollectionNewTransactionDocumentToAttach.getClass(), transactionDocumentCollectionNewTransactionDocumentToAttach.getTransactionNo());
                attachedTransactionDocumentCollectionNew.add(transactionDocumentCollectionNewTransactionDocumentToAttach);
            }
            transactionDocumentCollectionNew = attachedTransactionDocumentCollectionNew;
            configDocumentType.setTransactionDocumentCollection(transactionDocumentCollectionNew);
            configDocumentType = em.merge(configDocumentType);
            for (TransactionDocument transactionDocumentCollectionOldTransactionDocument : transactionDocumentCollectionOld) {
                if (!transactionDocumentCollectionNew.contains(transactionDocumentCollectionOldTransactionDocument)) {
                    transactionDocumentCollectionOldTransactionDocument.setDocumentType(null);
                    transactionDocumentCollectionOldTransactionDocument = em.merge(transactionDocumentCollectionOldTransactionDocument);
                }
            }
            for (TransactionDocument transactionDocumentCollectionNewTransactionDocument : transactionDocumentCollectionNew) {
                if (!transactionDocumentCollectionOld.contains(transactionDocumentCollectionNewTransactionDocument)) {
                    ConfigDocumentType oldDocumentTypeOfTransactionDocumentCollectionNewTransactionDocument = transactionDocumentCollectionNewTransactionDocument.getDocumentType();
                    transactionDocumentCollectionNewTransactionDocument.setDocumentType(configDocumentType);
                    transactionDocumentCollectionNewTransactionDocument = em.merge(transactionDocumentCollectionNewTransactionDocument);
                    if (oldDocumentTypeOfTransactionDocumentCollectionNewTransactionDocument != null && !oldDocumentTypeOfTransactionDocumentCollectionNewTransactionDocument.equals(configDocumentType)) {
                        oldDocumentTypeOfTransactionDocumentCollectionNewTransactionDocument.getTransactionDocumentCollection().remove(transactionDocumentCollectionNewTransactionDocument);
                        oldDocumentTypeOfTransactionDocumentCollectionNewTransactionDocument = em.merge(oldDocumentTypeOfTransactionDocumentCollectionNewTransactionDocument);
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
                String id = configDocumentType.getId();
                if (findConfigDocumentType(id) == null) {
                    throw new NonexistentEntityException("The configDocumentType with id " + id + " no longer exists.");
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
            ConfigDocumentType configDocumentType;
            try {
                configDocumentType = em.getReference(ConfigDocumentType.class, id);
                configDocumentType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configDocumentType with id " + id + " no longer exists.", enfe);
            }
            Collection<TransactionDocument> transactionDocumentCollection = configDocumentType.getTransactionDocumentCollection();
            for (TransactionDocument transactionDocumentCollectionTransactionDocument : transactionDocumentCollection) {
                transactionDocumentCollectionTransactionDocument.setDocumentType(null);
                transactionDocumentCollectionTransactionDocument = em.merge(transactionDocumentCollectionTransactionDocument);
            }
            em.remove(configDocumentType);
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

    public List<ConfigDocumentType> findConfigDocumentTypeEntities() {
        return findConfigDocumentTypeEntities(true, -1, -1);
    }

    public List<ConfigDocumentType> findConfigDocumentTypeEntities(int maxResults, int firstResult) {
        return findConfigDocumentTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigDocumentType> findConfigDocumentTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigDocumentType.class));
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

    public ConfigDocumentType findConfigDocumentType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigDocumentType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigDocumentTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigDocumentType> rt = cq.from(ConfigDocumentType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
