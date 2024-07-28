package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ProductPricing;
import za.raretag.mawa.entities.TransactionItemPricing;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigProductPricing.class)
public class ConfigProductPricing_ { 

    public static volatile CollectionAttribute<ConfigProductPricing, TransactionItemPricing> transactionItemPricingCollection;
    public static volatile CollectionAttribute<ConfigProductPricing, ProductPricing> productPricingCollection;
    public static volatile SingularAttribute<ConfigProductPricing, String> description;
    public static volatile SingularAttribute<ConfigProductPricing, String> id;
    public static volatile SingularAttribute<ConfigProductPricing, Date> validFrom;
    public static volatile SingularAttribute<ConfigProductPricing, Date> validTo;

}