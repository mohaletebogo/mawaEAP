package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.PartnerAddress;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigAddressType.class)
public class ConfigAddressType_ { 

    public static volatile SingularAttribute<ConfigAddressType, String> description;
    public static volatile SingularAttribute<ConfigAddressType, String> id;
    public static volatile SingularAttribute<ConfigAddressType, Date> validFrom;
    public static volatile CollectionAttribute<ConfigAddressType, PartnerAddress> partnerAddressCollection;
    public static volatile SingularAttribute<ConfigAddressType, Date> validTo;

}