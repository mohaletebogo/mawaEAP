package za.raretag.mawa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigRoleType;
import za.raretag.mawa.entities.ConfigRoleWorkcenterPK;
import za.raretag.mawa.entities.ConfigWorkcenter;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(ConfigRoleWorkcenter.class)
public class ConfigRoleWorkcenter_ { 

    public static volatile SingularAttribute<ConfigRoleWorkcenter, Integer> sequence;
    public static volatile SingularAttribute<ConfigRoleWorkcenter, ConfigRoleWorkcenterPK> configRoleWorkcenterPK;
    public static volatile SingularAttribute<ConfigRoleWorkcenter, ConfigRoleType> configRoleType;
    public static volatile SingularAttribute<ConfigRoleWorkcenter, String> active;
    public static volatile SingularAttribute<ConfigRoleWorkcenter, ConfigWorkcenter> configWorkcenter;

}