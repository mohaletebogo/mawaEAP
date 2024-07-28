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
import za.raretag.mawa.entities.TransactionRelation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigTransactionType;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigTransactionTypeJpaController implements Serializable {

    public ConfigTransactionTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigTransactionType configTransactionType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configTransactionType.getTransactionRelationCollection() == null) {
            configTransactionType.setTransactionRelationCollection(new ArrayList<TransactionRelation>());
        }
        if (configTransactionType.getTransactionCollection() == null) {
            configTransactionType.setTransactionCollection(new ArrayList<Transaction>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionRelation> attachedTransactionRelationCollection = new ArrayList<TransactionRelation>();
            for (TransactionRelation transactionRelationCollectionTransactionRelationToAttach : configTransactionType.getTransactionRelationCollection()) {
                transactionRelationCollectionTransactionRelationToAttach = em.getReference(transactionRelationCollectionTransactionRelationToAttach.getClass(), transactionRelationCollectionTransactionRelationToAttach.getTransactionRelationPK());
                attachedTransactionRelationCollection.add(transactionRelationCollectionTransactionRelationToAttach);
            }
            configTransactionType.setTransactionRelationCollection(attachedTransactionRelationCollection);
            Collection<Transaction> attachedTransactionCollection = new ArrayList<Transaction>();
            for (Transaction transactionCollectionTransactionToAttach : configTransactionType.getTransactionCollection()) {
                transactionCollectionTransactionToAttach = em.getReference(transactionCollectionTransactionToAttach.getClass(), transactionCollectionTransactionToAttach.getTransactionNo());
                attachedTransactionCollection.add(transactionCollectionTransactionToAttach);
            }
            configTransactionType.setTransactionCollection(attachedTransactionCollection);
            em.persist(configTransactionType);
            for (TransactionRelation transactionRelationCollectionTransactionRelation : configTransactionType.getTransactionRelationCollection()) {
                ConfigTransactionType oldConfigTransactionTypeOfTransactionRelationCollectionTransactionRelation = transactionRelationCollectionTransactionRelation.getConfigTransactionType();
                transactionRelationCollectionTransactionRelation.setConfigTransactionType(configTransactionType);
                transactionRelationCollectionTransactionRelation = em.merge(transactionRelationCollectionTransactionRelation);
                if (oldConfigTransactionTypeOfTransactionRelationCollectionTransactionRelation != null) {
                    oldConfigTransactionTypeOfTransactionRelationCollectionTransactionRelation.getTransactionRelationCollection().remove(transactionRelationCollectionTransactionRelation);
                    oldConfigTransactionTypeOfTransactionRelationCollectionTransactionRelation = em.merge(oldConfigTransactionTypeOfTransactionRelationCollectionTransactionRelation);
                }
            }
            for (Transaction transactionCollectionTransaction : configTransactionType.getTransactionCollection()) {
                ConfigTransactionType oldTransactionTypeOfTransactionCollectionTransaction = transactionCollectionTransaction.getTransactionType();
                transactionCollectionTransaction.setTransactionType(configTransactionType);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
                if (oldTransactionTypeOfTransactionCollectionTransaction != null) {
                    oldTransactionTypeOfTransactionCollectionTransaction.getTransactionCollection().remove(transactionCollectionTransaction);
                    oldTransactionTypeOfTransactionCollectionTransaction = em.merge(oldTransactionTypeOfTransactionCollectionTransaction);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigTransactionType(configTransactionType.getId()) != null) {
                throw new PreexistingEntityException("ConfigTransactionType " + configTransactionType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigTransactionType configTransactionType) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigTransactionType persistentConfigTransactionType = em.find(ConfigTransactionType.class, configTransactionType.getId());
            Collection<TransactionRelation> transactionRelationCollectionOld = persistentConfigTransactionType.getTransactionRelationCollection();
            Collection<TransactionRelation> transactionRelationCollectionNew = configTransactionType.getTransactionRelationCollection();
            Collection<Transaction> transactionCollectionOld = persistentConfigTransactionType.getTransactionCollection();
            Collection<Transaction> transactionCollectionNew = configTransactionType.getTransactionCollection();
            List<String> illegalOrphanMessages = null;
            for (TransactionRelation transactionRelationCollectionOldTransactionRelation : transactionRelationCollectionOld) {
                if (!transactionRelationCollectionNew.contains(transactionRelationCollectionOldTransactionRelation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionRelation " + transactionRelationCollectionOldTransactionRelation + " since its configTransactionType field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionRelation> attachedTransactionRelationCollectionNew = new ArrayList<TransactionRelation>();
            for (TransactionRelation transactionRelationCollectionNewTransactionRelationToAttach : transactionRelationCollectionNew) {
                transactionRelationCollectionNewTransactionRelationToAttach = em.getReference(transactionRelationCollectionNewTransactionRelationToAttach.getClass(), transactionRelationCollectionNewTransactionRelationToAttach.getTransactionRelationPK());
                attachedTransactionRelationCollectionNew.add(transactionRelationCollectionNewTransactionRelationToAttach);
            }
            transactionRelationCollectionNew = attachedTransactionRelationCollectionNew;
            configTransactionType.setTransactionRelationCollection(transactionRelationCollectionNew);
            Collection<Transaction> attachedTransactionCollectionNew = new ArrayList<Transaction>();
            for (Transaction transactionCollectionNewTransactionToAttach : transactionCollectionNew) {
                transactionCollectionNewTransactionToAttach = em.getReference(transactionCollectionNewTransactionToAttach.getClass(), transactionCollectionNewTransactionToAttach.getTransactionNo());
                attachedTransactionCollectionNew.add(transactionCollectionNewTransactionToAttach);
            }
            transactionCollectionNew = attachedTransactionCollectionNew;
            configTransactionType.setTransactionCollection(transactionCollectionNew);
            configTransactionType = em.merge(configTransactionType);
            for (TransactionRelation transactionRelationCollectionNewTransactionRelation : transactionRelationCollectionNew) {
                if (!transactionRelationCollectionOld.contains(transactionRelationCollectionNewTransactionRelation)) {
                    ConfigTransactionType oldConfigTransactionTypeOfTransactionRelationCollectionNewTransactionRelation = transactionRelationCollectionNewTransactionRelation.getConfigTransactionType();
                    transactionRelationCollectionNewTransactionRelation.setConfigTransactionType(configTransactionType);
                    transactionRelationCollectionNewTransactionRelation = em.merge(transactionRelationCollectionNewTransactionRelation);
                    if (oldConfigTransactionTypeOfTransactionRelationCollectionNewTransactionRelation != null && !oldConfigTransactionTypeOfTransactionRelationCollectionNewTransactionRelation.equals(configTransactionType)) {
                        oldConfigTransactionTypeOfTransactionRelationCollectionNewTransactionRelation.getTransactionRelationCollection().remove(transactionRelationCollectionNewTransactionRelation);
                        oldConfigTransactionTypeOfTransactionRelationCollectionNewTransactionRelation = em.merge(oldConfigTransactionTypeOfTransactionRelationCollectionNewTransactionRelation);
                    }
                }
            }
            for (Transaction transactionCollectionOldTransaction : transactionCollectionOld) {
                if (!transactionCollectionNew.contains(transactionCollectionOldTransaction)) {
                    transactionCollectionOldTransaction.setTransactionType(null);
                    transactionCollectionOldTransaction = em.merge(transactionCollectionOldTransaction);
                }
            }
            for (Transaction transactionCollectionNewTransaction : transactionCollectionNew) {
                if (!transactionCollectionOld.contains(transactionCollectionNewTransaction)) {
                    ConfigTransactionType oldTransactionTypeOfTransactionCollectionNewTransaction = transactionCollectionNewTransaction.getTransactionType();
                    transactionCollectionNewTransaction.setTransactionType(configTransactionType);
                    transactionCollectionNewTransaction = em.merge(transactionCollectionNewTransaction);
                    if (oldTransactionTypeOfTransactionCollectionNewTransaction != null && !oldTransactionTypeOfTransactionCollectionNewTransaction.equals(configTransactionType)) {
                        oldTransactionTypeOfTransactionCollectionNewTransaction.getTransactionCollection().remove(transactionCollectionNewTransaction);
                        oldTransactionTypeOfTransactionCollectionNewTransaction = em.merge(oldTransactionTypeOfTransactionCollectionNewTransaction);
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
                String id = configTransactionType.getId();
                if (findConfigTransactionType(id) == null) {
                    throw new NonexistentEntityException("The configTransactionType with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigTransactionType configTransactionType;
            try {
                configTransactionType = em.getReference(ConfigTransactionType.class, id);
                configTransactionType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configTransactionType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransactionRelation> transactionRelationCollectionOrphanCheck = configTransactionType.getTransactionRelationCollection();
            for (TransactionRelation transactionRelationCollectionOrphanCheckTransactionRelation : transactionRelationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConfigTransactionType (" + configTransactionType + ") cannot be destroyed since the TransactionRelation " + transactionRelationCollectionOrphanCheckTransactionRelation + " in its transactionRelationCollection field has a non-nullable configTransactionType field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Transaction> transactionCollection = configTransactionType.getTransactionCollection();
            for (Transaction transactionCollectionTransaction : transactionCollection) {
                transactionCollectionTransaction.setTransactionType(null);
                transactionCollectionTransaction = em.merge(transactionCollectionTransaction);
            }
            em.remove(configTransactionType);
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

    public List<ConfigTransactionType> findConfigTransactionTypeEntities() {
        return findConfigTransactionTypeEntities(true, -1, -1);
    }

    public List<ConfigTransactionType> findConfigTransactionTypeEntities(int maxResults, int firstResult) {
        return findConfigTransactionTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigTransactionType> findConfigTransactionTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigTransactionType.class));
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

    public ConfigTransactionType findConfigTransactionType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigTransactionType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigTransactionTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigTransactionType> rt = cq.from(ConfigTransactionType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
