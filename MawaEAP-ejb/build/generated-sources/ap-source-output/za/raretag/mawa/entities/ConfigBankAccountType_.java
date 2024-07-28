package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.TransactionBank;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigBankAccountType.class)
public class ConfigBankAccountType_ { 

    public static volatile SingularAttribute<ConfigBankAccountType, String> description;
    public static volatile SingularAttribute<ConfigBankAccountType, String> id;
    public static volatile SingularAttribute<ConfigBankAccountType, Date> validFrom;
    public static volatile CollectionAttribute<ConfigBankAccountType, TransactionBank> transactionBankCollection;
    public static volatile SingularAttribute<ConfigBankAccountType, Date> validTo;

}