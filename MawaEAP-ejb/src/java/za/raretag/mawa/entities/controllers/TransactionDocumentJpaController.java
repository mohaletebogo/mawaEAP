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
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.ConfigDocumentType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.TransactionDocument;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class TransactionDocumentJpaController implements Serializable {

    public TransactionDocumentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransactionDocument transactionDocument) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Transaction transactionOrphanCheck = transactionDocument.getTransaction();
        if (transactionOrphanCheck != null) {
            TransactionDocument oldTransactionDocumentOfTransaction = transactionOrphanCheck.getTransactionDocument();
            if (oldTransactionDocumentOfTransaction != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Transaction " + transactionOrphanCheck + " already has an item of type TransactionDocument whose transaction column cannot be null. Please make another selection for the transaction field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction transaction = transactionDocument.getTransaction();
            if (transaction != null) {
                transaction = em.getReference(transaction.getClass(), transaction.getTransactionNo());
                transactionDocument.setTransaction(transaction);
            }
            ConfigDocumentType documentType = transactionDocument.getDocumentType();
            if (documentType != null) {
                documentType = em.getReference(documentType.getClass(), documentType.getId());
                transactionDocument.setDocumentType(documentType);
            }
            em.persist(transactionDocument);
            if (transaction != null) {
                transaction.setTransactionDocument(transactionDocument);
                transaction = em.merge(transaction);
            }
            if (documentType != null) {
                documentType.getTransactionDocumentCollection().add(transactionDocument);
                documentType = em.merge(documentType);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransactionDocument(transactionDocument.getTransactionNo()) != null) {
                throw new PreexistingEntityException("TransactionDocument " + transactionDocument + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransactionDocument transactionDocument) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransactionDocument persistentTransactionDocument = em.find(TransactionDocument.class, transactionDocument.getTransactionNo());
            Transaction transactionOld = persistentTransactionDocument.getTransaction();
            Transaction transactionNew = transactionDocument.getTransaction();
            ConfigDocumentType documentTypeOld = persistentTransactionDocument.getDocumentType();
            ConfigDocumentType documentTypeNew = transactionDocument.getDocumentType();
            List<String> illegalOrphanMessages = null;
            if (transactionNew != null && !transactionNew.equals(transactionOld)) {
                TransactionDocument oldTransactionDocumentOfTransaction = transactionNew.getTransactionDocument();
                if (oldTransactionDocumentOfTransaction != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Transaction " + transactionNew + " already has an item of type TransactionDocument whose transaction column cannot be null. Please make another selection for the transaction field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transactionNew != null) {
                transactionNew = em.getReference(transactionNew.getClass(), transactionNew.getTransactionNo());
                transactionDocument.setTransaction(transactionNew);
            }
            if (documentTypeNew != null) {
                documentTypeNew = em.getReference(documentTypeNew.getClass(), documentTypeNew.getId());
                transactionDocument.setDocumentType(documentTypeNew);
            }
            transactionDocument = em.merge(transactionDocument);
            if (transactionOld != null && !transactionOld.equals(transactionNew)) {
                transactionOld.setTransactionDocument(null);
                transactionOld = em.merge(transactionOld);
            }
            if (transactionNew != null && !transactionNew.equals(transactionOld)) {
                transactionNew.setTransactionDocument(transactionDocument);
                transactionNew = em.merge(transactionNew);
            }
            if (documentTypeOld != null && !documentTypeOld.equals(documentTypeNew)) {
                documentTypeOld.getTransactionDocumentCollection().remove(transactionDocument);
                documentTypeOld = em.merge(documentTypeOld);
            }
            if (documentTypeNew != null && !documentTypeNew.equals(documentTypeOld)) {
                documentTypeNew.getTransactionDocumentCollection().add(transactionDocument);
                documentTypeNew = em.merge(documentTypeNew);
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
                String id = transactionDocument.getTransactionNo();
                if (findTransactionDocument(id) == null) {
                    throw new NonexistentEntityException("The transactionDocument with id " + id + " no longer exists.");
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
            TransactionDocument transactionDocument;
            try {
                transactionDocument = em.getReference(TransactionDocument.class, id);
                transactionDocument.getTransactionNo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transactionDocument with id " + id + " no longer exists.", enfe);
            }
            Transaction transaction = transactionDocument.getTransaction();
            if (transaction != null) {
                transaction.setTransactionDocument(null);
                transaction = em.merge(transaction);
            }
            ConfigDocumentType documentType = transactionDocument.getDocumentType();
            if (documentType != null) {
                documentType.getTransactionDocumentCollection().remove(transactionDocument);
                documentType = em.merge(documentType);
            }
            em.remove(transactionDocument);
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

    public List<TransactionDocument> findTransactionDocumentEntities() {
        return findTransactionDocumentEntities(true, -1, -1);
    }

    public List<TransactionDocument> findTransactionDocumentEntities(int maxResults, int firstResult) {
        return findTransactionDocumentEntities(false, maxResults, firstResult);
    }

    private List<TransactionDocument> findTransactionDocumentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransactionDocument.class));
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

    public TransactionDocument findTransactionDocument(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransactionDocument.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionDocumentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransactionDocument> rt = cq.from(TransactionDocument.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
