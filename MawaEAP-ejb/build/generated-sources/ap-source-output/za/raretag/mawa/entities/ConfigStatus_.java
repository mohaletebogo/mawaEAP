package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigStatus.class)
public class ConfigStatus_ { 

    public static volatile SingularAttribute<ConfigStatus, String> description;
    public static volatile SingularAttribute<ConfigStatus, String> id;
    public static volatile SingularAttribute<ConfigStatus, Date> validFrom;
    public static volatile SingularAttribute<ConfigStatus, Date> validTo;

}