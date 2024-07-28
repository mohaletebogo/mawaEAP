package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigBankAccountType;
import za.raretag.mawa.entities.ConfigBankAccountUsage;
import za.raretag.mawa.entities.ConfigBankName;
import za.raretag.mawa.entities.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(TransactionBank.class)
public class TransactionBank_ { 

    public static volatile SingularAttribute<TransactionBank, String> branchCode;
    public static volatile SingularAttribute<TransactionBank, String> accountHolder;
    public static volatile SingularAttribute<TransactionBank, String> accountIdNumber;
    public static volatile SingularAttribute<TransactionBank, ConfigBankAccountType> accountType;
    public static volatile SingularAttribute<TransactionBank, String> accountNo;
    public static volatile SingularAttribute<TransactionBank, Transaction> transactionNo;
    public static volatile SingularAttribute<TransactionBank, Integer> transactionBankId;
    public static volatile SingularAttribute<TransactionBank, String> branchName;
    public static volatile SingularAttribute<TransactionBank, ConfigBankName> bankName;
    public static volatile SingularAttribute<TransactionBank, Date> validFrom;
    public static volatile SingularAttribute<TransactionBank, Date> validTo;
    public static volatile SingularAttribute<TransactionBank, ConfigBankAccountUsage> usageType;

}