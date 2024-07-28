package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigAddressType;
import za.raretag.mawa.entities.Partner;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(PartnerAddress.class)
public class PartnerAddress_ { 

    public static volatile SingularAttribute<PartnerAddress, Partner> partnerNo;
    public static volatile SingularAttribute<PartnerAddress, ConfigAddressType> addressType;
    public static volatile SingularAttribute<PartnerAddress, String> postalCode;
    public static volatile SingularAttribute<PartnerAddress, String> addressLine1;
    public static volatile SingularAttribute<PartnerAddress, String> addressLine2;
    public static volatile SingularAttribute<PartnerAddress, String> addressLine3;
    public static volatile SingularAttribute<PartnerAddress, Date> validFrom;
    public static volatile SingularAttribute<PartnerAddress, Integer> addressId;
    public static volatile SingularAttribute<PartnerAddress, Date> validTo;

}