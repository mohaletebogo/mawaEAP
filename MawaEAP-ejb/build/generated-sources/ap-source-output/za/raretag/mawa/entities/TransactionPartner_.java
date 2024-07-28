package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigPartnerFunction;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionPartnerPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionPartner.class)
public class TransactionPartner_ { 

    public static volatile SingularAttribute<TransactionPartner, Date> statusDate;
    public static volatile SingularAttribute<TransactionPartner, TransactionPartnerPK> transactionPartnerPK;
    public static volatile SingularAttribute<TransactionPartner, String> statusReason;
    public static volatile SingularAttribute<TransactionPartner, Partner> partner;
    public static volatile SingularAttribute<TransactionPartner, String> createdBy;
    public static volatile SingularAttribute<TransactionPartner, ConfigPartnerFunction> configPartnerFunction;
    public static volatile SingularAttribute<TransactionPartner, Date> dateEffective;
    public static volatile SingularAttribute<TransactionPartner, Date> validFrom;
    public static volatile SingularAttribute<TransactionPartner, Date> dateAdded;
    public static volatile SingularAttribute<TransactionPartner, Transaction> transaction;
    public static volatile SingularAttribute<TransactionPartner, String> status;
    public static volatile SingularAttribute<TransactionPartner, Date> validTo;

}