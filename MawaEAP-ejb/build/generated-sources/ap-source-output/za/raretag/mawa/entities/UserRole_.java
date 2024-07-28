package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.UserRolePK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(UserRole.class)
public class UserRole_ { 

    public static volatile SingularAttribute<UserRole, UserRolePK> userRolePK;
    public static volatile SingularAttribute<UserRole, Date> validFrom;
    public static volatile SingularAttribute<UserRole, Date> validTo;

}