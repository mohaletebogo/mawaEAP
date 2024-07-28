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
import za.raretag.mawa.entities.MessageStatus;
import za.raretag.mawa.entities.MessageStatusPK;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class MessageStatusJpaController implements Serializable {

    public MessageStatusJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MessageStatus messageStatus) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (messageStatus.getMessageStatusPK() == null) {
            messageStatus.setMessageStatusPK(new MessageStatusPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(messageStatus);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findMessageStatus(messageStatus.getMessageStatusPK()) != null) {
                throw new PreexistingEntityException("MessageStatus " + messageStatus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MessageStatus messageStatus) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            messageStatus = em.merge(messageStatus);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MessageStatusPK id = messageStatus.getMessageStatusPK();
                if (findMessageStatus(id) == null) {
                    throw new NonexistentEntityException("The messageStatus with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MessageStatusPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MessageStatus messageStatus;
            try {
                messageStatus = em.getReference(MessageStatus.class, id);
                messageStatus.getMessageStatusPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The messageStatus with id " + id + " no longer exists.", enfe);
            }
            em.remove(messageStatus);
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

    public List<MessageStatus> findMessageStatusEntities() {
        return findMessageStatusEntities(true, -1, -1);
    }

    public List<MessageStatus> findMessageStatusEntities(int maxResults, int firstResult) {
        return findMessageStatusEntities(false, maxResults, firstResult);
    }

    private List<MessageStatus> findMessageStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MessageStatus.class));
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

    public MessageStatus findMessageStatus(MessageStatusPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MessageStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessageStatusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MessageStatus> rt = cq.from(MessageStatus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
