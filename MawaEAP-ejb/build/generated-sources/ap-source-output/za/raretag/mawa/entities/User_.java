package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> partnerNo;
    public static volatile SingularAttribute<User, byte[]> password;
    public static volatile SingularAttribute<User, Date> validFrom;
    public static volatile SingularAttribute<User, byte[]> tempPassword;
    public static volatile SingularAttribute<User, String> userId;
    public static volatile SingularAttribute<User, String> status;
    public static volatile SingularAttribute<User, Date> validTo;

}