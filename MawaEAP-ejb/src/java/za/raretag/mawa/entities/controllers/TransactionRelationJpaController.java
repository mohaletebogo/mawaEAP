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
import za.raretag.mawa.entities.ConfigTransactionType;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionRelation;
import za.raretag.mawa.entities.TransactionRelationPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionRelationJpaController implements Serializable {

    public TransactionRelationJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionRelation transactionRelation) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transactionRelation.getTransactionRelationPK() == null) {
            transactionRelation.setTransactionRelationPK(new TransactionRelationPK());
        }
        transactionRelation.getTransactionRelationPK().setTransactionNo2(transactionRelation.getTransaction1().getTransactionNo());
        transactionRelation.getTransactionRelationPK().setTransactionNo1(transactionRelation.getTransaction().getTransactionNo());
        transactionRelation.getTransactionRelationPK().setType(transactionRelation.getConfigTransactionType().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConfigTransactionType configTransactionType = transactionRelation.getConfigTransactionType();
            if (configTransactionType != null) {
                configTransactionType = em.getReference(configTransactionType.getClass(), configTransactionType.getId());
                transactionRelation.setConfigTransactionType(configTransactionType);
            }
            Transaction transaction = transactionRelation.getTransaction();
            if (transaction != null) {
                transaction = em.getReference(transaction.getClass(), transaction.getTransactionNo());
                transactionRelation.setTransaction(transaction);
            }
            Transaction transaction1 = transactionRelation.getTransaction1();
            if (transaction1 != null) {
                transaction1 = em.getReference(transaction1.getClass(), transaction1.getTransactionNo());
                transactionRelation.setTransaction1(transaction1);
            }
            em.persist(transactionRelation);
            if (configTransactionType != null) {
                configTransactionType.getTransactionRelationCollection().add(transactionRelation);
                configTransactionType = em.merge(configTransactionType);
            }
            if (transaction != null) {
                transaction.getTransactionRelationCollection().add(transactionRelation);
                transaction = em.merge(transaction);
            }
            if (transaction1 != null) {
                transaction1.getTransactionRelationCollection().add(transactionRelation);
                transaction1 = em.merge(transaction1);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransactionRelation(transactionRelation.getTransactionRelationPK()) != null) {
                throw new PreexistingEntityException("TransactionRelation " + transactionRelation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionRelation transactionRelation) throws NonexistentEntityException, RollbackFailureException, Exception {
        transactionRelation.getTransactionRelationPK().setTransactionNo2(transactionRelation.getTransaction1().getTransactionNo());
        transactionRelation.getTransactionRelationPK().setTransactionNo1(transactionRelation.getTransaction().getTransactionNo());
        transactionRelation.getTransactionRelationPK().setType(transactionRelation.getConfigTransactionType().getId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionRelation persistentTransactionRelation = em.find(TransactionRelation.class, transactionRelation.getTransactionRelationPK());
            ConfigTransactionType configTransactionTypeOld = persistentTransactionRelation.getConfigTransactionType();
            ConfigTransactionType configTransactionTypeNew = transactionRelation.getConfigTransactionType();
            Transaction transactionOld = persistentTransactionRelation.getTransaction();
            Transaction transactionNew = transactionRelation.getTransaction();
            Transaction transaction1Old = persistentTransactionRelation.getTransaction1();
            Transaction transaction1New = transactionRelation.getTransaction1();
            if (configTransactionTypeNew != null) {
                configTransactionTypeNew = em.getReference(configTransactionTypeNew.getClass(), configTransactionTypeNew.getId());
                transactionRelation.setConfigTransactionType(configTransactionTypeNew);
            }
            if (transactionNew != null) {
                transactionNew = em.getReference(transactionNew.getClass(), transactionNew.getTransactionNo());
                transactionRelation.setTransaction(transactionNew);
            }
            if (transaction1New != null) {
                transaction1New = em.getReference(transaction1New.getClass(), transaction1New.getTransactionNo());
                transactionRelation.setTransaction1(transaction1New);
            }
            transactionRelation = em.merge(transactionRelation);
            if (configTransactionTypeOld != null && !configTransactionTypeOld.equals(configTransactionTypeNew)) {
                configTransactionTypeOld.getTransactionRelationCollection().remove(transactionRelation);
                configTransactionTypeOld = em.merge(configTransactionTypeOld);
            }
            if (configTransactionTypeNew != null && !configTransactionTypeNew.equals(configTransactionTypeOld)) {
                configTransactionTypeNew.getTransactionRelationCollection().add(transactionRelation);
                configTransactionTypeNew = em.merge(configTransactionTypeNew);
            }
            if (transactionOld != null && !transactionOld.equals(transactionNew)) {
                transactionOld.getTransactionRelationCollection().remove(transactionRelation);
                transactionOld = em.merge(transactionOld);
            }
            if (transactionNew != null && !transactionNew.equals(transactionOld)) {
                transactionNew.getTransactionRelationCollection().add(transactionRelation);
                transactionNew = em.merge(transactionNew);
            }
            if (transaction1Old != null && !transaction1Old.equals(transaction1New)) {
                transaction1Old.getTransactionRelationCollection().remove(transactionRelation);
                transaction1Old = em.merge(transaction1Old);
            }
            if (transaction1New != null && !transaction1New.equals(transaction1Old)) {
                transaction1New.getTransactionRelationCollection().add(transactionRelation);
                transaction1New = em.merge(transaction1New);
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
                TransactionRelationPK id = transactionRelation.getTransactionRelationPK();
                if (findTransactionRelation(id) == null) {
                    throw new NonexistentEntityException("The transactionRelation with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TransactionRelationPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionRelation transactionRelation;
            try {
                transactionRelation = em.getReference(TransactionRelation.class, id);
                transactionRelation.getTransactionRelationPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionRelation with id " + id + " no longer exists.", enfe);
            }
            ConfigTransactionType configTransactionType = transactionRelation.getConfigTransactionType();
            if (configTransactionType != null) {
                configTransactionType.getTransactionRelationCollection().remove(transactionRelation);
                configTransactionType = em.merge(configTransactionType);
            }
            Transaction transaction = transactionRelation.getTransaction();
            if (transaction != null) {
                transaction.getTransactionRelationCollection().remove(transactionRelation);
                transaction = em.merge(transaction);
            }
            Transaction transaction1 = transactionRelation.getTransaction1();
            if (transaction1 != null) {
                transaction1.getTransactionRelationCollection().remove(transactionRelation);
                transaction1 = em.merge(transaction1);
            }
            em.remove(transactionRelation);
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

    public List<TransactionRelation> findTransactionRelationEntities() {
        return findTransactionRelationEntities(true, -1, -1);
    }

    public List<TransactionRelation> findTransactionRelationEntities(int maxResults, int firstResult) {
        return findTransactionRelationEntities(false, maxResults, firstResult);
    }

    private List<TransactionRelation> findTransactionRelationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionRelation.class));
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

    public TransactionRelation findTransactionRelation(TransactionRelationPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionRelation.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionRelationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionRelation> rt = cq.from(TransactionRelation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
