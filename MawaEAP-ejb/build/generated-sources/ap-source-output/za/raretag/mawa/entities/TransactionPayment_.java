package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigPaymentType;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionPayment.class)
public class TransactionPayment_ { 

    public static volatile SingularAttribute<TransactionPayment, Integer> paymentNo;
    public static volatile SingularAttribute<TransactionPayment, String> checkingId;
    public static volatile SingularAttribute<TransactionPayment, Partner> receivedBy;
    public static volatile SingularAttribute<TransactionPayment, Date> createdAt;
    public static volatile SingularAttribute<TransactionPayment, BigDecimal> amount;
    public static volatile SingularAttribute<TransactionPayment, Partner> createdBy;
    public static volatile SingularAttribute<TransactionPayment, Transaction> transactionNo;
    public static volatile SingularAttribute<TransactionPayment, String> terminalId;
    public static volatile SingularAttribute<TransactionPayment, Date> paymentDate;
    public static volatile SingularAttribute<TransactionPayment, String> externalReceiptNo;
    public static volatile SingularAttribute<TransactionPayment, ConfigPaymentType> paymentType;

}