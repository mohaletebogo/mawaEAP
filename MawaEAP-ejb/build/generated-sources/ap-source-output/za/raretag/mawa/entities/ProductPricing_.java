package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigProductPricing;
import za.raretag.mawa.entities.Product;
import za.raretag.mawa.entities.ProductPricingPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ProductPricing.class)
public class ProductPricing_ { 

    public static volatile SingularAttribute<ProductPricing, Product> product;
    public static volatile SingularAttribute<ProductPricing, ProductPricingPK> productPricingPK;
    public static volatile SingularAttribute<ProductPricing, Date> validFrom;
    public static volatile SingularAttribute<ProductPricing, ConfigProductPricing> configProductPricing;
    public static volatile SingularAttribute<ProductPricing, BigDecimal> value;
    public static volatile SingularAttribute<ProductPricing, Date> validTo;

}