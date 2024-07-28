package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigAmountType;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionAmountPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionAmount.class)
public class TransactionAmount_ { 

    public static volatile SingularAttribute<TransactionAmount, BigDecimal> amount;
    public static volatile SingularAttribute<TransactionAmount, ConfigAmountType> configAmountType;
    public static volatile SingularAttribute<TransactionAmount, TransactionAmountPK> transactionAmountPK;
    public static volatile SingularAttribute<TransactionAmount, Date> validFrom;
    public static volatile SingularAttribute<TransactionAmount, Transaction> transaction;
    public static volatile SingularAttribute<TransactionAmount, Date> validTo;

}