/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.raretag.mawa.beans;

import java.util.List;
import javax.ejb.Local;
import za.raretag.mawa.generic.PartnerQuery;
import za.raretag.mawa.entities.*;
import za.raretag.mawa.generic.Contact;
import za.raretag.mawa.generic.Customer;
import za.raretag.mawa.generic.Employee;
import za.raretag.mawa.generic.Identity;
import za.raretag.mawa.generic.MessageContainer;
import za.raretag.mawa.generic.Person;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Local
public interface PartnerBeanLocal {

   // String createPartner(String idnumber,String name1, String name2, String name3, String type,String title,String gender,String maritalStatus,String birthDate,String user);
    
    Response createEmployee(Employee employee);
    
    PartnerIdentity searchCustomerByID(String idType, String idNumber);

    Partner searchCustomer(String partnerNo);

    List<Partner> searchCustomer(String surname, String firstname);

    List<Partner> searchCustomerByRole(String partnerRole);

    List<MessageContainer> addIdentity(String partnerNo, Identity ident);
    
    List<MessageContainer> addRole(String partnerNo, String role);    

    List<MessageContainer> addContact(String partnerNo,Contact contact);

    boolean addAddress(Partner Partner, String addressType, String addressLine1, String addressLine2, String addressTown, String addressCode,String user);

    List<PartnerIdentity> getAllCustomers();

    boolean setIDStatus(PartnerIdentity partnerIdentity,String user);

    boolean updatePartner(Partner partner,String user);

    List<Customer> getCustomerByID(String idnumber);

    List<Partner> searchCustomerByContact(String contactNumber);
    
    List<Partner> searchCustomerByEmail(String email);

    Response search(PartnerQuery partnerQuery);

    Response searchAll(String searchString);

    Response create(Person person);

    Response getCustomer(String partnerNo);
    
}
