package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigLocationType;
import za.raretag.mawa.entities.ConfigSalesArea;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionLocationPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(TransactionLocation.class)
public class TransactionLocation_ { 

    public static volatile SingularAttribute<TransactionLocation, TransactionLocationPK> transactionLocationPK;
    public static volatile SingularAttribute<TransactionLocation, ConfigSalesArea> configSalesArea;
    public static volatile SingularAttribute<TransactionLocation, Date> validFrom;
    public static volatile SingularAttribute<TransactionLocation, ConfigLocationType> configLocationType;
    public static volatile SingularAttribute<TransactionLocation, Transaction> transaction;
    public static volatile SingularAttribute<TransactionLocation, Date> validTo;

}