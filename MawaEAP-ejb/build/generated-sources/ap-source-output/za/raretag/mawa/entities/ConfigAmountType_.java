package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.TransactionAmount;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigAmountType.class)
public class ConfigAmountType_ { 

    public static volatile SingularAttribute<ConfigAmountType, String> description;
    public static volatile SingularAttribute<ConfigAmountType, String> id;
    public static volatile SingularAttribute<ConfigAmountType, Date> validFrom;
    public static volatile CollectionAttribute<ConfigAmountType, TransactionAmount> transactionAmountCollection;
    public static volatile SingularAttribute<ConfigAmountType, Date> validTo;

}