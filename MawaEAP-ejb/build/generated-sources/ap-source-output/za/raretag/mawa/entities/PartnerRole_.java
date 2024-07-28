package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigRoleType;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerRolePK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(PartnerRole.class)
public class PartnerRole_ { 

    public static volatile SingularAttribute<PartnerRole, Partner> partner;
    public static volatile SingularAttribute<PartnerRole, ConfigRoleType> configRoleType;
    public static volatile SingularAttribute<PartnerRole, PartnerRolePK> partnerRolePK;
    public static volatile SingularAttribute<PartnerRole, Date> validFrom;
    public static volatile SingularAttribute<PartnerRole, Date> validTo;

}