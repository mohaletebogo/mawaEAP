package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.TransactionDate;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(ConfigDateType.class)
public class ConfigDateType_ { 

    public static volatile CollectionAttribute<ConfigDateType, TransactionDate> transactionDateCollection;
    public static volatile SingularAttribute<ConfigDateType, String> description;
    public static volatile SingularAttribute<ConfigDateType, String> id;
    public static volatile SingularAttribute<ConfigDateType, Date> validFrom;
    public static volatile SingularAttribute<ConfigDateType, Date> validTo;

}