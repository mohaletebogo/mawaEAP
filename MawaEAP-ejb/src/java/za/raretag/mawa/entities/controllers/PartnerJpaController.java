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
import za.raretag.mawa.entities.TransactionPayment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import za.raretag.mawa.entities.PartnerRelation;
import za.raretag.mawa.entities.PartnerRole;
import za.raretag.mawa.entities.TransactionPartner;
import za.raretag.mawa.entities.PartnerAddress;
import za.raretag.mawa.entities.CheckOut;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerAttribute;
import za.raretag.mawa.entities.PartnerContact;
import za.raretag.mawa.entities.PartnerIdentity;
import za.raretag.mawa.entities.controllers.exceptions.IllegalOrphanException;
import za.raretag.mawa.entities.controllers.exceptions.NonexistentEntityException;
import za.raretag.mawa.entities.controllers.exceptions.PreexistingEntityException;
import za.raretag.mawa.entities.controllers.exceptions.RollbackFailureException;

/**
 *
 * @author tebogom
 */
public class PartnerJpaController implements Serializable {

    public PartnerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partner partner) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (partner.getTransactionPaymentCollection() == null) {
            partner.setTransactionPaymentCollection(new ArrayList<TransactionPayment>());
        }
        if (partner.getTransactionPaymentCollection1() == null) {
            partner.setTransactionPaymentCollection1(new ArrayList<TransactionPayment>());
        }
        if (partner.getPartnerRelationCollection() == null) {
            partner.setPartnerRelationCollection(new ArrayList<PartnerRelation>());
        }
        if (partner.getPartnerRelationCollection1() == null) {
            partner.setPartnerRelationCollection1(new ArrayList<PartnerRelation>());
        }
        if (partner.getPartnerRoleCollection() == null) {
            partner.setPartnerRoleCollection(new ArrayList<PartnerRole>());
        }
        if (partner.getTransactionPartnerCollection() == null) {
            partner.setTransactionPartnerCollection(new ArrayList<TransactionPartner>());
        }
        if (partner.getPartnerAddressCollection() == null) {
            partner.setPartnerAddressCollection(new ArrayList<PartnerAddress>());
        }
        if (partner.getCheckOutCollection() == null) {
            partner.setCheckOutCollection(new ArrayList<CheckOut>());
        }
        if (partner.getCheckOutCollection1() == null) {
            partner.setCheckOutCollection1(new ArrayList<CheckOut>());
        }
        if (partner.getPartnerAttributeCollection() == null) {
            partner.setPartnerAttributeCollection(new ArrayList<PartnerAttribute>());
        }
        if (partner.getPartnerContactCollection() == null) {
            partner.setPartnerContactCollection(new ArrayList<PartnerContact>());
        }
        if (partner.getPartnerIdentityCollection() == null) {
            partner.setPartnerIdentityCollection(new ArrayList<PartnerIdentity>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransactionPayment> attachedTransactionPaymentCollection = new ArrayList<TransactionPayment>();
            for (TransactionPayment transactionPaymentCollectionTransactionPaymentToAttach : partner.getTransactionPaymentCollection()) {
                transactionPaymentCollectionTransactionPaymentToAttach = em.getReference(transactionPaymentCollectionTransactionPaymentToAttach.getClass(), transactionPaymentCollectionTransactionPaymentToAttach.getPaymentNo());
                attachedTransactionPaymentCollection.add(transactionPaymentCollectionTransactionPaymentToAttach);
            }
            partner.setTransactionPaymentCollection(attachedTransactionPaymentCollection);
            Collection<TransactionPayment> attachedTransactionPaymentCollection1 = new ArrayList<TransactionPayment>();
            for (TransactionPayment transactionPaymentCollection1TransactionPaymentToAttach : partner.getTransactionPaymentCollection1()) {
                transactionPaymentCollection1TransactionPaymentToAttach = em.getReference(transactionPaymentCollection1TransactionPaymentToAttach.getClass(), transactionPaymentCollection1TransactionPaymentToAttach.getPaymentNo());
                attachedTransactionPaymentCollection1.add(transactionPaymentCollection1TransactionPaymentToAttach);
            }
            partner.setTransactionPaymentCollection1(attachedTransactionPaymentCollection1);
            Collection<PartnerRelation> attachedPartnerRelationCollection = new ArrayList<PartnerRelation>();
            for (PartnerRelation partnerRelationCollectionPartnerRelationToAttach : partner.getPartnerRelationCollection()) {
                partnerRelationCollectionPartnerRelationToAttach = em.getReference(partnerRelationCollectionPartnerRelationToAttach.getClass(), partnerRelationCollectionPartnerRelationToAttach.getPartnerRelationPK());
                attachedPartnerRelationCollection.add(partnerRelationCollectionPartnerRelationToAttach);
            }
            partner.setPartnerRelationCollection(attachedPartnerRelationCollection);
            Collection<PartnerRelation> attachedPartnerRelationCollection1 = new ArrayList<PartnerRelation>();
            for (PartnerRelation partnerRelationCollection1PartnerRelationToAttach : partner.getPartnerRelationCollection1()) {
                partnerRelationCollection1PartnerRelationToAttach = em.getReference(partnerRelationCollection1PartnerRelationToAttach.getClass(), partnerRelationCollection1PartnerRelationToAttach.getPartnerRelationPK());
                attachedPartnerRelationCollection1.add(partnerRelationCollection1PartnerRelationToAttach);
            }
            partner.setPartnerRelationCollection1(attachedPartnerRelationCollection1);
            Collection<PartnerRole> attachedPartnerRoleCollection = new ArrayList<PartnerRole>();
            for (PartnerRole partnerRoleCollectionPartnerRoleToAttach : partner.getPartnerRoleCollection()) {
                partnerRoleCollectionPartnerRoleToAttach = em.getReference(partnerRoleCollectionPartnerRoleToAttach.getClass(), partnerRoleCollectionPartnerRoleToAttach.getPartnerRolePK());
                attachedPartnerRoleCollection.add(partnerRoleCollectionPartnerRoleToAttach);
            }
            partner.setPartnerRoleCollection(attachedPartnerRoleCollection);
            Collection<TransactionPartner> attachedTransactionPartnerCollection = new ArrayList<TransactionPartner>();
            for (TransactionPartner transactionPartnerCollectionTransactionPartnerToAttach : partner.getTransactionPartnerCollection()) {
                transactionPartnerCollectionTransactionPartnerToAttach = em.getReference(transactionPartnerCollectionTransactionPartnerToAttach.getClass(), transactionPartnerCollectionTransactionPartnerToAttach.getTransactionPartnerPK());
                attachedTransactionPartnerCollection.add(transactionPartnerCollectionTransactionPartnerToAttach);
            }
            partner.setTransactionPartnerCollection(attachedTransactionPartnerCollection);
            Collection<PartnerAddress> attachedPartnerAddressCollection = new ArrayList<PartnerAddress>();
            for (PartnerAddress partnerAddressCollectionPartnerAddressToAttach : partner.getPartnerAddressCollection()) {
                partnerAddressCollectionPartnerAddressToAttach = em.getReference(partnerAddressCollectionPartnerAddressToAttach.getClass(), partnerAddressCollectionPartnerAddressToAttach.getAddressId());
                attachedPartnerAddressCollection.add(partnerAddressCollectionPartnerAddressToAttach);
            }
            partner.setPartnerAddressCollection(attachedPartnerAddressCollection);
            Collection<CheckOut> attachedCheckOutCollection = new ArrayList<CheckOut>();
            for (CheckOut checkOutCollectionCheckOutToAttach : partner.getCheckOutCollection()) {
                checkOutCollectionCheckOutToAttach = em.getReference(checkOutCollectionCheckOutToAttach.getClass(), checkOutCollectionCheckOutToAttach.getCheckingId());
                attachedCheckOutCollection.add(checkOutCollectionCheckOutToAttach);
            }
            partner.setCheckOutCollection(attachedCheckOutCollection);
            Collection<CheckOut> attachedCheckOutCollection1 = new ArrayList<CheckOut>();
            for (CheckOut checkOutCollection1CheckOutToAttach : partner.getCheckOutCollection1()) {
                checkOutCollection1CheckOutToAttach = em.getReference(checkOutCollection1CheckOutToAttach.getClass(), checkOutCollection1CheckOutToAttach.getCheckingId());
                attachedCheckOutCollection1.add(checkOutCollection1CheckOutToAttach);
            }
            partner.setCheckOutCollection1(attachedCheckOutCollection1);
            Collection<PartnerAttribute> attachedPartnerAttributeCollection = new ArrayList<PartnerAttribute>();
            for (PartnerAttribute partnerAttributeCollectionPartnerAttributeToAttach : partner.getPartnerAttributeCollection()) {
                partnerAttributeCollectionPartnerAttributeToAttach = em.getReference(partnerAttributeCollectionPartnerAttributeToAttach.getClass(), partnerAttributeCollectionPartnerAttributeToAttach.getPartnerAttributePK());
                attachedPartnerAttributeCollection.add(partnerAttributeCollectionPartnerAttributeToAttach);
            }
            partner.setPartnerAttributeCollection(attachedPartnerAttributeCollection);
            Collection<PartnerContact> attachedPartnerContactCollection = new ArrayList<PartnerContact>();
            for (PartnerContact partnerContactCollectionPartnerContactToAttach : partner.getPartnerContactCollection()) {
                partnerContactCollectionPartnerContactToAttach = em.getReference(partnerContactCollectionPartnerContactToAttach.getClass(), partnerContactCollectionPartnerContactToAttach.getContactId());
                attachedPartnerContactCollection.add(partnerContactCollectionPartnerContactToAttach);
            }
            partner.setPartnerContactCollection(attachedPartnerContactCollection);
            Collection<PartnerIdentity> attachedPartnerIdentityCollection = new ArrayList<PartnerIdentity>();
            for (PartnerIdentity partnerIdentityCollectionPartnerIdentityToAttach : partner.getPartnerIdentityCollection()) {
                partnerIdentityCollectionPartnerIdentityToAttach = em.getReference(partnerIdentityCollectionPartnerIdentityToAttach.getClass(), partnerIdentityCollectionPartnerIdentityToAttach.getPartnerIdentityPK());
                attachedPartnerIdentityCollection.add(partnerIdentityCollectionPartnerIdentityToAttach);
            }
            partner.setPartnerIdentityCollection(attachedPartnerIdentityCollection);
            em.persist(partner);
            for (TransactionPayment transactionPaymentCollectionTransactionPayment : partner.getTransactionPaymentCollection()) {
                Partner oldCreatedByOfTransactionPaymentCollectionTransactionPayment = transactionPaymentCollectionTransactionPayment.getCreatedBy();
                transactionPaymentCollectionTransactionPayment.setCreatedBy(partner);
                transactionPaymentCollectionTransactionPayment = em.merge(transactionPaymentCollectionTransactionPayment);
                if (oldCreatedByOfTransactionPaymentCollectionTransactionPayment != null) {
                    oldCreatedByOfTransactionPaymentCollectionTransactionPayment.getTransactionPaymentCollection().remove(transactionPaymentCollectionTransactionPayment);
                    oldCreatedByOfTransactionPaymentCollectionTransactionPayment = em.merge(oldCreatedByOfTransactionPaymentCollectionTransactionPayment);
                }
            }
            for (TransactionPayment transactionPaymentCollection1TransactionPayment : partner.getTransactionPaymentCollection1()) {
                Partner oldReceivedByOfTransactionPaymentCollection1TransactionPayment = transactionPaymentCollection1TransactionPayment.getReceivedBy();
                transactionPaymentCollection1TransactionPayment.setReceivedBy(partner);
                transactionPaymentCollection1TransactionPayment = em.merge(transactionPaymentCollection1TransactionPayment);
                if (oldReceivedByOfTransactionPaymentCollection1TransactionPayment != null) {
                    oldReceivedByOfTransactionPaymentCollection1TransactionPayment.getTransactionPaymentCollection1().remove(transactionPaymentCollection1TransactionPayment);
                    oldReceivedByOfTransactionPaymentCollection1TransactionPayment = em.merge(oldReceivedByOfTransactionPaymentCollection1TransactionPayment);
                }
            }
            for (PartnerRelation partnerRelationCollectionPartnerRelation : partner.getPartnerRelationCollection()) {
                Partner oldPartnerOfPartnerRelationCollectionPartnerRelation = partnerRelationCollectionPartnerRelation.getPartner();
                partnerRelationCollectionPartnerRelation.setPartner(partner);
                partnerRelationCollectionPartnerRelation = em.merge(partnerRelationCollectionPartnerRelation);
                if (oldPartnerOfPartnerRelationCollectionPartnerRelation != null) {
                    oldPartnerOfPartnerRelationCollectionPartnerRelation.getPartnerRelationCollection().remove(partnerRelationCollectionPartnerRelation);
                    oldPartnerOfPartnerRelationCollectionPartnerRelation = em.merge(oldPartnerOfPartnerRelationCollectionPartnerRelation);
                }
            }
            for (PartnerRelation partnerRelationCollection1PartnerRelation : partner.getPartnerRelationCollection1()) {
                Partner oldPartner1OfPartnerRelationCollection1PartnerRelation = partnerRelationCollection1PartnerRelation.getPartner1();
                partnerRelationCollection1PartnerRelation.setPartner1(partner);
                partnerRelationCollection1PartnerRelation = em.merge(partnerRelationCollection1PartnerRelation);
                if (oldPartner1OfPartnerRelationCollection1PartnerRelation != null) {
                    oldPartner1OfPartnerRelationCollection1PartnerRelation.getPartnerRelationCollection1().remove(partnerRelationCollection1PartnerRelation);
                    oldPartner1OfPartnerRelationCollection1PartnerRelation = em.merge(oldPartner1OfPartnerRelationCollection1PartnerRelation);
                }
            }
            for (PartnerRole partnerRoleCollectionPartnerRole : partner.getPartnerRoleCollection()) {
                Partner oldPartnerOfPartnerRoleCollectionPartnerRole = partnerRoleCollectionPartnerRole.getPartner();
                partnerRoleCollectionPartnerRole.setPartner(partner);
                partnerRoleCollectionPartnerRole = em.merge(partnerRoleCollectionPartnerRole);
                if (oldPartnerOfPartnerRoleCollectionPartnerRole != null) {
                    oldPartnerOfPartnerRoleCollectionPartnerRole.getPartnerRoleCollection().remove(partnerRoleCollectionPartnerRole);
                    oldPartnerOfPartnerRoleCollectionPartnerRole = em.merge(oldPartnerOfPartnerRoleCollectionPartnerRole);
                }
            }
            for (TransactionPartner transactionPartnerCollectionTransactionPartner : partner.getTransactionPartnerCollection()) {
                Partner oldPartnerOfTransactionPartnerCollectionTransactionPartner = transactionPartnerCollectionTransactionPartner.getPartner();
                transactionPartnerCollectionTransactionPartner.setPartner(partner);
                transactionPartnerCollectionTransactionPartner = em.merge(transactionPartnerCollectionTransactionPartner);
                if (oldPartnerOfTransactionPartnerCollectionTransactionPartner != null) {
                    oldPartnerOfTransactionPartnerCollectionTransactionPartner.getTransactionPartnerCollection().remove(transactionPartnerCollectionTransactionPartner);
                    oldPartnerOfTransactionPartnerCollectionTransactionPartner = em.merge(oldPartnerOfTransactionPartnerCollectionTransactionPartner);
                }
            }
            for (PartnerAddress partnerAddressCollectionPartnerAddress : partner.getPartnerAddressCollection()) {
                Partner oldPartnerNoOfPartnerAddressCollectionPartnerAddress = partnerAddressCollectionPartnerAddress.getPartnerNo();
                partnerAddressCollectionPartnerAddress.setPartnerNo(partner);
                partnerAddressCollectionPartnerAddress = em.merge(partnerAddressCollectionPartnerAddress);
                if (oldPartnerNoOfPartnerAddressCollectionPartnerAddress != null) {
                    oldPartnerNoOfPartnerAddressCollectionPartnerAddress.getPartnerAddressCollection().remove(partnerAddressCollectionPartnerAddress);
                    oldPartnerNoOfPartnerAddressCollectionPartnerAddress = em.merge(oldPartnerNoOfPartnerAddressCollectionPartnerAddress);
                }
            }
            for (CheckOut checkOutCollectionCheckOut : partner.getCheckOutCollection()) {
                Partner oldCreatedByOfCheckOutCollectionCheckOut = checkOutCollectionCheckOut.getCreatedBy();
                checkOutCollectionCheckOut.setCreatedBy(partner);
                checkOutCollectionCheckOut = em.merge(checkOutCollectionCheckOut);
                if (oldCreatedByOfCheckOutCollectionCheckOut != null) {
                    oldCreatedByOfCheckOutCollectionCheckOut.getCheckOutCollection().remove(checkOutCollectionCheckOut);
                    oldCreatedByOfCheckOutCollectionCheckOut = em.merge(oldCreatedByOfCheckOutCollectionCheckOut);
                }
            }
            for (CheckOut checkOutCollection1CheckOut : partner.getCheckOutCollection1()) {
                Partner oldReceivedByOfCheckOutCollection1CheckOut = checkOutCollection1CheckOut.getReceivedBy();
                checkOutCollection1CheckOut.setReceivedBy(partner);
                checkOutCollection1CheckOut = em.merge(checkOutCollection1CheckOut);
                if (oldReceivedByOfCheckOutCollection1CheckOut != null) {
                    oldReceivedByOfCheckOutCollection1CheckOut.getCheckOutCollection1().remove(checkOutCollection1CheckOut);
                    oldReceivedByOfCheckOutCollection1CheckOut = em.merge(oldReceivedByOfCheckOutCollection1CheckOut);
                }
            }
            for (PartnerAttribute partnerAttributeCollectionPartnerAttribute : partner.getPartnerAttributeCollection()) {
                Partner oldPartnerOfPartnerAttributeCollectionPartnerAttribute = partnerAttributeCollectionPartnerAttribute.getPartner();
                partnerAttributeCollectionPartnerAttribute.setPartner(partner);
                partnerAttributeCollectionPartnerAttribute = em.merge(partnerAttributeCollectionPartnerAttribute);
                if (oldPartnerOfPartnerAttributeCollectionPartnerAttribute != null) {
                    oldPartnerOfPartnerAttributeCollectionPartnerAttribute.getPartnerAttributeCollection().remove(partnerAttributeCollectionPartnerAttribute);
                    oldPartnerOfPartnerAttributeCollectionPartnerAttribute = em.merge(oldPartnerOfPartnerAttributeCollectionPartnerAttribute);
                }
            }
            for (PartnerContact partnerContactCollectionPartnerContact : partner.getPartnerContactCollection()) {
                Partner oldPartnerNoOfPartnerContactCollectionPartnerContact = partnerContactCollectionPartnerContact.getPartnerNo();
                partnerContactCollectionPartnerContact.setPartnerNo(partner);
                partnerContactCollectionPartnerContact = em.merge(partnerContactCollectionPartnerContact);
                if (oldPartnerNoOfPartnerContactCollectionPartnerContact != null) {
                    oldPartnerNoOfPartnerContactCollectionPartnerContact.getPartnerContactCollection().remove(partnerContactCollectionPartnerContact);
                    oldPartnerNoOfPartnerContactCollectionPartnerContact = em.merge(oldPartnerNoOfPartnerContactCollectionPartnerContact);
                }
            }
            for (PartnerIdentity partnerIdentityCollectionPartnerIdentity : partner.getPartnerIdentityCollection()) {
                Partner oldPartnerNoOfPartnerIdentityCollectionPartnerIdentity = partnerIdentityCollectionPartnerIdentity.getPartnerNo();
                partnerIdentityCollectionPartnerIdentity.setPartnerNo(partner);
                partnerIdentityCollectionPartnerIdentity = em.merge(partnerIdentityCollectionPartnerIdentity);
                if (oldPartnerNoOfPartnerIdentityCollectionPartnerIdentity != null) {
                    oldPartnerNoOfPartnerIdentityCollectionPartnerIdentity.getPartnerIdentityCollection().remove(partnerIdentityCollectionPartnerIdentity);
                    oldPartnerNoOfPartnerIdentityCollectionPartnerIdentity = em.merge(oldPartnerNoOfPartnerIdentityCollectionPartnerIdentity);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPartner(partner.getPartnerNo()) != null) {
                throw new PreexistingEntityException("Partner " + partner + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partner partner) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Partner persistentPartner = em.find(Partner.class, partner.getPartnerNo());
            Collection<TransactionPayment> transactionPaymentCollectionOld = persistentPartner.getTransactionPaymentCollection();
            Collection<TransactionPayment> transactionPaymentCollectionNew = partner.getTransactionPaymentCollection();
            Collection<TransactionPayment> transactionPaymentCollection1Old = persistentPartner.getTransactionPaymentCollection1();
            Collection<TransactionPayment> transactionPaymentCollection1New = partner.getTransactionPaymentCollection1();
            Collection<PartnerRelation> partnerRelationCollectionOld = persistentPartner.getPartnerRelationCollection();
            Collection<PartnerRelation> partnerRelationCollectionNew = partner.getPartnerRelationCollection();
            Collection<PartnerRelation> partnerRelationCollection1Old = persistentPartner.getPartnerRelationCollection1();
            Collection<PartnerRelation> partnerRelationCollection1New = partner.getPartnerRelationCollection1();
            Collection<PartnerRole> partnerRoleCollectionOld = persistentPartner.getPartnerRoleCollection();
            Collection<PartnerRole> partnerRoleCollectionNew = partner.getPartnerRoleCollection();
            Collection<TransactionPartner> transactionPartnerCollectionOld = persistentPartner.getTransactionPartnerCollection();
            Collection<TransactionPartner> transactionPartnerCollectionNew = partner.getTransactionPartnerCollection();
            Collection<PartnerAddress> partnerAddressCollectionOld = persistentPartner.getPartnerAddressCollection();
            Collection<PartnerAddress> partnerAddressCollectionNew = partner.getPartnerAddressCollection();
            Collection<CheckOut> checkOutCollectionOld = persistentPartner.getCheckOutCollection();
            Collection<CheckOut> checkOutCollectionNew = partner.getCheckOutCollection();
            Collection<CheckOut> checkOutCollection1Old = persistentPartner.getCheckOutCollection1();
            Collection<CheckOut> checkOutCollection1New = partner.getCheckOutCollection1();
            Collection<PartnerAttribute> partnerAttributeCollectionOld = persistentPartner.getPartnerAttributeCollection();
            Collection<PartnerAttribute> partnerAttributeCollectionNew = partner.getPartnerAttributeCollection();
            Collection<PartnerContact> partnerContactCollectionOld = persistentPartner.getPartnerContactCollection();
            Collection<PartnerContact> partnerContactCollectionNew = partner.getPartnerContactCollection();
            Collection<PartnerIdentity> partnerIdentityCollectionOld = persistentPartner.getPartnerIdentityCollection();
            Collection<PartnerIdentity> partnerIdentityCollectionNew = partner.getPartnerIdentityCollection();
            List<String> illegalOrphanMessages = null;
            for (PartnerRelation partnerRelationCollectionOldPartnerRelation : partnerRelationCollectionOld) {
                if (!partnerRelationCollectionNew.contains(partnerRelationCollectionOldPartnerRelation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PartnerRelation " + partnerRelationCollectionOldPartnerRelation + " since its partner field is not nullable.");
                }
            }
            for (PartnerRelation partnerRelationCollection1OldPartnerRelation : partnerRelationCollection1Old) {
                if (!partnerRelationCollection1New.contains(partnerRelationCollection1OldPartnerRelation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PartnerRelation " + partnerRelationCollection1OldPartnerRelation + " since its partner1 field is not nullable.");
                }
            }
            for (PartnerRole partnerRoleCollectionOldPartnerRole : partnerRoleCollectionOld) {
                if (!partnerRoleCollectionNew.contains(partnerRoleCollectionOldPartnerRole)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PartnerRole " + partnerRoleCollectionOldPartnerRole + " since its partner field is not nullable.");
                }
            }
            for (TransactionPartner transactionPartnerCollectionOldTransactionPartner : transactionPartnerCollectionOld) {
                if (!transactionPartnerCollectionNew.contains(transactionPartnerCollectionOldTransactionPartner)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransactionPartner " + transactionPartnerCollectionOldTransactionPartner + " since its partner field is not nullable.");
                }
            }
            for (PartnerAttribute partnerAttributeCollectionOldPartnerAttribute : partnerAttributeCollectionOld) {
                if (!partnerAttributeCollectionNew.contains(partnerAttributeCollectionOldPartnerAttribute)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PartnerAttribute " + partnerAttributeCollectionOldPartnerAttribute + " since its partner field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionPayment> attachedTransactionPaymentCollectionNew = new ArrayList<TransactionPayment>();
            for (TransactionPayment transactionPaymentCollectionNewTransactionPaymentToAttach : transactionPaymentCollectionNew) {
                transactionPaymentCollectionNewTransactionPaymentToAttach = em.getReference(transactionPaymentCollectionNewTransactionPaymentToAttach.getClass(), transactionPaymentCollectionNewTransactionPaymentToAttach.getPaymentNo());
                attachedTransactionPaymentCollectionNew.add(transactionPaymentCollectionNewTransactionPaymentToAttach);
            }
            transactionPaymentCollectionNew = attachedTransactionPaymentCollectionNew;
            partner.setTransactionPaymentCollection(transactionPaymentCollectionNew);
            Collection<TransactionPayment> attachedTransactionPaymentCollection1New = new ArrayList<TransactionPayment>();
            for (TransactionPayment transactionPaymentCollection1NewTransactionPaymentToAttach : transactionPaymentCollection1New) {
                transactionPaymentCollection1NewTransactionPaymentToAttach = em.getReference(transactionPaymentCollection1NewTransactionPaymentToAttach.getClass(), transactionPaymentCollection1NewTransactionPaymentToAttach.getPaymentNo());
                attachedTransactionPaymentCollection1New.add(transactionPaymentCollection1NewTransactionPaymentToAttach);
            }
            transactionPaymentCollection1New = attachedTransactionPaymentCollection1New;
            partner.setTransactionPaymentCollection1(transactionPaymentCollection1New);
            Collection<PartnerRelation> attachedPartnerRelationCollectionNew = new ArrayList<PartnerRelation>();
            for (PartnerRelation partnerRelationCollectionNewPartnerRelationToAttach : partnerRelationCollectionNew) {
                partnerRelationCollectionNewPartnerRelationToAttach = em.getReference(partnerRelationCollectionNewPartnerRelationToAttach.getClass(), partnerRelationCollectionNewPartnerRelationToAttach.getPartnerRelationPK());
                attachedPartnerRelationCollectionNew.add(partnerRelationCollectionNewPartnerRelationToAttach);
            }
            partnerRelationCollectionNew = attachedPartnerRelationCollectionNew;
            partner.setPartnerRelationCollection(partnerRelationCollectionNew);
            Collection<PartnerRelation> attachedPartnerRelationCollection1New = new ArrayList<PartnerRelation>();
            for (PartnerRelation partnerRelationCollection1NewPartnerRelationToAttach : partnerRelationCollection1New) {
                partnerRelationCollection1NewPartnerRelationToAttach = em.getReference(partnerRelationCollection1NewPartnerRelationToAttach.getClass(), partnerRelationCollection1NewPartnerRelationToAttach.getPartnerRelationPK());
                attachedPartnerRelationCollection1New.add(partnerRelationCollection1NewPartnerRelationToAttach);
            }
            partnerRelationCollection1New = attachedPartnerRelationCollection1New;
            partner.setPartnerRelationCollection1(partnerRelationCollection1New);
            Collection<PartnerRole> attachedPartnerRoleCollectionNew = new ArrayList<PartnerRole>();
            for (PartnerRole partnerRoleCollectionNewPartnerRoleToAttach : partnerRoleCollectionNew) {
                partnerRoleCollectionNewPartnerRoleToAttach = em.getReference(partnerRoleCollectionNewPartnerRoleToAttach.getClass(), partnerRoleCollectionNewPartnerRoleToAttach.getPartnerRolePK());
                attachedPartnerRoleCollectionNew.add(partnerRoleCollectionNewPartnerRoleToAttach);
            }
            partnerRoleCollectionNew = attachedPartnerRoleCollectionNew;
            partner.setPartnerRoleCollection(partnerRoleCollectionNew);
            Collection<TransactionPartner> attachedTransactionPartnerCollectionNew = new ArrayList<TransactionPartner>();
            for (TransactionPartner transactionPartnerCollectionNewTransactionPartnerToAttach : transactionPartnerCollectionNew) {
                transactionPartnerCollectionNewTransactionPartnerToAttach = em.getReference(transactionPartnerCollectionNewTransactionPartnerToAttach.getClass(), transactionPartnerCollectionNewTransactionPartnerToAttach.getTransactionPartnerPK());
                attachedTransactionPartnerCollectionNew.add(transactionPartnerCollectionNewTransactionPartnerToAttach);
            }
            transactionPartnerCollectionNew = attachedTransactionPartnerCollectionNew;
            partner.setTransactionPartnerCollection(transactionPartnerCollectionNew);
            Collection<PartnerAddress> attachedPartnerAddressCollectionNew = new ArrayList<PartnerAddress>();
            for (PartnerAddress partnerAddressCollectionNewPartnerAddressToAttach : partnerAddressCollectionNew) {
                partnerAddressCollectionNewPartnerAddressToAttach = em.getReference(partnerAddressCollectionNewPartnerAddressToAttach.getClass(), partnerAddressCollectionNewPartnerAddressToAttach.getAddressId());
                attachedPartnerAddressCollectionNew.add(partnerAddressCollectionNewPartnerAddressToAttach);
            }
            partnerAddressCollectionNew = attachedPartnerAddressCollectionNew;
            partner.setPartnerAddressCollection(partnerAddressCollectionNew);
            Collection<CheckOut> attachedCheckOutCollectionNew = new ArrayList<CheckOut>();
            for (CheckOut checkOutCollectionNewCheckOutToAttach : checkOutCollectionNew) {
                checkOutCollectionNewCheckOutToAttach = em.getReference(checkOutCollectionNewCheckOutToAttach.getClass(), checkOutCollectionNewCheckOutToAttach.getCheckingId());
                attachedCheckOutCollectionNew.add(checkOutCollectionNewCheckOutToAttach);
            }
            checkOutCollectionNew = attachedCheckOutCollectionNew;
            partner.setCheckOutCollection(checkOutCollectionNew);
            Collection<CheckOut> attachedCheckOutCollection1New = new ArrayList<CheckOut>();
            for (CheckOut checkOutCollection1NewCheckOutToAttach : checkOutCollection1New) {
                checkOutCollection1NewCheckOutToAttach = em.getReference(checkOutCollection1NewCheckOutToAttach.getClass(), checkOutCollection1NewCheckOutToAttach.getCheckingId());
                attachedCheckOutCollection1New.add(checkOutCollection1NewCheckOutToAttach);
            }
            checkOutCollection1New = attachedCheckOutCollection1New;
            partner.setCheckOutCollection1(checkOutCollection1New);
            Collection<PartnerAttribute> attachedPartnerAttributeCollectionNew = new ArrayList<PartnerAttribute>();
            for (PartnerAttribute partnerAttributeCollectionNewPartnerAttributeToAttach : partnerAttributeCollectionNew) {
                partnerAttributeCollectionNewPartnerAttributeToAttach = em.getReference(partnerAttributeCollectionNewPartnerAttributeToAttach.getClass(), partnerAttributeCollectionNewPartnerAttributeToAttach.getPartnerAttributePK());
                attachedPartnerAttributeCollectionNew.add(partnerAttributeCollectionNewPartnerAttributeToAttach);
            }
            partnerAttributeCollectionNew = attachedPartnerAttributeCollectionNew;
            partner.setPartnerAttributeCollection(partnerAttributeCollectionNew);
            Collection<PartnerContact> attachedPartnerContactCollectionNew = new ArrayList<PartnerContact>();
            for (PartnerContact partnerContactCollectionNewPartnerContactToAttach : partnerContactCollectionNew) {
                partnerContactCollectionNewPartnerContactToAttach = em.getReference(partnerContactCollectionNewPartnerContactToAttach.getClass(), partnerContactCollectionNewPartnerContactToAttach.getContactId());
                attachedPartnerContactCollectionNew.add(partnerContactCollectionNewPartnerContactToAttach);
            }
            partnerContactCollectionNew = attachedPartnerContactCollectionNew;
            partner.setPartnerContactCollection(partnerContactCollectionNew);
            Collection<PartnerIdentity> attachedPartnerIdentityCollectionNew = new ArrayList<PartnerIdentity>();
            for (PartnerIdentity partnerIdentityCollectionNewPartnerIdentityToAttach : partnerIdentityCollectionNew) {
                partnerIdentityCollectionNewPartnerIdentityToAttach = em.getReference(partnerIdentityCollectionNewPartnerIdentityToAttach.getClass(), partnerIdentityCollectionNewPartnerIdentityToAttach.getPartnerIdentityPK());
                attachedPartnerIdentityCollectionNew.add(partnerIdentityCollectionNewPartnerIdentityToAttach);
            }
            partnerIdentityCollectionNew = attachedPartnerIdentityCollectionNew;
            partner.setPartnerIdentityCollection(partnerIdentityCollectionNew);
            partner = em.merge(partner);
            for (TransactionPayment transactionPaymentCollectionOldTransactionPayment : transactionPaymentCollectionOld) {
                if (!transactionPaymentCollectionNew.contains(transactionPaymentCollectionOldTransactionPayment)) {
                    transactionPaymentCollectionOldTransactionPayment.setCreatedBy(null);
                    transactionPaymentCollectionOldTransactionPayment = em.merge(transactionPaymentCollectionOldTransactionPayment);
                }
            }
            for (TransactionPayment transactionPaymentCollectionNewTransactionPayment : transactionPaymentCollectionNew) {
                if (!transactionPaymentCollectionOld.contains(transactionPaymentCollectionNewTransactionPayment)) {
                    Partner oldCreatedByOfTransactionPaymentCollectionNewTransactionPayment = transactionPaymentCollectionNewTransactionPayment.getCreatedBy();
                    transactionPaymentCollectionNewTransactionPayment.setCreatedBy(partner);
                    transactionPaymentCollectionNewTransactionPayment = em.merge(transactionPaymentCollectionNewTransactionPayment);
                    if (oldCreatedByOfTransactionPaymentCollectionNewTransactionPayment != null && !oldCreatedByOfTransactionPaymentCollectionNewTransactionPayment.equals(partner)) {
                        oldCreatedByOfTransactionPaymentCollectionNewTransactionPayment.getTransactionPaymentCollection().remove(transactionPaymentCollectionNewTransactionPayment);
                        oldCreatedByOfTransactionPaymentCollectionNewTransactionPayment = em.merge(oldCreatedByOfTransactionPaymentCollectionNewTransactionPayment);
                    }
                }
            }
            for (TransactionPayment transactionPaymentCollection1OldTransactionPayment : transactionPaymentCollection1Old) {
                if (!transactionPaymentCollection1New.contains(transactionPaymentCollection1OldTransactionPayment)) {
                    transactionPaymentCollection1OldTransactionPayment.setReceivedBy(null);
                    transactionPaymentCollection1OldTransactionPayment = em.merge(transactionPaymentCollection1OldTransactionPayment);
                }
            }
            for (TransactionPayment transactionPaymentCollection1NewTransactionPayment : transactionPaymentCollection1New) {
                if (!transactionPaymentCollection1Old.contains(transactionPaymentCollection1NewTransactionPayment)) {
                    Partner oldReceivedByOfTransactionPaymentCollection1NewTransactionPayment = transactionPaymentCollection1NewTransactionPayment.getReceivedBy();
                    transactionPaymentCollection1NewTransactionPayment.setReceivedBy(partner);
                    transactionPaymentCollection1NewTransactionPayment = em.merge(transactionPaymentCollection1NewTransactionPayment);
                    if (oldReceivedByOfTransactionPaymentCollection1NewTransactionPayment != null && !oldReceivedByOfTransactionPaymentCollection1NewTransactionPayment.equals(partner)) {
                        oldReceivedByOfTransactionPaymentCollection1NewTransactionPayment.getTransactionPaymentCollection1().remove(transactionPaymentCollection1NewTransactionPayment);
                        oldReceivedByOfTransactionPaymentCollection1NewTransactionPayment = em.merge(oldReceivedByOfTransactionPaymentCollection1NewTransactionPayment);
                    }
                }
            }
            for (PartnerRelation partnerRelationCollectionNewPartnerRelation : partnerRelationCollectionNew) {
                if (!partnerRelationCollectionOld.contains(partnerRelationCollectionNewPartnerRelation)) {
                    Partner oldPartnerOfPartnerRelationCollectionNewPartnerRelation = partnerRelationCollectionNewPartnerRelation.getPartner();
                    partnerRelationCollectionNewPartnerRelation.setPartner(partner);
                    partnerRelationCollectionNewPartnerRelation = em.merge(partnerRelationCollectionNewPartnerRelation);
                    if (oldPartnerOfPartnerRelationCollectionNewPartnerRelation != null && !oldPartnerOfPartnerRelationCollectionNewPartnerRelation.equals(partner)) {
                        oldPartnerOfPartnerRelationCollectionNewPartnerRelation.getPartnerRelationCollection().remove(partnerRelationCollectionNewPartnerRelation);
                        oldPartnerOfPartnerRelationCollectionNewPartnerRelation = em.merge(oldPartnerOfPartnerRelationCollectionNewPartnerRelation);
                    }
                }
            }
            for (PartnerRelation partnerRelationCollection1NewPartnerRelation : partnerRelationCollection1New) {
                if (!partnerRelationCollection1Old.contains(partnerRelationCollection1NewPartnerRelation)) {
                    Partner oldPartner1OfPartnerRelationCollection1NewPartnerRelation = partnerRelationCollection1NewPartnerRelation.getPartner1();
                    partnerRelationCollection1NewPartnerRelation.setPartner1(partner);
                    partnerRelationCollection1NewPartnerRelation = em.merge(partnerRelationCollection1NewPartnerRelation);
                    if (oldPartner1OfPartnerRelationCollection1NewPartnerRelation != null && !oldPartner1OfPartnerRelationCollection1NewPartnerRelation.equals(partner)) {
                        oldPartner1OfPartnerRelationCollection1NewPartnerRelation.getPartnerRelationCollection1().remove(partnerRelationCollection1NewPartnerRelation);
                        oldPartner1OfPartnerRelationCollection1NewPartnerRelation = em.merge(oldPartner1OfPartnerRelationCollection1NewPartnerRelation);
                    }
                }
            }
            for (PartnerRole partnerRoleCollectionNewPartnerRole : partnerRoleCollectionNew) {
                if (!partnerRoleCollectionOld.contains(partnerRoleCollectionNewPartnerRole)) {
                    Partner oldPartnerOfPartnerRoleCollectionNewPartnerRole = partnerRoleCollectionNewPartnerRole.getPartner();
                    partnerRoleCollectionNewPartnerRole.setPartner(partner);
                    partnerRoleCollectionNewPartnerRole = em.merge(partnerRoleCollectionNewPartnerRole);
                    if (oldPartnerOfPartnerRoleCollectionNewPartnerRole != null && !oldPartnerOfPartnerRoleCollectionNewPartnerRole.equals(partner)) {
                        oldPartnerOfPartnerRoleCollectionNewPartnerRole.getPartnerRoleCollection().remove(partnerRoleCollectionNewPartnerRole);
                        oldPartnerOfPartnerRoleCollectionNewPartnerRole = em.merge(oldPartnerOfPartnerRoleCollectionNewPartnerRole);
                    }
                }
            }
            for (TransactionPartner transactionPartnerCollectionNewTransactionPartner : transactionPartnerCollectionNew) {
                if (!transactionPartnerCollectionOld.contains(transactionPartnerCollectionNewTransactionPartner)) {
                    Partner oldPartnerOfTransactionPartnerCollectionNewTransactionPartner = transactionPartnerCollectionNewTransactionPartner.getPartner();
                    transactionPartnerCollectionNewTransactionPartner.setPartner(partner);
                    transactionPartnerCollectionNewTransactionPartner = em.merge(transactionPartnerCollectionNewTransactionPartner);
                    if (oldPartnerOfTransactionPartnerCollectionNewTransactionPartner != null && !oldPartnerOfTransactionPartnerCollectionNewTransactionPartner.equals(partner)) {
                        oldPartnerOfTransactionPartnerCollectionNewTransactionPartner.getTransactionPartnerCollection().remove(transactionPartnerCollectionNewTransactionPartner);
                        oldPartnerOfTransactionPartnerCollectionNewTransactionPartner = em.merge(oldPartnerOfTransactionPartnerCollectionNewTransactionPartner);
                    }
                }
            }
            for (PartnerAddress partnerAddressCollectionOldPartnerAddress : partnerAddressCollectionOld) {
                if (!partnerAddressCollectionNew.contains(partnerAddressCollectionOldPartnerAddress)) {
                    partnerAddressCollectionOldPartnerAddress.setPartnerNo(null);
                    partnerAddressCollectionOldPartnerAddress = em.merge(partnerAddressCollectionOldPartnerAddress);
                }
            }
            for (PartnerAddress partnerAddressCollectionNewPartnerAddress : partnerAddressCollectionNew) {
                if (!partnerAddressCollectionOld.contains(partnerAddressCollectionNewPartnerAddress)) {
                    Partner oldPartnerNoOfPartnerAddressCollectionNewPartnerAddress = partnerAddressCollectionNewPartnerAddress.getPartnerNo();
                    partnerAddressCollectionNewPartnerAddress.setPartnerNo(partner);
                    partnerAddressCollectionNewPartnerAddress = em.merge(partnerAddressCollectionNewPartnerAddress);
                    if (oldPartnerNoOfPartnerAddressCollectionNewPartnerAddress != null && !oldPartnerNoOfPartnerAddressCollectionNewPartnerAddress.equals(partner)) {
                        oldPartnerNoOfPartnerAddressCollectionNewPartnerAddress.getPartnerAddressCollection().remove(partnerAddressCollectionNewPartnerAddress);
                        oldPartnerNoOfPartnerAddressCollectionNewPartnerAddress = em.merge(oldPartnerNoOfPartnerAddressCollectionNewPartnerAddress);
                    }
                }
            }
            for (CheckOut checkOutCollectionOldCheckOut : checkOutCollectionOld) {
                if (!checkOutCollectionNew.contains(checkOutCollectionOldCheckOut)) {
                    checkOutCollectionOldCheckOut.setCreatedBy(null);
                    checkOutCollectionOldCheckOut = em.merge(checkOutCollectionOldCheckOut);
                }
            }
            for (CheckOut checkOutCollectionNewCheckOut : checkOutCollectionNew) {
                if (!checkOutCollectionOld.contains(checkOutCollectionNewCheckOut)) {
                    Partner oldCreatedByOfCheckOutCollectionNewCheckOut = checkOutCollectionNewCheckOut.getCreatedBy();
                    checkOutCollectionNewCheckOut.setCreatedBy(partner);
                    checkOutCollectionNewCheckOut = em.merge(checkOutCollectionNewCheckOut);
                    if (oldCreatedByOfCheckOutCollectionNewCheckOut != null && !oldCreatedByOfCheckOutCollectionNewCheckOut.equals(partner)) {
                        oldCreatedByOfCheckOutCollectionNewCheckOut.getCheckOutCollection().remove(checkOutCollectionNewCheckOut);
                        oldCreatedByOfCheckOutCollectionNewCheckOut = em.merge(oldCreatedByOfCheckOutCollectionNewCheckOut);
                    }
                }
            }
            for (CheckOut checkOutCollection1OldCheckOut : checkOutCollection1Old) {
                if (!checkOutCollection1New.contains(checkOutCollection1OldCheckOut)) {
                    checkOutCollection1OldCheckOut.setReceivedBy(null);
                    checkOutCollection1OldCheckOut = em.merge(checkOutCollection1OldCheckOut);
                }
            }
            for (CheckOut checkOutCollection1NewCheckOut : checkOutCollection1New) {
                if (!checkOutCollection1Old.contains(checkOutCollection1NewCheckOut)) {
                    Partner oldReceivedByOfCheckOutCollection1NewCheckOut = checkOutCollection1NewCheckOut.getReceivedBy();
                    checkOutCollection1NewCheckOut.setReceivedBy(partner);
                    checkOutCollection1NewCheckOut = em.merge(checkOutCollection1NewCheckOut);
                    if (oldReceivedByOfCheckOutCollection1NewCheckOut != null && !oldReceivedByOfCheckOutCollection1NewCheckOut.equals(partner)) {
                        oldReceivedByOfCheckOutCollection1NewCheckOut.getCheckOutCollection1().remove(checkOutCollection1NewCheckOut);
                        oldReceivedByOfCheckOutCollection1NewCheckOut = em.merge(oldReceivedByOfCheckOutCollection1NewCheckOut);
                    }
                }
            }
            for (PartnerAttribute partnerAttributeCollectionNewPartnerAttribute : partnerAttributeCollectionNew) {
                if (!partnerAttributeCollectionOld.contains(partnerAttributeCollectionNewPartnerAttribute)) {
                    Partner oldPartnerOfPartnerAttributeCollectionNewPartnerAttribute = partnerAttributeCollectionNewPartnerAttribute.getPartner();
                    partnerAttributeCollectionNewPartnerAttribute.setPartner(partner);
                    partnerAttributeCollectionNewPartnerAttribute = em.merge(partnerAttributeCollectionNewPartnerAttribute);
                    if (oldPartnerOfPartnerAttributeCollectionNewPartnerAttribute != null && !oldPartnerOfPartnerAttributeCollectionNewPartnerAttribute.equals(partner)) {
                        oldPartnerOfPartnerAttributeCollectionNewPartnerAttribute.getPartnerAttributeCollection().remove(partnerAttributeCollectionNewPartnerAttribute);
                        oldPartnerOfPartnerAttributeCollectionNewPartnerAttribute = em.merge(oldPartnerOfPartnerAttributeCollectionNewPartnerAttribute);
                    }
                }
            }
            for (PartnerContact partnerContactCollectionOldPartnerContact : partnerContactCollectionOld) {
                if (!partnerContactCollectionNew.contains(partnerContactCollectionOldPartnerContact)) {
                    partnerContactCollectionOldPartnerContact.setPartnerNo(null);
                    partnerContactCollectionOldPartnerContact = em.merge(partnerContactCollectionOldPartnerContact);
                }
            }
            for (PartnerContact partnerContactCollectionNewPartnerContact : partnerContactCollectionNew) {
                if (!partnerContactCollectionOld.contains(partnerContactCollectionNewPartnerContact)) {
                    Partner oldPartnerNoOfPartnerContactCollectionNewPartnerContact = partnerContactCollectionNewPartnerContact.getPartnerNo();
                    partnerContactCollectionNewPartnerContact.setPartnerNo(partner);
                    partnerContactCollectionNewPartnerContact = em.merge(partnerContactCollectionNewPartnerContact);
                    if (oldPartnerNoOfPartnerContactCollectionNewPartnerContact != null && !oldPartnerNoOfPartnerContactCollectionNewPartnerContact.equals(partner)) {
                        oldPartnerNoOfPartnerContactCollectionNewPartnerContact.getPartnerContactCollection().remove(partnerContactCollectionNewPartnerContact);
                        oldPartnerNoOfPartnerContactCollectionNewPartnerContact = em.merge(oldPartnerNoOfPartnerContactCollectionNewPartnerContact);
                    }
                }
            }
            for (PartnerIdentity partnerIdentityCollectionOldPartnerIdentity : partnerIdentityCollectionOld) {
                if (!partnerIdentityCollectionNew.contains(partnerIdentityCollectionOldPartnerIdentity)) {
                    partnerIdentityCollectionOldPartnerIdentity.setPartnerNo(null);
                    partnerIdentityCollectionOldPartnerIdentity = em.merge(partnerIdentityCollectionOldPartnerIdentity);
                }
            }
            for (PartnerIdentity partnerIdentityCollectionNewPartnerIdentity : partnerIdentityCollectionNew) {
                if (!partnerIdentityCollectionOld.contains(partnerIdentityCollectionNewPartnerIdentity)) {
                    Partner oldPartnerNoOfPartnerIdentityCollectionNewPartnerIdentity = partnerIdentityCollectionNewPartnerIdentity.getPartnerNo();
                    partnerIdentityCollectionNewPartnerIdentity.setPartnerNo(partner);
                    partnerIdentityCollectionNewPartnerIdentity = em.merge(partnerIdentityCollectionNewPartnerIdentity);
                    if (oldPartnerNoOfPartnerIdentityCollectionNewPartnerIdentity != null && !oldPartnerNoOfPartnerIdentityCollectionNewPartnerIdentity.equals(partner)) {
                        oldPartnerNoOfPartnerIdentityCollectionNewPartnerIdentity.getPartnerIdentityCollection().remove(partnerIdentityCollectionNewPartnerIdentity);
                        oldPartnerNoOfPartnerIdentityCollectionNewPartnerIdentity = em.merge(oldPartnerNoOfPartnerIdentityCollectionNewPartnerIdentity);
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
                String id = partner.getPartnerNo();
                if (findPartner(id) == null) {
                    throw new NonexistentEntityException("The partner with id " + id + " no longer exists.");
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
            Partner partner;
            try {
                partner = em.getReference(Partner.class, id);
                partner.getPartnerNo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partner with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PartnerRelation> partnerRelationCollectionOrphanCheck = partner.getPartnerRelationCollection();
            for (PartnerRelation partnerRelationCollectionOrphanCheckPartnerRelation : partnerRelationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partner (" + partner + ") cannot be destroyed since the PartnerRelation " + partnerRelationCollectionOrphanCheckPartnerRelation + " in its partnerRelationCollection field has a non-nullable partner field.");
            }
            Collection<PartnerRelation> partnerRelationCollection1OrphanCheck = partner.getPartnerRelationCollection1();
            for (PartnerRelation partnerRelationCollection1OrphanCheckPartnerRelation : partnerRelationCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partner (" + partner + ") cannot be destroyed since the PartnerRelation " + partnerRelationCollection1OrphanCheckPartnerRelation + " in its partnerRelationCollection1 field has a non-nullable partner1 field.");
            }
            Collection<PartnerRole> partnerRoleCollectionOrphanCheck = partner.getPartnerRoleCollection();
            for (PartnerRole partnerRoleCollectionOrphanCheckPartnerRole : partnerRoleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partner (" + partner + ") cannot be destroyed since the PartnerRole " + partnerRoleCollectionOrphanCheckPartnerRole + " in its partnerRoleCollection field has a non-nullable partner field.");
            }
            Collection<TransactionPartner> transactionPartnerCollectionOrphanCheck = partner.getTransactionPartnerCollection();
            for (TransactionPartner transactionPartnerCollectionOrphanCheckTransactionPartner : transactionPartnerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partner (" + partner + ") cannot be destroyed since the TransactionPartner " + transactionPartnerCollectionOrphanCheckTransactionPartner + " in its transactionPartnerCollection field has a non-nullable partner field.");
            }
            Collection<PartnerAttribute> partnerAttributeCollectionOrphanCheck = partner.getPartnerAttributeCollection();
            for (PartnerAttribute partnerAttributeCollectionOrphanCheckPartnerAttribute : partnerAttributeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partner (" + partner + ") cannot be destroyed since the PartnerAttribute " + partnerAttributeCollectionOrphanCheckPartnerAttribute + " in its partnerAttributeCollection field has a non-nullable partner field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransactionPayment> transactionPaymentCollection = partner.getTransactionPaymentCollection();
            for (TransactionPayment transactionPaymentCollectionTransactionPayment : transactionPaymentCollection) {
                transactionPaymentCollectionTransactionPayment.setCreatedBy(null);
                transactionPaymentCollectionTransactionPayment = em.merge(transactionPaymentCollectionTransactionPayment);
            }
            Collection<TransactionPayment> transactionPaymentCollection1 = partner.getTransactionPaymentCollection1();
            for (TransactionPayment transactionPaymentCollection1TransactionPayment : transactionPaymentCollection1) {
                transactionPaymentCollection1TransactionPayment.setReceivedBy(null);
                transactionPaymentCollection1TransactionPayment = em.merge(transactionPaymentCollection1TransactionPayment);
            }
            Collection<PartnerAddress> partnerAddressCollection = partner.getPartnerAddressCollection();
            for (PartnerAddress partnerAddressCollectionPartnerAddress : partnerAddressCollection) {
                partnerAddressCollectionPartnerAddress.setPartnerNo(null);
                partnerAddressCollectionPartnerAddress = em.merge(partnerAddressCollectionPartnerAddress);
            }
            Collection<CheckOut> checkOutCollection = partner.getCheckOutCollection();
            for (CheckOut checkOutCollectionCheckOut : checkOutCollection) {
                checkOutCollectionCheckOut.setCreatedBy(null);
                checkOutCollectionCheckOut = em.merge(checkOutCollectionCheckOut);
            }
            Collection<CheckOut> checkOutCollection1 = partner.getCheckOutCollection1();
            for (CheckOut checkOutCollection1CheckOut : checkOutCollection1) {
                checkOutCollection1CheckOut.setReceivedBy(null);
                checkOutCollection1CheckOut = em.merge(checkOutCollection1CheckOut);
            }
            Collection<PartnerContact> partnerContactCollection = partner.getPartnerContactCollection();
            for (PartnerContact partnerContactCollectionPartnerContact : partnerContactCollection) {
                partnerContactCollectionPartnerContact.setPartnerNo(null);
                partnerContactCollectionPartnerContact = em.merge(partnerContactCollectionPartnerContact);
            }
            Collection<PartnerIdentity> partnerIdentityCollection = partner.getPartnerIdentityCollection();
            for (PartnerIdentity partnerIdentityCollectionPartnerIdentity : partnerIdentityCollection) {
                partnerIdentityCollectionPartnerIdentity.setPartnerNo(null);
                partnerIdentityCollectionPartnerIdentity = em.merge(partnerIdentityCollectionPartnerIdentity);
            }
            em.remove(partner);
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

    public List<Partner> findPartnerEntities() {
        return findPartnerEntities(true, -1, -1);
    }

    public List<Partner> findPartnerEntities(int maxResults, int firstResult) {
        return findPartnerEntities(false, maxResults, firstResult);
    }

    private List<Partner> findPartnerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partner.class));
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

    public Partner findPartner(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partner.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartnerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partner> rt = cq.from(Partner.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
