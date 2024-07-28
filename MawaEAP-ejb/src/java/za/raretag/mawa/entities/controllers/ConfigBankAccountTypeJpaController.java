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
import za.raretag.mawa.entities.ConfigBankAccountType;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigBankAccountTypeJpaController implements Serializable {

    public ConfigBankAccountTypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigBankAccountType configBankAccountType) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configBankAccountType.getTransactionBankCollection() == null) {
            configBankAccountType.setTransactionBankCollection(new ArrayList<TransactionBank>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionBank> attachedTransactionBankCollection = new ArrayList<TransactionBank>();
            for (TransactionBank transactionBankCollectionTransactionBankToAttach : configBankAccountType.getTransactionBankCollection()) {
                transactionBankCollectionTransactionBankToAttach = em.getReference(transactionBankCollectionTransactionBankToAttach.getClass(), transactionBankCollectionTransactionBankToAttach.getTransactionBankId());
                attachedTransactionBankCollection.add(transactionBankCollectionTransactionBankToAttach);
            }
            configBankAccountType.setTransactionBankCollection(attachedTransactionBankCollection);
            em.persist(configBankAccountType);
            for (TransactionBank transactionBankCollectionTransactionBank : configBankAccountType.getTransactionBankCollection()) {
                ConfigBankAccountType oldAccountTypeOfTransactionBankCollectionTransactionBank = transactionBankCollectionTransactionBank.getAccountType();
                transactionBankCollectionTransactionBank.setAccountType(configBankAccountType);
                transactionBankCollectionTransactionBank = em.merge(transactionBankCollectionTransactionBank);
                if (oldAccountTypeOfTransactionBankCollectionTransactionBank != null) {
                    oldAccountTypeOfTransactionBankCollectionTransactionBank.getTransactionBankCollection().remove(transactionBankCollectionTransactionBank);
                    oldAccountTypeOfTransactionBankCollectionTransactionBank = em.merge(oldAccountTypeOfTransactionBankCollectionTransactionBank);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigBankAccountType(configBankAccountType.getId()) != null) {
                throw new PreexistingEntityException("ConfigBankAccountType " + configBankAccountType + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigBankAccountType configBankAccountType) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigBankAccountType persistentConfigBankAccountType = em.find(ConfigBankAccountType.class, configBankAccountType.getId());
            Collection<TransactionBank> transactionBankCollectionOld = persistentConfigBankAccountType.getTransactionBankCollection();
            Collection<TransactionBank> transactionBankCollectionNew = configBankAccountType.getTransactionBankCollection();
            Collection<TransactionBank> attachedTransactionBankCollectionNew = new ArrayList<TransactionBank>();
            for (TransactionBank transactionBankCollectionNewTransactionBankToAttach : transactionBankCollectionNew) {
                transactionBankCollectionNewTransactionBankToAttach = em.getReference(transactionBankCollectionNewTransactionBankToAttach.getClass(), transactionBankCollectionNewTransactionBankToAttach.getTransactionBankId());
                attachedTransactionBankCollectionNew.add(transactionBankCollectionNewTransactionBankToAttach);
            }
            transactionBankCollectionNew = attachedTransactionBankCollectionNew;
            configBankAccountType.setTransactionBankCollection(transactionBankCollectionNew);
            configBankAccountType = em.merge(configBankAccountType);
            for (TransactionBank transactionBankCollectionOldTransactionBank : transactionBankCollectionOld) {
                if (!transactionBankCollectionNew.contains(transactionBankCollectionOldTransactionBank)) {
                    transactionBankCollectionOldTransactionBank.setAccountType(null);
                    transactionBankCollectionOldTransactionBank = em.merge(transactionBankCollectionOldTransactionBank);
                }
            }
            for (TransactionBank transactionBankCollectionNewTransactionBank : transactionBankCollectionNew) {
                if (!transactionBankCollectionOld.contains(transactionBankCollectionNewTransactionBank)) {
                    ConfigBankAccountType oldAccountTypeOfTransactionBankCollectionNewTransactionBank = transactionBankCollectionNewTransactionBank.getAccountType();
                    transactionBankCollectionNewTransactionBank.setAccountType(configBankAccountType);
                    transactionBankCollectionNewTransactionBank = em.merge(transactionBankCollectionNewTransactionBank);
                    if (oldAccountTypeOfTransactionBankCollectionNewTransactionBank != null && !oldAccountTypeOfTransactionBankCollectionNewTransactionBank.equals(configBankAccountType)) {
                        oldAccountTypeOfTransactionBankCollectionNewTransactionBank.getTransactionBankCollection().remove(transactionBankCollectionNewTransactionBank);
                        oldAccountTypeOfTransactionBankCollectionNewTransactionBank = em.merge(oldAccountTypeOfTransactionBankCollectionNewTransactionBank);
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
                String id = configBankAccountType.getId();
                if (findConfigBankAccountType(id) == null) {
                    throw new NonexistentEntityException("The configBankAccountType with id " + id + " no longer exists.");
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
            ConfigBankAccountType configBankAccountType;
            try {
                configBankAccountType = em.getReference(ConfigBankAccountType.class, id);
                configBankAccountType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configBankAccountType with id " + id + " no longer exists.", enfe);
            }
            Collection<TransactionBank> transactionBankCollection = configBankAccountType.getTransactionBankCollection();
            for (TransactionBank transactionBankCollectionTransactionBank : transactionBankCollection) {
                transactionBankCollectionTransactionBank.setAccountType(null);
                transactionBankCollectionTransactionBank = em.merge(transactionBankCollectionTransactionBank);
            }
            em.remove(configBankAccountType);
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

    public List<ConfigBankAccountType> findConfigBankAccountTypeEntities() {
        return findConfigBankAccountTypeEntities(true, -1, -1);
    }

    public List<ConfigBankAccountType> findConfigBankAccountTypeEntities(int maxResults, int firstResult) {
        return findConfigBankAccountTypeEntities(false, maxResults, firstResult);
    }

    private List<ConfigBankAccountType> findConfigBankAccountTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigBankAccountType.class));
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

    public ConfigBankAccountType findConfigBankAccountType(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigBankAccountType.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigBankAccountTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigBankAccountType> rt = cq.from(ConfigBankAccountType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
