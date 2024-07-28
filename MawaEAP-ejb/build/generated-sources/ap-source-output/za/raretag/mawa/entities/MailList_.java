package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(MailList.class)
public class MailList_ { 

    public static volatile SingularAttribute<MailList, String> listId;
    public static volatile SingularAttribute<MailList, String> emailAddress;
    public static volatile SingularAttribute<MailList, Integer> id;
    public static volatile SingularAttribute<MailList, Date> validFrom;
    public static volatile SingularAttribute<MailList, Date> validTo;

}