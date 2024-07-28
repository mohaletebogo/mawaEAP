package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigProductAttribute;
import za.raretag.mawa.entities.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ProductAttribute.class)
public class ProductAttribute_ { 

    public static volatile SingularAttribute<ProductAttribute, Product> productCode;
    public static volatile SingularAttribute<ProductAttribute, String> attributeValue;
    public static volatile SingularAttribute<ProductAttribute, ConfigProductAttribute> attributeType;
    public static volatile SingularAttribute<ProductAttribute, Integer> attibuteId;
    public static volatile SingularAttribute<ProductAttribute, Date> validFrom;
    public static volatile SingularAttribute<ProductAttribute, Date> validTo;

}