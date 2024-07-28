package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigPartnerAttributes;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerAttributePK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(PartnerAttribute.class)
public class PartnerAttribute_ { 

    public static volatile SingularAttribute<PartnerAttribute, ConfigPartnerAttributes> configPartnerAttributes;
    public static volatile SingularAttribute<PartnerAttribute, PartnerAttributePK> partnerAttributePK;
    public static volatile SingularAttribute<PartnerAttribute, Partner> partner;
    public static volatile SingularAttribute<PartnerAttribute, Date> validFrom;
    public static volatile SingularAttribute<PartnerAttribute, Date> validTo;

}