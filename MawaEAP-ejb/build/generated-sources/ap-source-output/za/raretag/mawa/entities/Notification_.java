package za.raretag.mawa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(Notification.class)
public class Notification_ { 

    public static volatile SingularAttribute<Notification, String> subject;
    public static volatile SingularAttribute<Notification, String> recipient;
    public static volatile SingularAttribute<Notification, Integer> id;
    public static volatile SingularAttribute<Notification, String> type;
    public static volatile SingularAttribute<Notification, String> body;
    public static volatile SingularAttribute<Notification, String> status;

}