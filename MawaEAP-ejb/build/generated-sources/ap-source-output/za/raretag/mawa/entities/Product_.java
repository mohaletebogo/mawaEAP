package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ProductAttribute;
import za.raretag.mawa.entities.ProductPricing;
import za.raretag.mawa.entities.TransactionItem;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, String> productCode;
    public static volatile CollectionAttribute<Product, ProductPricing> productPricingCollection;
    public static volatile CollectionAttribute<Product, TransactionItem> transactionItemCollection;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile CollectionAttribute<Product, ProductAttribute> productAttributeCollection;
    public static volatile SingularAttribute<Product, Date> validFrom;
    public static volatile SingularAttribute<Product, Date> validTo;

}