package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ObjectStatusPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ObjectStatus.class)
public class ObjectStatus_ { 

    public static volatile SingularAttribute<ObjectStatus, ObjectStatusPK> objectStatusPK;
    public static volatile SingularAttribute<ObjectStatus, String> active;
    public static volatile SingularAttribute<ObjectStatus, Date> setTime;

}