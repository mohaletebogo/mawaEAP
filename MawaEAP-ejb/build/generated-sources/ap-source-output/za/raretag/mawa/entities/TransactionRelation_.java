package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigTransactionType;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionRelationPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionRelation.class)
public class TransactionRelation_ { 

    public static volatile SingularAttribute<TransactionRelation, TransactionRelationPK> transactionRelationPK;
    public static volatile SingularAttribute<TransactionRelation, ConfigTransactionType> configTransactionType;
    public static volatile SingularAttribute<TransactionRelation, Transaction> transaction1;
    public static volatile SingularAttribute<TransactionRelation, Date> validFrom;
    public static volatile SingularAttribute<TransactionRelation, Transaction> transaction;
    public static volatile SingularAttribute<TransactionRelation, Date> validTo;

}