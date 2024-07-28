package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionRelation;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigTransactionType.class)
public class ConfigTransactionType_ { 

    public static volatile CollectionAttribute<ConfigTransactionType, Transaction> transactionCollection;
    public static volatile CollectionAttribute<ConfigTransactionType, TransactionRelation> transactionRelationCollection;
    public static volatile SingularAttribute<ConfigTransactionType, String> description;
    public static volatile SingularAttribute<ConfigTransactionType, String> id;
    public static volatile SingularAttribute<ConfigTransactionType, Date> validFrom;
    public static volatile SingularAttribute<ConfigTransactionType, Date> validTo;

}