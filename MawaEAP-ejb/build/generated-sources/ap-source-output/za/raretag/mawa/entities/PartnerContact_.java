package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigContactType;
import za.raretag.mawa.entities.Partner;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(PartnerContact.class)
public class PartnerContact_ { 

    public static volatile SingularAttribute<PartnerContact, Partner> partnerNo;
    public static volatile SingularAttribute<PartnerContact, Integer> contactId;
    public static volatile SingularAttribute<PartnerContact, Date> validFrom;
    public static volatile SingularAttribute<PartnerContact, ConfigContactType> type;
    public static volatile SingularAttribute<PartnerContact, String> value;
    public static volatile SingularAttribute<PartnerContact, Date> validTo;

}