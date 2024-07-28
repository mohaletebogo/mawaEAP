package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigSalesArea;
import za.raretag.mawa.entities.Partner;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(CheckOut.class)
public class CheckOut_ { 

    public static volatile SingularAttribute<CheckOut, String> checkingId;
    public static volatile SingularAttribute<CheckOut, BigDecimal> depositAmount;
    public static volatile SingularAttribute<CheckOut, Partner> receivedBy;
    public static volatile SingularAttribute<CheckOut, Date> createdAt;
    public static volatile SingularAttribute<CheckOut, String> receiptFrom;
    public static volatile SingularAttribute<CheckOut, Partner> createdBy;
    public static volatile SingularAttribute<CheckOut, ConfigSalesArea> salesArea;
    public static volatile SingularAttribute<CheckOut, BigDecimal> cashAmount;
    public static volatile SingularAttribute<CheckOut, String> terminalId;
    public static volatile SingularAttribute<CheckOut, String> receiptTo;

}