package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigFileLocation.class)
public class ConfigFileLocation_ { 

    public static volatile SingularAttribute<ConfigFileLocation, String> locationName;
    public static volatile SingularAttribute<ConfigFileLocation, String> filePath;
    public static volatile SingularAttribute<ConfigFileLocation, Date> validFrom;
    public static volatile SingularAttribute<ConfigFileLocation, Date> validTo;

}