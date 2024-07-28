package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigDocumentType;
import za.raretag.mawa.entities.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionDocument.class)
public class TransactionDocument_ { 

    public static volatile SingularAttribute<TransactionDocument, Date> createdAt;
    public static volatile SingularAttribute<TransactionDocument, String> createdBy;
    public static volatile SingularAttribute<TransactionDocument, ConfigDocumentType> documentType;
    public static volatile SingularAttribute<TransactionDocument, String> transactionNo;
    public static volatile SingularAttribute<TransactionDocument, String> location;
    public static volatile SingularAttribute<TransactionDocument, Transaction> transaction;

}