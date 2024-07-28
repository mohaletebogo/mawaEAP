package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.PartnerIdentity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigIdentityType.class)
public class ConfigIdentityType_ { 

    public static volatile SingularAttribute<ConfigIdentityType, String> description;
    public static volatile CollectionAttribute<ConfigIdentityType, PartnerIdentity> partnerIdentityCollection;
    public static volatile SingularAttribute<ConfigIdentityType, String> id;
    public static volatile SingularAttribute<ConfigIdentityType, Date> validFrom;
    public static volatile SingularAttribute<ConfigIdentityType, Date> validTo;

}