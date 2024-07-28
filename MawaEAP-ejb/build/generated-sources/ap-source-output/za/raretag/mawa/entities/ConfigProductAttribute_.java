package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ProductAttribute;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(ConfigProductAttribute.class)
public class ConfigProductAttribute_ { 

    public static volatile SingularAttribute<ConfigProductAttribute, String> description;
    public static volatile CollectionAttribute<ConfigProductAttribute, ProductAttribute> productAttributeCollection;
    public static volatile SingularAttribute<ConfigProductAttribute, String> id;
    public static volatile SingularAttribute<ConfigProductAttribute, Date> validFrom;
    public static volatile SingularAttribute<ConfigProductAttribute, Date> validTo;

}