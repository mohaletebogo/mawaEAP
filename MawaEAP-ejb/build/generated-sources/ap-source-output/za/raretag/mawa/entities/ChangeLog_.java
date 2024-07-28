package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ChangeLog.class)
public class ChangeLog_ { 

    public static volatile SingularAttribute<ChangeLog, String> newValue;
    public static volatile SingularAttribute<ChangeLog, Date> changeAt;
    public static volatile SingularAttribute<ChangeLog, String> changedBy;
    public static volatile SingularAttribute<ChangeLog, String> changeType;
    public static volatile SingularAttribute<ChangeLog, String> objectName;
    public static volatile SingularAttribute<ChangeLog, Integer> changeId;
    public static volatile SingularAttribute<ChangeLog, String> oldValue;
    public static volatile SingularAttribute<ChangeLog, String> objectId;

}