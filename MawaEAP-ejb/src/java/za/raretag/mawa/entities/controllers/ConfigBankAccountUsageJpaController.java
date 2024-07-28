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
import za.raretag.mawa.entities.TransactionBank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.ConfigBankAccountUsage;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigBankAccountUsageJpaController implements Serializable {

    public ConfigBankAccountUsageJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigBankAccountUsage configBankAccountUsage) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configBankAccountUsage.getTransactionBankCollection() == null) {
            configBankAccountUsage.setTransactionBankCollection(new ArrayList<TransactionBank>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionBank> attachedTransactionBankCollection = new ArrayList<TransactionBank>();
            for (TransactionBank transactionBankCollectionTransactionBankToAttach : configBankAccountUsage.getTransactionBankCollection()) {
                transactionBankCollectionTransactionBankToAttach = em.getReference(transactionBankCollectionTransactionBankToAttach.getClass(), transactionBankCollectionTransactionBankToAttach.getTransactionBankId());
                attachedTransactionBankCollection.add(transactionBankCollectionTransactionBankToAttach);
            }
            configBankAccountUsage.setTransactionBankCollection(attachedTransactionBankCollection);
            em.persist(configBankAccountUsage);
            for (TransactionBank transactionBankCollectionTransactionBank : configBankAccountUsage.getTransactionBankCollection()) {
                ConfigBankAccountUsage oldUsageTypeOfTransactionBankCollectionTransactionBank = transactionBankCollectionTransactionBank.getUsageType();
                transactionBankCollectionTransactionBank.setUsageType(configBankAccountUsage);
                transactionBankCollectionTransactionBank = em.merge(transactionBankCollectionTransactionBank);
                if (oldUsageTypeOfTransactionBankCollectionTransactionBank != null) {
                    oldUsageTypeOfTransactionBankCollectionTransactionBank.getTransactionBankCollection().remove(transactionBankCollectionTransactionBank);
                    oldUsageTypeOfTransactionBankCollectionTransactionBank = em.merge(oldUsageTypeOfTransactionBankCollectionTransactionBank);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigBankAccountUsage(configBankAccountUsage.getId()) != null) {
                throw new PreexistingEntityException("ConfigBankAccountUsage " + configBankAccountUsage + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigBankAccountUsage configBankAccountUsage) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigBankAccountUsage persistentConfigBankAccountUsage = em.find(ConfigBankAccountUsage.class, configBankAccountUsage.getId());
            Collection<TransactionBank> transactionBankCollectionOld = persistentConfigBankAccountUsage.getTransactionBankCollection();
            Collection<TransactionBank> transactionBankCollectionNew = configBankAccountUsage.getTransactionBankCollection();
            Collection<TransactionBank> attachedTransactionBankCollectionNew = new ArrayList<TransactionBank>();
            for (TransactionBank transactionBankCollectionNewTransactionBankToAttach : transactionBankCollectionNew) {
                transactionBankCollectionNewTransactionBankToAttach = em.getReference(transactionBankCollectionNewTransactionBankToAttach.getClass(), transactionBankCollectionNewTransactionBankToAttach.getTransactionBankId());
                attachedTransactionBankCollectionNew.add(transactionBankCollectionNewTransactionBankToAttach);
            }
            transactionBankCollectionNew = attachedTransactionBankCollectionNew;
            configBankAccountUsage.setTransactionBankCollection(transactionBankCollectionNew);
            configBankAccountUsage = em.merge(configBankAccountUsage);
            for (TransactionBank transactionBankCollectionOldTransactionBank : transactionBankCollectionOld) {
                if (!transactionBankCollectionNew.contains(transactionBankCollectionOldTransactionBank)) {
                    transactionBankCollectionOldTransactionBank.setUsageType(null);
                    transactionBankCollectionOldTransactionBank = em.merge(transactionBankCollectionOldTransactionBank);
                }
            }
            for (TransactionBank transactionBankCollectionNewTransactionBank : transactionBankCollectionNew) {
                if (!transactionBankCollectionOld.contains(transactionBankCollectionNewTransactionBank)) {
                    ConfigBankAccountUsage oldUsageTypeOfTransactionBankCollectionNewTransactionBank = transactionBankCollectionNewTransactionBank.getUsageType();
                    transactionBankCollectionNewTransactionBank.setUsageType(configBankAccountUsage);
                    transactionBankCollectionNewTransactionBank = em.merge(transactionBankCollectionNewTransactionBank);
                    if (oldUsageTypeOfTransactionBankCollectionNewTransactionBank != null && !oldUsageTypeOfTransactionBankCollectionNewTransactionBank.equals(configBankAccountUsage)) {
                        oldUsageTypeOfTransactionBankCollectionNewTransactionBank.getTransactionBankCollection().remove(transactionBankCollectionNewTransactionBank);
                        oldUsageTypeOfTransactionBankCollectionNewTransactionBank = em.merge(oldUsageTypeOfTransactionBankCollectionNewTransactionBank);
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
                String id = configBankAccountUsage.getId();
                if (findConfigBankAccountUsage(id) == null) {
                    throw new NonexistentEntityException("The configBankAccountUsage with id " + id + " no longer exists.");
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
            ConfigBankAccountUsage configBankAccountUsage;
            try {
                configBankAccountUsage = em.getReference(ConfigBankAccountUsage.class, id);
                configBankAccountUsage.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configBankAccountUsage with id " + id + " no longer exists.", enfe);
            }
            Collection<TransactionBank> transactionBankCollection = configBankAccountUsage.getTransactionBankCollection();
            for (TransactionBank transactionBankCollectionTransactionBank : transactionBankCollection) {
                transactionBankCollectionTransactionBank.setUsageType(null);
                transactionBankCollectionTransactionBank = em.merge(transactionBankCollectionTransactionBank);
            }
            em.remove(configBankAccountUsage);
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

    public List<ConfigBankAccountUsage> findConfigBankAccountUsageEntities() {
        return findConfigBankAccountUsageEntities(true, -1, -1);
    }

    public List<ConfigBankAccountUsage> findConfigBankAccountUsageEntities(int maxResults, int firstResult) {
        return findConfigBankAccountUsageEntities(false, maxResults, firstResult);
    }

    private List<ConfigBankAccountUsage> findConfigBankAccountUsageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigBankAccountUsage.class));
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

    public ConfigBankAccountUsage findConfigBankAccountUsage(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigBankAccountUsage.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigBankAccountUsageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigBankAccountUsage> rt = cq.from(ConfigBankAccountUsage.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
