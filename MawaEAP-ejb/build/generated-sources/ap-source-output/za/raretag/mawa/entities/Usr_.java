package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(Usr.class)
public class Usr_ { 

    public static volatile SingularAttribute<Usr, String> partnerNo;
    public static volatile SingularAttribute<Usr, byte[]> password;
    public static volatile SingularAttribute<Usr, Date> validFrom;
    public static volatile SingularAttribute<Usr, byte[]> tempPassword;
    public static volatile SingularAttribute<Usr, String> userId;
    public static volatile SingularAttribute<Usr, String> status;
    public static volatile SingularAttribute<Usr, Date> validTo;

}