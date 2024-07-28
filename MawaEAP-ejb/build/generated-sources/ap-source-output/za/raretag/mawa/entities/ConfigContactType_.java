package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.PartnerContact;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigContactType.class)
public class ConfigContactType_ { 

    public static volatile SingularAttribute<ConfigContactType, String> description;
    public static volatile SingularAttribute<ConfigContactType, String> id;
    public static volatile SingularAttribute<ConfigContactType, Date> validFrom;
    public static volatile CollectionAttribute<ConfigContactType, PartnerContact> partnerContactCollection;
    public static volatile SingularAttribute<ConfigContactType, Date> validTo;

}