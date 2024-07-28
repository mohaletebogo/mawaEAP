/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import static com.sun.xml.bind.util.CalendarConv.formatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import za.raretag.mawa.generic.PartnerQuery;
import za.raretag.mawa.entities.*;
import za.raretag.mawa.entities.controllers.*;
import za.raretag.mawa.entities.controllers.exceptions.*;
import za.raretag.mawa.generic.Contact;
import za.raretag.mawa.generic.Controllers;
import za.raretag.mawa.generic.Conversion;
import za.raretag.mawa.generic.Customer;
import za.raretag.mawa.generic.Data;
import za.raretag.mawa.generic.Employee;
import za.raretag.mawa.generic.Identity;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Order;
import za.raretag.mawa.generic.Person;
import za.raretag.mawa.generic.Policy;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PartnerBean implements PartnerBeanLocal {

    @EJB
    private UserBeanLocal userBean;

    @PersistenceContext(unitName = "MawaEAP-ejbPU")
    EntityManager em;
    @Resource
    EJBContext context;
    EntityManagerFactory emf;
    UserTransaction utx;

    @EJB
    private NumberRangeBeanLocal numberRangeBean;

    PartnerJpaController partnerController;
    PartnerIdentityJpaController partnerIdentityController;
    PartnerRoleJpaController partnerRoleController;
    PartnerContactJpaController partnerContactController;
    PartnerAddressJpaController partnerAddressController;
    UserJpaController userController;
    ConfigContactTypeJpaController contactTypeController;
    ConfigIdentityTypeJpaController identityController;
    ConfigRoleTypeJpaController roleTypeController;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void instantiateControllers() {
        emf = em.getEntityManagerFactory();
        utx = context.getUserTransaction();
        partnerController = new PartnerJpaController(utx, emf);
        partnerIdentityController = new PartnerIdentityJpaController(utx, emf);
        partnerContactController = new PartnerContactJpaController(utx, emf);
        partnerRoleController = new PartnerRoleJpaController(utx, emf);
        userController = new UserJpaController(utx, emf);
        contactTypeController = new ConfigContactTypeJpaController(utx, emf);
        identityController = new ConfigIdentityTypeJpaController(utx, emf);
        roleTypeController = new ConfigRoleTypeJpaController(utx, emf);

    }

    @Override
    public Response createEmployee(Employee employee) {
        instantiateControllers();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<String> data = new Data();

        String partnerNo = numberRangeBean.getNumber("EMPLOYEE");
        Partner newPartner = new Partner(partnerNo);
        newPartner.setName1(employee.getLastName().toUpperCase());
        newPartner.setName2(employee.getFirstName().toUpperCase());
        // newPartner.setName3(employee.getMiddleName().toUpperCase());
        newPartner.setValidFrom(new Date());
        newPartner.setValidTo(Conversion.stringToDate("9999-12-31"));

        try {
            partnerController.create(newPartner);
            data.set(partnerNo);
            MessageContainer message = new MessageContainer("S", "Employee " + partnerNo + " Created Succesfully");
            messageList.add(message);
            PartnerRolePK newPartnerRolePK = new PartnerRolePK("EMPLOYEE", partnerNo);
            PartnerRole newPartnerRole = new PartnerRole();
            newPartnerRole.setPartnerRolePK(newPartnerRolePK);
            newPartnerRole.setValidFrom(new Date());
            newPartnerRole.setValidTo(Conversion.stringToDate("9999-12-31"));
            partnerRoleController.create(newPartnerRole);
            message = new MessageContainer("S", "Employee role added");
            messageList.add(message);

            for (Identity ident : employee.getIdentity()) {
                messageList.addAll(addIdentity(partnerNo, ident));
            }

            for (Contact contact : employee.getContacts()) {
                messageList.addAll(addContact(partnerNo, contact));
            }

            messageList.addAll(userBean.createUser(partnerNo, employee.getRole(), ""));

        } catch (RollbackFailureException ex) {
            Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }

    @Override
    public PartnerIdentity searchCustomerByID(String idType, String idNumber) {
        instantiateControllers();
        PartnerIdentityPK newPartnerIdentityPK;
        PartnerIdentity foundPartnerIdentity = null;
        if (idNumber != null || !"".equals(idNumber)) {
            newPartnerIdentityPK = new PartnerIdentityPK(idType, idNumber);
            foundPartnerIdentity = partnerIdentityController.findPartnerIdentity(newPartnerIdentityPK);
        }
        return foundPartnerIdentity;
    }

    @Override
    public Partner searchCustomer(String partnerNo) {
        instantiateControllers();
        return partnerController.findPartner(partnerNo);
    }

    @Override
    public List<Partner> searchCustomer(String surname, String firstname) {
        instantiateControllers();
        return searchCustomerBySurname(surname);
    }

    @Override
    public List<Partner> searchCustomerByRole(String partnerRole) {
        instantiateControllers();
        return searchByPartnerRole(partnerRole);
    }

    @Override
    public List<MessageContainer> addIdentity(String partnerNo, Identity ident) {
        instantiateControllers();
        String idType = ident.getType();
        String idNumber = ident.getNumber();
        List<MessageContainer> messageList = new ArrayList<>();
        if (idNumber != null || !"".equals(idNumber)) {
            PartnerIdentityPK newPartnerIdentityPK = new PartnerIdentityPK(idType, idNumber);
            PartnerIdentity oldPartnerIdentity = partnerIdentityController.findPartnerIdentity(newPartnerIdentityPK);
            if (oldPartnerIdentity == null) {
                try {
                    PartnerIdentity newPartnerIdentity = new PartnerIdentity();
                    newPartnerIdentity.setPartnerIdentityPK(newPartnerIdentityPK);
                    newPartnerIdentity.setValidFrom(new Date());
                    newPartnerIdentity.setValidTo(Conversion.stringToDate("9999-12-31"));
                    newPartnerIdentity.setConfigIdentityType(identityController.findConfigIdentityType(idType));
                    newPartnerIdentity.setPartnerNo(partnerController.findPartner(partnerNo));
                    partnerIdentityController.create(newPartnerIdentity);
                    MessageContainer message = new MessageContainer("S", "ID Number " + idNumber + " Added Successfully");
                    messageList.add(message);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
                    MessageContainer message = new MessageContainer("E", ex.getMessage());
                    messageList.add(message);
                } catch (Exception ex) {
                    Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
                    MessageContainer message = new MessageContainer("E", ex.getMessage());
                    messageList.add(message);
                }
            } else {
                MessageContainer message = new MessageContainer("E", "ID Number " + idNumber + " Already Exist");
                messageList.add(message);
            }
        }

        return messageList;
    }

    @Override
    public List<MessageContainer> addRole(String partnerNo, String role) {
        instantiateControllers();

        List<MessageContainer> messageList = new ArrayList<>();

        PartnerRolePK newPartnerRolePK = new PartnerRolePK(role, partnerNo);
        PartnerRole oldPartnerRole = partnerRoleController.findPartnerRole(newPartnerRolePK);
        if (oldPartnerRole == null) {
            try {
                PartnerRole newPartnerRole = new PartnerRole();
                newPartnerRole.setPartnerRolePK(newPartnerRolePK);
                newPartnerRole.setConfigRoleType(roleTypeController.findConfigRoleType(role));
                newPartnerRole.setPartner(partnerController.findPartner(partnerNo));
                newPartnerRole.setValidFrom(new Date());
                newPartnerRole.setValidTo(Conversion.stringToDate("9999-12-31"));
                partnerRoleController.create(newPartnerRole);
                MessageContainer message = new MessageContainer("S", "Role Added Successfully");
                messageList.add(message);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
                MessageContainer message = new MessageContainer("E", ex.getMessage());
                messageList.add(message);
            } catch (Exception ex) {
                Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
                MessageContainer message = new MessageContainer("E", ex.getMessage());
                messageList.add(message);
            }
        } else {
            MessageContainer message = new MessageContainer("E", "Role Already Exist");
            messageList.add(message);
        }

        return messageList;
    }

    @Override
    public List<MessageContainer> addContact(String partnerNo, Contact contact) {
        instantiateControllers();
        List<MessageContainer> messageList = new ArrayList<>();
        try {
            PartnerContact partnerContact = new PartnerContact();
            partnerContact.setPartnerNo(partnerController.findPartner(partnerNo));
            partnerContact.setType(contactTypeController.findConfigContactType(contact.getContactType()));
            partnerContact.setValue(contact.getContactValue());
            partnerContact.setValidFrom(new Date());
            partnerContact.setValidTo(Conversion.stringToDate("9999-12-31"));
            partnerContactController.create(partnerContact);

            MessageContainer message = new MessageContainer("S", "Contact Number Added Successfully");
            messageList.add(message);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return messageList;
    }

    @Override
    public boolean addAddress(Partner partner, String addressType, String addressLine1, String addressLine2, String addressTown, String addressCode, String user) {

//        Partner userPartner = userController.findUser(user).getEmployeeNo();
        boolean saved;
//        try {
//
//            PartnerAddressPK partnerAddressPK = new PartnerAddressPK(partner.getPartnerNo(), addressType);
//            PartnerAddress oldPartnerAddress = partnerAddressController.findPartnerAddress(partnerAddressPK);
//
//            if (oldPartnerAddress != null) {
//                partnerAddressController.destroy(partnerAddressPK);
//            }
//
//            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            PartnerAddress partnerAddress = new PartnerAddress();
//            partnerAddress.setAddressLine1(addressLine1);
//            partnerAddress.setAddressLine2(addressLine2);
//            partnerAddress.setAddressLine3(addressTown);
//            partnerAddress.setAddressLine4(addressCode);
//            partnerAddress.setAddressLine5("");
//            partnerAddress.setAddressLine6("");
//            partnerAddress.setPartner(partner);
//            ConfigAddressType configAddressType = addressTypeController.findConfigAddressType(addressType);
//            partnerAddress.setConfigAddressType(configAddressType);
//
//            partnerAddress.setPartnerAddressPK(partnerAddressPK);
//            partnerAddress.setValidFrom(new Date());
//            Date newPartnerAddressDate;
//            newPartnerAddressDate = formatter.parse("9999-12-31");
//            partnerAddress.setValidTo(newPartnerAddressDate);
//            partnerAddress.setCreatedBy(userPartner);
//            partnerAddress.setCreatedOn(new Date());
//            partnerAddressController.create(partnerAddress);
//            saved = true;
//
//        } catch (Exception ex) {
//            Logger.getLogger(PartnerBean.class
//                    .getName()).log(Level.SEVERE, null, ex);
//            saved = false;
//        }
        return false;
    }

    @Override
    public List<PartnerIdentity> getAllCustomers() {
        instantiateControllers();
        return partnerIdentityController.findPartnerIdentityEntities();
    }

    @Override
    public boolean setIDStatus(PartnerIdentity partnerIdentity, String user) {
//        try {
//            instantiateControllers();
//            Partner userPartner = userController.findUser(user).getEmployeeNo();
//            partnerIdentity.setLastChangedBy(userPartner);
//            partnerIdentity.setLastChangedOn(new Date());
//            partnerIdentityController.edit(partnerIdentity);
//
//        } catch (RollbackFailureException ex) {
//            Logger.getLogger(PartnerBean.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(PartnerBean.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
        return false;
    }

    @Override
    public boolean updatePartner(Partner partner, String user) {
        boolean updated = false;

//        Partner userPartner = userController.findUser(user).getEmployeeNo();
//        partner.setLastChangedBy(userPartner);
//        partner.setLastChangedOn(new Date());
//        try {
//            partnerController.edit(partner);
//            updated = true;
//
//        } catch (NonexistentEntityException ex) {
//            Logger.getLogger(PartnerBean.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } catch (RollbackFailureException ex) {
//            Logger.getLogger(PartnerBean.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(PartnerBean.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
        return updated;
    }

    @Override
    public List<Customer> getCustomerByID(String idnumber) {

        instantiateControllers();
        PartnerIdentityPK newPartnerIdentityPK;
        List<Customer> customerList = new ArrayList<>();
//        if (idnumber != null || !"".equals(idnumber)) {
//            PartnerIdentity foundPartnerIdentity = null;
//            newPartnerIdentityPK = new PartnerIdentityPK("ID01", idnumber);
//            try {
//                Customer foundCustomer = new Customer();
//                foundPartnerIdentity = partnerIdentityController.findPartnerIdentity(newPartnerIdentityPK);
//                foundCustomer.setCustomerNo(foundPartnerIdentity.getPartnerNo().getPartnerNo());
//                foundCustomer.setIdNumber(foundPartnerIdentity.getPartnerIdentityPK().getIdNumber());
//                foundCustomer.setName1(foundPartnerIdentity.getPartnerNo().getName1());
//                foundCustomer.setName2(foundPartnerIdentity.getPartnerNo().getName2());
//                customerList.add(foundCustomer);
//            } catch (Exception ex) {
//            }
//
//        }
        return customerList;
    }

    @Override
    public List<Partner> searchCustomerByContact(String contactNumber) {
        instantiateControllers();
        List<Partner> partnerList = new ArrayList<>();

//        try {
//            TypedQuery<PartnerContact> queryPartnerByContact = em.createNamedQuery("PartnerContact.findByContactNumber", PartnerContact.class
//            );
//            queryPartnerByContact.setParameter(
//                    "contactNumber", contactNumber);
//
//            List<PartnerContact> partnerContactList = queryPartnerByContact.getResultList();
//
//            for (Iterator<PartnerContact> it = partnerContactList.iterator();
//                    it.hasNext();) {
//                PartnerContact lr_partnerContact = it.next();
//                Partner partner = lr_partnerContact.getPartner();
//                partnerList.add(partner);
//            }
//
//        } finally {
//            // em.close();
//
//        }
        return partnerList;
    }

    private List<PartnerIdentity> searchCustomerByID(String idnumber) {
        instantiateControllers();
        List<PartnerIdentityPK> partnerIdentityPKList = new ArrayList<>();
        List<PartnerIdentity> partnerIdentityList = new ArrayList<>();
        idnumber = "%" + idnumber + "%";
        try {
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//
//            CriteriaQuery<PartnerIdentity> q = cb.createQuery(PartnerIdentity.class);
//            Root<PartnerIdentity> c = q.from(PartnerIdentity.class);
//            ParameterExpression<String> p = cb.parameter(String.class);
//            q.select(c).where(cb.like(c.get("idNumber"), p));
//            

            TypedQuery<PartnerIdentity> query
                    = em.createQuery("SELECT p FROM PartnerIdentity p WHERE p.partnerIdentityPK.idNumber like :idNumber", PartnerIdentity.class);
            query.setParameter("idNumber", idnumber);
            partnerIdentityList = query.getResultList();
            for (PartnerIdentityPK partnerIdentityPK : partnerIdentityPKList) {
                partnerIdentityList.add(partnerIdentityController.findPartnerIdentity(partnerIdentityPK));
            }

        } finally {
            //em.close();

        }
        return partnerIdentityList;
    }

    private List<Partner> searchCustomerBySurname(String surname) {
        instantiateControllers();
        List<Partner> partnerList = new ArrayList<>();
        surname = "%" + surname + "%";
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Partner> q = cb.createQuery(Partner.class);
            Root<Partner> c = q.from(Partner.class);
            ParameterExpression<String> p = cb.parameter(String.class);
            q.select(c).where(cb.like(c.get("name1"), p));

            TypedQuery<Partner> query = em.createQuery(q);
            query.setParameter(p, surname);
            partnerList = query.getResultList();

        } finally {
            //em.close();

        }
        return partnerList;
    }

    private List<Partner> searchByPartnerRole(String role) {
        instantiateControllers();
        List<Partner> partnerList = new ArrayList<>();
        List<PartnerRole> partnerRoleList = new ArrayList<>();

        try {
            TypedQuery<PartnerRole> query = em.createNamedQuery("PartnerRole.findByRoleId", PartnerRole.class
            );
            query.setParameter(
                    "roleId", role);
            partnerRoleList = query.getResultList();

            for (PartnerRole partnerRole : partnerRoleList) {
                partnerList.add(partnerController.findPartner(partnerRole.getPartnerRolePK().getPartnerNo()));
            }

        } finally {
            em.close();

        }
        return partnerList;
    }

    @Override
    public List<Partner> searchCustomerByEmail(String email) {
        instantiateControllers();
        List<Partner> partnerList = new ArrayList<>();

        try {
            TypedQuery<PartnerContact> queryPartnerByContact = em.createNamedQuery("PartnerContact.findByValue", PartnerContact.class
            );
            queryPartnerByContact.setParameter(
                    "value", email);

            List<PartnerContact> partnerContactList = queryPartnerByContact.getResultList();
//
            for (PartnerContact lr_partnerContact : partnerContactList) {
                partnerList.add(lr_partnerContact.getPartnerNo());
            }

        } finally {
            // em.close();

        }
        return partnerList;
    }

    private List<PartnerIdentity> getPartnerIdentities(String partnerNo) {
        instantiateControllers();
        List<PartnerIdentity> partnerIdentityList = new ArrayList<>();

        try {
            TypedQuery<PartnerIdentity> queryPartnerIdentity = em.createNamedQuery("PartnerIdentity.findByPartnerNo", PartnerIdentity.class
            );
            queryPartnerIdentity.setParameter(
                    "partnerNo", partnerNo);

            partnerIdentityList = queryPartnerIdentity.getResultList();

        } finally {
            // em.close();

        }
        return partnerIdentityList;
    }

    @Override
    public Response search(PartnerQuery partnerQuery) {
        instantiateControllers();
        List<MessageContainer> messageList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        List<PartnerIdentity> identityList = new ArrayList<>();
        Data<List<Customer>> data = new Data();

        List<PartnerIdentity> partnerIdentityList = searchCustomerByID(partnerQuery.getSearchTerm());
        for (PartnerIdentity partnerIdentity : partnerIdentityList) {
            customerList.add(convertPartnerToCustomer(partnerIdentity.getPartnerNo()));
        }

        List<Partner> surnameList = searchCustomerBySurname(partnerQuery.getSearchTerm());
        for (Partner partner : surnameList) {
            customerList.add(convertPartnerToCustomer(partner));
        }

        if (!"".equals(partnerQuery.getSearchTerm()) && partnerQuery.getSearchTerm() != null) {
            Partner partnerByNo = partnerController.findPartner(partnerQuery.getSearchTerm());
            if (partnerByNo != null) {
                customerList.add(convertPartnerToCustomer(partnerByNo));
            }
        }
        data.set(customerList);
        return new Response(data, messageList);
    }

    @Override
    public Response searchAll(String searchString) {
        return null;
    }

    @Override
    public Response create(Person person) {
        instantiateControllers();
        List<MessageContainer> messageList = new ArrayList<>();
        Boolean isEmployee = false;
        String employeeEmail = null;
        Data<String> data = new Data();
        Customer cust = null;
        String partnerNo = null;
        try {
            for (Identity ident : person.getIdentity()) {
                PartnerQuery partQuery = new PartnerQuery();
                partQuery.setSearchTerm(ident.getNumber());
                Response resp = search(partQuery);
                Data<List<Customer>> customers = resp.getData();
                if (customers.get().iterator().hasNext()) {
                    cust = customers.get().iterator().next();

                }
            }
            if (cust != null) {
                data.set(cust.getId());
                partnerNo = cust.getId();
            } else {
                partnerNo = numberRangeBean.getNumber("PERSON");
                Partner newPartner = new Partner(partnerNo);
                newPartner.setName1(person.getLastName().toUpperCase());
                newPartner.setName2(person.getFirstName().toUpperCase());
                newPartner.setName3(person.getMiddleName().toUpperCase());
                newPartner.setValidFrom(new Date());
                newPartner.setValidTo(Conversion.stringToDate("9999-12-31"));

                partnerController.create(newPartner);
                data.set(partnerNo);
                MessageContainer message = new MessageContainer("S", "Partner " + partnerNo + " Created Succesfully");
                messageList.add(message);

                if (!person.getIdentity().isEmpty()) {
                    for (Identity ident : person.getIdentity()) {
                        messageList.addAll(addIdentity(partnerNo, ident));
                    }
                }
                if (!person.getContacts().isEmpty()) {
                    for (Contact contact : person.getContacts()) {
                        if (contact.getContactType().equals("EMAIL")) {
                            employeeEmail = contact.getContactValue();
                        }
                        messageList.addAll(addContact(partnerNo, contact));
                    }
                }

            }
            if (!person.getRoles().isEmpty()) {
                for (String role : person.getRoles()) {
                    if ("EMPLOYEE".equals(role)) {
                        isEmployee = true;
                    }
                    messageList.addAll(addRole(partnerNo, role));
                }
            }
            if (isEmployee) {
                messageList.addAll(userBean.createUser(partnerNo, "ADMINISTRATOR", employeeEmail));
            }
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        } catch (Exception ex) {
            Logger.getLogger(PartnerBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageContainer message = new MessageContainer("E", ex.getMessage());
            messageList.add(message);
        }
        return new Response(data, messageList);
    }

    @Override
    public Response getCustomer(String partnerNo) {
        instantiateControllers();
        List<MessageContainer> messageList = new ArrayList<>();
        Data<Customer> data = new Data();
        Partner partner = partnerController.findPartner(partnerNo);
        data.set(convertPartnerToCustomer(partner));
        return new Response(data, messageList);
    }

    private Customer convertPartnerToCustomer(Partner partner) {
        String idNumber = "";
        List<Identity> custIdentList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        List<Policy> policyList = new ArrayList<>();
        Iterator<PartnerIdentity> identityIterator = partner.getPartnerIdentityCollection().iterator();
        while (identityIterator.hasNext()) {
            PartnerIdentity partnerIdentity = identityIterator.next();
            Identity identity = new Identity();
            identity.setType(partnerIdentity.getPartnerIdentityPK().getIdType());
            identity.setNumber(partnerIdentity.getPartnerIdentityPK().getIdNumber());
            custIdentList.add(identity);
            if ("".equals(idNumber)) {
                idNumber = partnerIdentity.getPartnerIdentityPK().getIdNumber();
            }
        }
        Customer customer = new Customer();
        customer.setIdNumber(idNumber);
        customer.setIdentity(custIdentList);
        customer.setLastName(partner.getName1());
        customer.setFirstName(partner.getName2());
        customer.setMiddleName(partner.getName3());
        customer.setFullName(Conversion.getPartnerName(partner));
        customer.setId(partner.getPartnerNo());

        Iterator<TransactionPartner> transactionIterator = partner.getTransactionPartnerCollection().iterator();
        while (transactionIterator.hasNext()) {
            TransactionPartner trxPartner = transactionIterator.next();
            if (!"SALESREPRESENTATIVE".equals(trxPartner.getConfigPartnerFunction().getId())) {
                if ("FUNERALPOLICY".equals(trxPartner.getTransaction().getTransactionType().getId())) {
                    if (!"CANCELLED".equals(trxPartner.getStatus())) {
                        Policy policy = new Policy(trxPartner.getTransaction());
                        policyList.add(policy);
                    }
                } else {
                    Order order = new Order();
                    order.setId(trxPartner.getTransaction().getTransactionNo());
                    order.setType(trxPartner.getTransaction().getTransactionType().getDescription());
                    order.setStatus(trxPartner.getTransaction().getStatus().getDescription());
                    orderList.add(order);
                }
            }
        }
        customer.setOrders(orderList);
        customer.setPolicies(policyList);
        return customer;
    }

    private Customer convertIdentityToCustomer(PartnerIdentity partnerIdentity) {
        Identity identity = new Identity();
        identity.setType(partnerIdentity.getPartnerIdentityPK().getIdType());
        identity.setNumber(partnerIdentity.getPartnerIdentityPK().getIdNumber());
        List<Identity> custIdentList = new ArrayList<>();
        custIdentList.add(identity);
        Customer customer = new Customer();
        customer.setIdentity(custIdentList);
        Partner partner = partnerIdentity.getPartnerNo();
        customer.setLastName(partner.getName1());
        customer.setFirstName(partner.getName2());
        customer.setMiddleName(partner.getName3());
        customer.setId(partner.getPartnerNo());
        return customer;
    }

}
