package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionTrip.class)
public class TransactionTrip_ { 

    public static volatile SingularAttribute<TransactionTrip, String> endPoint;
    public static volatile SingularAttribute<TransactionTrip, String> driver;
    public static volatile SingularAttribute<TransactionTrip, BigDecimal> distance;
    public static volatile SingularAttribute<TransactionTrip, String> transactionNo;
    public static volatile SingularAttribute<TransactionTrip, String> startPoint;
    public static volatile SingularAttribute<TransactionTrip, Integer> tripId;
    public static volatile SingularAttribute<TransactionTrip, String> vehicleId;
    public static volatile SingularAttribute<TransactionTrip, Date> validFrom;
    public static volatile SingularAttribute<TransactionTrip, Date> validTo;

}