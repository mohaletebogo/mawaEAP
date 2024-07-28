package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigRoleWorkcenter;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigWorkcenter.class)
public class ConfigWorkcenter_ { 

    public static volatile SingularAttribute<ConfigWorkcenter, String> description;
    public static volatile SingularAttribute<ConfigWorkcenter, String> id;
    public static volatile SingularAttribute<ConfigWorkcenter, Date> validFrom;
    public static volatile CollectionAttribute<ConfigWorkcenter, ConfigRoleWorkcenter> configRoleWorkcenterCollection;
    public static volatile SingularAttribute<ConfigWorkcenter, Date> validTo;

}