package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionStatus;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(ConfigStatusType.class)
public class ConfigStatusType_ { 

    public static volatile CollectionAttribute<ConfigStatusType, Transaction> transactionCollection;
    public static volatile CollectionAttribute<ConfigStatusType, TransactionStatus> transactionStatusCollection;
    public static volatile SingularAttribute<ConfigStatusType, String> description;
    public static volatile SingularAttribute<ConfigStatusType, String> id;
    public static volatile SingularAttribute<ConfigStatusType, Date> validFrom;
    public static volatile SingularAttribute<ConfigStatusType, Date> validTo;

}