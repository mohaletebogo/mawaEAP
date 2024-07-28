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
import za.raretag.mawa.entities.ConfigBankAccountUsage;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.ConfigBankName;
import za.raretag.mawa.entities.ConfigBankAccountType;
import za.raretag.mawa.entities.TransactionBank;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionBankJpaController implements Serializable {

    public TransactionBankJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionBank transactionBank) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigBankAccountUsage usageType = transactionBank.getUsageType();
            if (usageType != null) {
                usageType = em.getReference(usageType.getClass(), usageType.getId());
                transactionBank.setUsageType(usageType);
            }
            Transaction transactionNo = transactionBank.getTransactionNo();
            if (transactionNo != null) {
                transactionNo = em.getReference(transactionNo.getClass(), transactionNo.getTransactionNo());
                transactionBank.setTransactionNo(transactionNo);
            }
            ConfigBankName bankName = transactionBank.getBankName();
            if (bankName != null) {
                bankName = em.getReference(bankName.getClass(), bankName.getId());
                transactionBank.setBankName(bankName);
            }
            ConfigBankAccountType accountType = transactionBank.getAccountType();
            if (accountType != null) {
                accountType = em.getReference(accountType.getClass(), accountType.getId());
                transactionBank.setAccountType(accountType);
            }
            em.persist(transactionBank);
            if (usageType != null) {
                usageType.getTransactionBankCollection().add(transactionBank);
                usageType = em.merge(usageType);
            }
            if (transactionNo != null) {
                transactionNo.getTransactionBankCollection().add(transactionBank);
                transactionNo = em.merge(transactionNo);
            }
            if (bankName != null) {
                bankName.getTransactionBankCollection().add(transactionBank);
                bankName = em.merge(bankName);
            }
            if (accountType != null) {
                accountType.getTransactionBankCollection().add(transactionBank);
                accountType = em.merge(accountType);
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

    public void edit(TransactionBank transactionBank) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionBank persistentTransactionBank = em.find(TransactionBank.class, transactionBank.getTransactionBankId());
            ConfigBankAccountUsage usageTypeOld = persistentTransactionBank.getUsageType();
            ConfigBankAccountUsage usageTypeNew = transactionBank.getUsageType();
            Transaction transactionNoOld = persistentTransactionBank.getTransactionNo();
            Transaction transactionNoNew = transactionBank.getTransactionNo();
            ConfigBankName bankNameOld = persistentTransactionBank.getBankName();
            ConfigBankName bankNameNew = transactionBank.getBankName();
            ConfigBankAccountType accountTypeOld = persistentTransactionBank.getAccountType();
            ConfigBankAccountType accountTypeNew = transactionBank.getAccountType();
            if (usageTypeNew != null) {
                usageTypeNew = em.getReference(usageTypeNew.getClass(), usageTypeNew.getId());
                transactionBank.setUsageType(usageTypeNew);
            }
            if (transactionNoNew != null) {
                transactionNoNew = em.getReference(transactionNoNew.getClass(), transactionNoNew.getTransactionNo());
                transactionBank.setTransactionNo(transactionNoNew);
            }
            if (bankNameNew != null) {
                bankNameNew = em.getReference(bankNameNew.getClass(), bankNameNew.getId());
                transactionBank.setBankName(bankNameNew);
            }
            if (accountTypeNew != null) {
                accountTypeNew = em.getReference(accountTypeNew.getClass(), accountTypeNew.getId());
                transactionBank.setAccountType(accountTypeNew);
            }
            transactionBank = em.merge(transactionBank);
            if (usageTypeOld != null && !usageTypeOld.equals(usageTypeNew)) {
                usageTypeOld.getTransactionBankCollection().remove(transactionBank);
                usageTypeOld = em.merge(usageTypeOld);
            }
            if (usageTypeNew != null && !usageTypeNew.equals(usageTypeOld)) {
                usageTypeNew.getTransactionBankCollection().add(transactionBank);
                usageTypeNew = em.merge(usageTypeNew);
            }
            if (transactionNoOld != null && !transactionNoOld.equals(transactionNoNew)) {
                transactionNoOld.getTransactionBankCollection().remove(transactionBank);
                transactionNoOld = em.merge(transactionNoOld);
            }
            if (transactionNoNew != null && !transactionNoNew.equals(transactionNoOld)) {
                transactionNoNew.getTransactionBankCollection().add(transactionBank);
                transactionNoNew = em.merge(transactionNoNew);
            }
            if (bankNameOld != null && !bankNameOld.equals(bankNameNew)) {
                bankNameOld.getTransactionBankCollection().remove(transactionBank);
                bankNameOld = em.merge(bankNameOld);
            }
            if (bankNameNew != null && !bankNameNew.equals(bankNameOld)) {
                bankNameNew.getTransactionBankCollection().add(transactionBank);
                bankNameNew = em.merge(bankNameNew);
            }
            if (accountTypeOld != null && !accountTypeOld.equals(accountTypeNew)) {
                accountTypeOld.getTransactionBankCollection().remove(transactionBank);
                accountTypeOld = em.merge(accountTypeOld);
            }
            if (accountTypeNew != null && !accountTypeNew.equals(accountTypeOld)) {
                accountTypeNew.getTransactionBankCollection().add(transactionBank);
                accountTypeNew = em.merge(accountTypeNew);
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
                Integer id = transactionBank.getTransactionBankId();
                if (findTransactionBank(id) == null) {
                    throw new NonexistentEntityException("The transactionBank with id " + id + " no longer exists.");
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
            TransactionBank transactionBank;
            try {
                transactionBank = em.getReference(TransactionBank.class, id);
                transactionBank.getTransactionBankId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionBank with id " + id + " no longer exists.", enfe);
            }
            ConfigBankAccountUsage usageType = transactionBank.getUsageType();
            if (usageType != null) {
                usageType.getTransactionBankCollection().remove(transactionBank);
                usageType = em.merge(usageType);
            }
            Transaction transactionNo = transactionBank.getTransactionNo();
            if (transactionNo != null) {
                transactionNo.getTransactionBankCollection().remove(transactionBank);
                transactionNo = em.merge(transactionNo);
            }
            ConfigBankName bankName = transactionBank.getBankName();
            if (bankName != null) {
                bankName.getTransactionBankCollection().remove(transactionBank);
                bankName = em.merge(bankName);
            }
            ConfigBankAccountType accountType = transactionBank.getAccountType();
            if (accountType != null) {
                accountType.getTransactionBankCollection().remove(transactionBank);
                accountType = em.merge(accountType);
            }
            em.remove(transactionBank);
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

    public List<TransactionBank> findTransactionBankEntities() {
        return findTransactionBankEntities(true, -1, -1);
    }

    public List<TransactionBank> findTransactionBankEntities(int maxResults, int firstResult) {
        return findTransactionBankEntities(false, maxResults, firstResult);
    }

    private List<TransactionBank> findTransactionBankEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionBank.class));
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

    public TransactionBank findTransactionBank(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionBank.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionBankCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionBank> rt = cq.from(TransactionBank.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
