package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigStatusType;
import za.raretag.mawa.entities.ConfigTransactionType;
import za.raretag.mawa.entities.TransactionAmount;
import za.raretag.mawa.entities.TransactionBank;
import za.raretag.mawa.entities.TransactionDate;
import za.raretag.mawa.entities.TransactionDocument;
import za.raretag.mawa.entities.TransactionItem;
import za.raretag.mawa.entities.TransactionLocation;
import za.raretag.mawa.entities.TransactionPartner;
import za.raretag.mawa.entities.TransactionPayment;
import za.raretag.mawa.entities.TransactionRelation;
import za.raretag.mawa.entities.TransactionStatus;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(Transaction.class)
public class Transaction_ { 

    public static volatile SingularAttribute<Transaction, Date> statusDate;
    public static volatile CollectionAttribute<Transaction, TransactionItem> transactionItemCollection;
    public static volatile SingularAttribute<Transaction, TransactionDocument> transactionDocument;
    public static volatile SingularAttribute<Transaction, String> transactionNo;
    public static volatile SingularAttribute<Transaction, String> description;
    public static volatile SingularAttribute<Transaction, Date> validFrom;
    public static volatile CollectionAttribute<Transaction, TransactionPayment> transactionPaymentCollection;
    public static volatile CollectionAttribute<Transaction, TransactionRelation> transactionRelationCollection1;
    public static volatile CollectionAttribute<Transaction, TransactionBank> transactionBankCollection;
    public static volatile SingularAttribute<Transaction, ConfigTransactionType> transactionType;
    public static volatile CollectionAttribute<Transaction, TransactionStatus> transactionStatusCollection;
    public static volatile CollectionAttribute<Transaction, TransactionLocation> transactionLocationCollection;
    public static volatile CollectionAttribute<Transaction, TransactionRelation> transactionRelationCollection;
    public static volatile CollectionAttribute<Transaction, TransactionDate> transactionDateCollection;
    public static volatile CollectionAttribute<Transaction, TransactionPartner> transactionPartnerCollection;
    public static volatile CollectionAttribute<Transaction, TransactionAmount> transactionAmountCollection;
    public static volatile SingularAttribute<Transaction, Date> validTo;
    public static volatile SingularAttribute<Transaction, ConfigStatusType> status;

}