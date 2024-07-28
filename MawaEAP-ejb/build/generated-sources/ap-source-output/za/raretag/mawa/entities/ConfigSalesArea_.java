package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.CheckOut;
import za.raretag.mawa.entities.TransactionLocation;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigSalesArea.class)
public class ConfigSalesArea_ { 

    public static volatile CollectionAttribute<ConfigSalesArea, TransactionLocation> transactionLocationCollection;
    public static volatile SingularAttribute<ConfigSalesArea, String> description;
    public static volatile CollectionAttribute<ConfigSalesArea, CheckOut> checkOutCollection;
    public static volatile SingularAttribute<ConfigSalesArea, String> id;
    public static volatile SingularAttribute<ConfigSalesArea, Date> validFrom;
    public static volatile SingularAttribute<ConfigSalesArea, Date> validTo;

}