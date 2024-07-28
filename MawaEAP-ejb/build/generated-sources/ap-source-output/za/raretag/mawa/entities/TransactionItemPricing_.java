package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigProductPricing;
import za.raretag.mawa.entities.TransactionItem;
import za.raretag.mawa.entities.TransactionItemPricingPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionItemPricing.class)
public class TransactionItemPricing_ { 

    public static volatile SingularAttribute<TransactionItemPricing, TransactionItem> transactionItem;
    public static volatile SingularAttribute<TransactionItemPricing, TransactionItemPricingPK> transactionItemPricingPK;
    public static volatile SingularAttribute<TransactionItemPricing, Date> validFrom;
    public static volatile SingularAttribute<TransactionItemPricing, ConfigProductPricing> configProductPricing;
    public static volatile SingularAttribute<TransactionItemPricing, BigDecimal> value;
    public static volatile SingularAttribute<TransactionItemPricing, Date> validTo;

}