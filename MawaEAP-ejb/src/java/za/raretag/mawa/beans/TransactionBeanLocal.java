/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionLocationPK;
import za.raretag.mawa.entities.TransactionPartnerPK;
import za.raretag.mawa.generic.Claim;
import za.raretag.mawa.generic.Dependent;
import za.raretag.mawa.generic.Item;
import za.raretag.mawa.generic.Order;
import za.raretag.mawa.generic.OrderQuery;
import za.raretag.mawa.generic.Payment;
import za.raretag.mawa.generic.Policy;
import za.raretag.mawa.generic.Request;
import za.raretag.mawa.generic.Response;

/**
 *
 * @author tebogom
 */
@Local
public interface TransactionBeanLocal {

    Response createPolicy(Policy policy);

    Response addPartner(Request request);

    Response addProduct(Request request);
    
    Response addLocation(Request request);

    Response getTransaction(String transactionNo);

    Response addDate(Request request);

    Response addPayment(Request request);

    Response setStatus(String transactionNo, String status);

    Response addDependent(Request request);

    Response createClaim(Claim claim);

    Response create(Request request);
    
    Response getTransactionPartner(String transactionNo, String partnerFunction);

    Response replaceDependent(Request request);

    Response deactivateDependent(Request request);

    Response addReferenceDocument(Request request);

    Response getTransactionType(String transactionNo);

    Response search(OrderQuery query);
    
}
