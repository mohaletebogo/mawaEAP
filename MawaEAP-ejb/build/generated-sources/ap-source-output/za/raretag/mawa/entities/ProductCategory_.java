package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ProductCategoryPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ProductCategory.class)
public class ProductCategory_ { 

    public static volatile SingularAttribute<ProductCategory, ProductCategoryPK> productCategoryPK;
    public static volatile SingularAttribute<ProductCategory, Date> validFrom;
    public static volatile SingularAttribute<ProductCategory, Date> validTo;

}