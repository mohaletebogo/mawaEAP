package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.ConfigProductCategory;
import za.raretag.mawa.entities.Product;
import za.raretag.mawa.entities.Transaction;
import za.raretag.mawa.entities.TransactionItemPricing;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(TransactionItem.class)
public class TransactionItem_ { 

    public static volatile SingularAttribute<TransactionItem, BigDecimal> unitPrice;
    public static volatile CollectionAttribute<TransactionItem, TransactionItemPricing> transactionItemPricingCollection;
    public static volatile SingularAttribute<TransactionItem, Integer> itemId;
    public static volatile SingularAttribute<TransactionItem, Integer> quantity;
    public static volatile SingularAttribute<TransactionItem, Product> productId;
    public static volatile SingularAttribute<TransactionItem, ConfigProductCategory> itemCategory;
    public static volatile SingularAttribute<TransactionItem, Transaction> transactionNo;
    public static volatile SingularAttribute<TransactionItem, Date> validFrom;
    public static volatile SingularAttribute<TransactionItem, Date> validTo;

}