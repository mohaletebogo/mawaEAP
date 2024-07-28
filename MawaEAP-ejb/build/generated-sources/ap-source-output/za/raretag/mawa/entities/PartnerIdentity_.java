package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigIdentityType;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerIdentityPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(PartnerIdentity.class)
public class PartnerIdentity_ { 

    public static volatile SingularAttribute<PartnerIdentity, Partner> partnerNo;
    public static volatile SingularAttribute<PartnerIdentity, PartnerIdentityPK> partnerIdentityPK;
    public static volatile SingularAttribute<PartnerIdentity, ConfigIdentityType> configIdentityType;
    public static volatile SingularAttribute<PartnerIdentity, Date> validFrom;
    public static volatile SingularAttribute<PartnerIdentity, Date> validTo;

}