package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigRoleWorkcenter;
import za.raretag.mawa.entities.PartnerRole;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigRoleType.class)
public class ConfigRoleType_ { 

    public static volatile SingularAttribute<ConfigRoleType, String> description;
    public static volatile CollectionAttribute<ConfigRoleType, PartnerRole> partnerRoleCollection;
    public static volatile SingularAttribute<ConfigRoleType, String> id;
    public static volatile SingularAttribute<ConfigRoleType, Date> validFrom;
    public static volatile CollectionAttribute<ConfigRoleType, ConfigRoleWorkcenter> configRoleWorkcenterCollection;
    public static volatile SingularAttribute<ConfigRoleType, Date> validTo;

}