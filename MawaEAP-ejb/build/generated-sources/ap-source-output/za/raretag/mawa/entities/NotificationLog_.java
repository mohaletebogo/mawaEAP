package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(NotificationLog.class)
public class NotificationLog_ { 

    public static volatile SingularAttribute<NotificationLog, String> result;
    public static volatile SingularAttribute<NotificationLog, Date> executionTime;
    public static volatile SingularAttribute<NotificationLog, String> action;
    public static volatile SingularAttribute<NotificationLog, Integer> logId;
    public static volatile SingularAttribute<NotificationLog, Integer> notificationId;

}