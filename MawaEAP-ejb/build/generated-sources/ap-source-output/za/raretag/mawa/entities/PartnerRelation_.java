package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigRelationType;
import za.raretag.mawa.entities.Partner;
import za.raretag.mawa.entities.PartnerRelationPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(PartnerRelation.class)
public class PartnerRelation_ { 

    public static volatile SingularAttribute<PartnerRelation, Partner> partner;
    public static volatile SingularAttribute<PartnerRelation, ConfigRelationType> configRelationType;
    public static volatile SingularAttribute<PartnerRelation, Partner> partner1;
    public static volatile SingularAttribute<PartnerRelation, PartnerRelationPK> partnerRelationPK;
    public static volatile SingularAttribute<PartnerRelation, Date> validFrom;
    public static volatile SingularAttribute<PartnerRelation, Date> validTo;

}