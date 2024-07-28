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
import za.raretag.mawa.entities.ConfigPartnerFunction;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.TransactionPartner;
import za.raretag.mawa.entities.TransactionPartnerPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionPartnerJpaController implements Serializable {

    public TransactionPartnerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionPartner transactionPartner) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transactionPartner.getTransactionPartnerPK() == null) {
            transactionPartner.setTransactionPartnerPK(new TransactionPartnerPK());
        }
        transactionPartner.getTransactionPartnerPK().setPartnerType(transactionPartner.getConfigPartnerFunction().getId());
        transactionPartner.getTransactionPartnerPK().setPartnerNo(transactionPartner.getPartner().getPartnerNo());
        transactionPartner.getTransactionPartnerPK().setTransactionNo(transactionPartner.getTransaction().getTransactionNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigPartnerFunction configPartnerFunction = transactionPartner.getConfigPartnerFunction();
            if (configPartnerFunction != null) {
                configPartnerFunction = em.getReference(configPartnerFunction.getClass(), configPartnerFunction.getId());
                transactionPartner.setConfigPartnerFunction(configPartnerFunction);
            }
            Transaction transaction = transactionPartner.getTransaction();
            if (transaction != null) {
                transaction = em.getReference(transaction.getClass(), transaction.getTransactionNo());
                transactionPartner.setTransaction(transaction);
            }
            Partner partner = transactionPartner.getPartner();
            if (partner != null) {
                partner = em.getReference(partner.getClass(), partner.getPartnerNo());
                transactionPartner.setPartner(partner);
            }
            em.persist(transactionPartner);
            if (configPartnerFunction != null) {
                configPartnerFunction.getTransactionPartnerCollection().add(transactionPartner);
                configPartnerFunction = em.merge(configPartnerFunction);
            }
            if (transaction != null) {
                transaction.getTransactionPartnerCollection().add(transactionPartner);
                transaction = em.merge(transaction);
            }
            if (partner != null) {
                partner.getTransactionPartnerCollection().add(transactionPartner);
                partner = em.merge(partner);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransactionPartner(transactionPartner.getTransactionPartnerPK()) != null) {
                throw new PreexistingEntityException("TransactionPartner " + transactionPartner + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionPartner transactionPartner) throws NonexistentEntityException, RollbackFailureException, Exception {
        transactionPartner.getTransactionPartnerPK().setPartnerType(transactionPartner.getConfigPartnerFunction().getId());
        transactionPartner.getTransactionPartnerPK().setPartnerNo(transactionPartner.getPartner().getPartnerNo());
        transactionPartner.getTransactionPartnerPK().setTransactionNo(transactionPartner.getTransaction().getTransactionNo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionPartner persistentTransactionPartner = em.find(TransactionPartner.class, transactionPartner.getTransactionPartnerPK());
            ConfigPartnerFunction configPartnerFunctionOld = persistentTransactionPartner.getConfigPartnerFunction();
            ConfigPartnerFunction configPartnerFunctionNew = transactionPartner.getConfigPartnerFunction();
            Transaction transactionOld = persistentTransactionPartner.getTransaction();
            Transaction transactionNew = transactionPartner.getTransaction();
            Partner partnerOld = persistentTransactionPartner.getPartner();
            Partner partnerNew = transactionPartner.getPartner();
            if (configPartnerFunctionNew != null) {
                configPartnerFunctionNew = em.getReference(configPartnerFunctionNew.getClass(), configPartnerFunctionNew.getId());
                transactionPartner.setConfigPartnerFunction(configPartnerFunctionNew);
            }
            if (transactionNew != null) {
                transactionNew = em.getReference(transactionNew.getClass(), transactionNew.getTransactionNo());
                transactionPartner.setTransaction(transactionNew);
            }
            if (partnerNew != null) {
                partnerNew = em.getReference(partnerNew.getClass(), partnerNew.getPartnerNo());
                transactionPartner.setPartner(partnerNew);
            }
            transactionPartner = em.merge(transactionPartner);
            if (configPartnerFunctionOld != null && !configPartnerFunctionOld.equals(configPartnerFunctionNew)) {
                configPartnerFunctionOld.getTransactionPartnerCollection().remove(transactionPartner);
                configPartnerFunctionOld = em.merge(configPartnerFunctionOld);
            }
            if (configPartnerFunctionNew != null && !configPartnerFunctionNew.equals(configPartnerFunctionOld)) {
                configPartnerFunctionNew.getTransactionPartnerCollection().add(transactionPartner);
                configPartnerFunctionNew = em.merge(configPartnerFunctionNew);
            }
            if (transactionOld != null && !transactionOld.equals(transactionNew)) {
                transactionOld.getTransactionPartnerCollection().remove(transactionPartner);
                transactionOld = em.merge(transactionOld);
            }
            if (transactionNew != null && !transactionNew.equals(transactionOld)) {
                transactionNew.getTransactionPartnerCollection().add(transactionPartner);
                transactionNew = em.merge(transactionNew);
            }
            if (partnerOld != null && !partnerOld.equals(partnerNew)) {
                partnerOld.getTransactionPartnerCollection().remove(transactionPartner);
                partnerOld = em.merge(partnerOld);
            }
            if (partnerNew != null && !partnerNew.equals(partnerOld)) {
                partnerNew.getTransactionPartnerCollection().add(transactionPartner);
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
                TransactionPartnerPK id = transactionPartner.getTransactionPartnerPK();
                if (findTransactionPartner(id) == null) {
                    throw new NonexistentEntityException("The transactionPartner with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TransactionPartnerPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionPartner transactionPartner;
            try {
                transactionPartner = em.getReference(TransactionPartner.class, id);
                transactionPartner.getTransactionPartnerPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionPartner with id " + id + " no longer exists.", enfe);
            }
            ConfigPartnerFunction configPartnerFunction = transactionPartner.getConfigPartnerFunction();
            if (configPartnerFunction != null) {
                configPartnerFunction.getTransactionPartnerCollection().remove(transactionPartner);
                configPartnerFunction = em.merge(configPartnerFunction);
            }
            Transaction transaction = transactionPartner.getTransaction();
            if (transaction != null) {
                transaction.getTransactionPartnerCollection().remove(transactionPartner);
                transaction = em.merge(transaction);
            }
            Partner partner = transactionPartner.getPartner();
            if (partner != null) {
                partner.getTransactionPartnerCollection().remove(transactionPartner);
                partner = em.merge(partner);
            }
            em.remove(transactionPartner);
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

    public List<TransactionPartner> findTransactionPartnerEntities() {
        return findTransactionPartnerEntities(true, -1, -1);
    }

    public List<TransactionPartner> findTransactionPartnerEntities(int maxResults, int firstResult) {
        return findTransactionPartnerEntities(false, maxResults, firstResult);
    }

    private List<TransactionPartner> findTransactionPartnerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionPartner.class));
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

    public TransactionPartner findTransactionPartner(TransactionPartnerPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionPartner.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionPartnerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionPartner> rt = cq.from(TransactionPartner.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
