package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.PartnerAttribute;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigPartnerAttributes.class)
public class ConfigPartnerAttributes_ { 

    public static volatile CollectionAttribute<ConfigPartnerAttributes, PartnerAttribute> partnerAttributeCollection;
    public static volatile SingularAttribute<ConfigPartnerAttributes, String> description;
    public static volatile SingularAttribute<ConfigPartnerAttributes, String> id;
    public static volatile SingularAttribute<ConfigPartnerAttributes, Date> validFrom;
    public static volatile SingularAttribute<ConfigPartnerAttributes, Date> validTo;

}