package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigStatusType;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionStatusPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionStatus.class)
public class TransactionStatus_ { 

    public static volatile SingularAttribute<TransactionStatus, TransactionStatusPK> transactionStatusPK;
    public static volatile SingularAttribute<TransactionStatus, Date> statusDate;
    public static volatile SingularAttribute<TransactionStatus, String> createdBy;
    public static volatile SingularAttribute<TransactionStatus, ConfigStatusType> configStatusType;
    public static volatile SingularAttribute<TransactionStatus, String> active;
    public static volatile SingularAttribute<TransactionStatus, Transaction> transaction;

}