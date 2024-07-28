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
import za.raretag.mawa.entities.ConfigBankName;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class ConfigBankNameJpaController implements Serializable {

    public ConfigBankNameJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConfigBankName configBankName) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (configBankName.getTransactionBankCollection() == null) {
            configBankName.setTransactionBankCollection(new ArrayList<TransactionBank>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionBank> attachedTransactionBankCollection = new ArrayList<TransactionBank>();
            for (TransactionBank transactionBankCollectionTransactionBankToAttach : configBankName.getTransactionBankCollection()) {
                transactionBankCollectionTransactionBankToAttach = em.getReference(transactionBankCollectionTransactionBankToAttach.getClass(), transactionBankCollectionTransactionBankToAttach.getTransactionBankId());
                attachedTransactionBankCollection.add(transactionBankCollectionTransactionBankToAttach);
            }
            configBankName.setTransactionBankCollection(attachedTransactionBankCollection);
            em.persist(configBankName);
            for (TransactionBank transactionBankCollectionTransactionBank : configBankName.getTransactionBankCollection()) {
                ConfigBankName oldBankNameOfTransactionBankCollectionTransactionBank = transactionBankCollectionTransactionBank.getBankName();
                transactionBankCollectionTransactionBank.setBankName(configBankName);
                transactionBankCollectionTransactionBank = em.merge(transactionBankCollectionTransactionBank);
                if (oldBankNameOfTransactionBankCollectionTransactionBank != null) {
                    oldBankNameOfTransactionBankCollectionTransactionBank.getTransactionBankCollection().remove(transactionBankCollectionTransactionBank);
                    oldBankNameOfTransactionBankCollectionTransactionBank = em.merge(oldBankNameOfTransactionBankCollectionTransactionBank);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConfigBankName(configBankName.getId()) != null) {
                throw new PreexistingEntityException("ConfigBankName " + configBankName + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConfigBankName configBankName) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigBankName persistentConfigBankName = em.find(ConfigBankName.class, configBankName.getId());
            Collection<TransactionBank> transactionBankCollectionOld = persistentConfigBankName.getTransactionBankCollection();
            Collection<TransactionBank> transactionBankCollectionNew = configBankName.getTransactionBankCollection();
            Collection<TransactionBank> attachedTransactionBankCollectionNew = new ArrayList<TransactionBank>();
            for (TransactionBank transactionBankCollectionNewTransactionBankToAttach : transactionBankCollectionNew) {
                transactionBankCollectionNewTransactionBankToAttach = em.getReference(transactionBankCollectionNewTransactionBankToAttach.getClass(), transactionBankCollectionNewTransactionBankToAttach.getTransactionBankId());
                attachedTransactionBankCollectionNew.add(transactionBankCollectionNewTransactionBankToAttach);
            }
            transactionBankCollectionNew = attachedTransactionBankCollectionNew;
            configBankName.setTransactionBankCollection(transactionBankCollectionNew);
            configBankName = em.merge(configBankName);
            for (TransactionBank transactionBankCollectionOldTransactionBank : transactionBankCollectionOld) {
                if (!transactionBankCollectionNew.contains(transactionBankCollectionOldTransactionBank)) {
                    transactionBankCollectionOldTransactionBank.setBankName(null);
                    transactionBankCollectionOldTransactionBank = em.merge(transactionBankCollectionOldTransactionBank);
                }
            }
            for (TransactionBank transactionBankCollectionNewTransactionBank : transactionBankCollectionNew) {
                if (!transactionBankCollectionOld.contains(transactionBankCollectionNewTransactionBank)) {
                    ConfigBankName oldBankNameOfTransactionBankCollectionNewTransactionBank = transactionBankCollectionNewTransactionBank.getBankName();
                    transactionBankCollectionNewTransactionBank.setBankName(configBankName);
                    transactionBankCollectionNewTransactionBank = em.merge(transactionBankCollectionNewTransactionBank);
                    if (oldBankNameOfTransactionBankCollectionNewTransactionBank != null && !oldBankNameOfTransactionBankCollectionNewTransactionBank.equals(configBankName)) {
                        oldBankNameOfTransactionBankCollectionNewTransactionBank.getTransactionBankCollection().remove(transactionBankCollectionNewTransactionBank);
                        oldBankNameOfTransactionBankCollectionNewTransactionBank = em.merge(oldBankNameOfTransactionBankCollectionNewTransactionBank);
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
                String id = configBankName.getId();
                if (findConfigBankName(id) == null) {
                    throw new NonexistentEntityException("The configBankName with id " + id + " no longer exists.");
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
            ConfigBankName configBankName;
            try {
                configBankName = em.getReference(ConfigBankName.class, id);
                configBankName.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configBankName with id " + id + " no longer exists.", enfe);
            }
            Collection<TransactionBank> transactionBankCollection = configBankName.getTransactionBankCollection();
            for (TransactionBank transactionBankCollectionTransactionBank : transactionBankCollection) {
                transactionBankCollectionTransactionBank.setBankName(null);
                transactionBankCollectionTransactionBank = em.merge(transactionBankCollectionTransactionBank);
            }
            em.remove(configBankName);
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

    public List<ConfigBankName> findConfigBankNameEntities() {
        return findConfigBankNameEntities(true, -1, -1);
    }

    public List<ConfigBankName> findConfigBankNameEntities(int maxResults, int firstResult) {
        return findConfigBankNameEntities(false, maxResults, firstResult);
    }

    private List<ConfigBankName> findConfigBankNameEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConfigBankName.class));
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

    public ConfigBankName findConfigBankName(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConfigBankName.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigBankNameCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConfigBankName> rt = cq.from(ConfigBankName.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
