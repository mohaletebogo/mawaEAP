package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.TransactionPartner;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigPartnerFunction.class)
public class ConfigPartnerFunction_ { 

    public static volatile CollectionAttribute<ConfigPartnerFunction, TransactionPartner> transactionPartnerCollection;
    public static volatile SingularAttribute<ConfigPartnerFunction, String> description;
    public static volatile SingularAttribute<ConfigPartnerFunction, String> id;
    public static volatile SingularAttribute<ConfigPartnerFunction, Date> validFrom;
    public static volatile SingularAttribute<ConfigPartnerFunction, Date> validTo;

}