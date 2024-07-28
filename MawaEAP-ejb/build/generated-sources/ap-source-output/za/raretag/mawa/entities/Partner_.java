package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.CheckOut;
import za.raretag.mawa.entities.PartnerAddress;
import za.raretag.mawa.entities.PartnerAttribute;
import za.raretag.mawa.entities.PartnerContact;
import za.raretag.mawa.entities.PartnerIdentity;
import za.raretag.mawa.entities.PartnerRelation;
import za.raretag.mawa.entities.PartnerRole;
import za.raretag.mawa.entities.TransactionPartner;
import za.raretag.mawa.entities.TransactionPayment;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(Partner.class)
public class Partner_ { 

    public static volatile CollectionAttribute<Partner, PartnerRelation> partnerRelationCollection1;
    public static volatile CollectionAttribute<Partner, CheckOut> checkOutCollection;
    public static volatile SingularAttribute<Partner, Date> validFrom;
    public static volatile CollectionAttribute<Partner, TransactionPayment> transactionPaymentCollection;
    public static volatile CollectionAttribute<Partner, CheckOut> checkOutCollection1;
    public static volatile SingularAttribute<Partner, String> type;
    public static volatile CollectionAttribute<Partner, PartnerAddress> partnerAddressCollection;
    public static volatile SingularAttribute<Partner, String> partnerNo;
    public static volatile SingularAttribute<Partner, String> name4;
    public static volatile CollectionAttribute<Partner, TransactionPayment> transactionPaymentCollection1;
    public static volatile CollectionAttribute<Partner, PartnerAttribute> partnerAttributeCollection;
    public static volatile SingularAttribute<Partner, String> name3;
    public static volatile CollectionAttribute<Partner, PartnerRelation> partnerRelationCollection;
    public static volatile SingularAttribute<Partner, String> createdBy;
    public static volatile CollectionAttribute<Partner, TransactionPartner> transactionPartnerCollection;
    public static volatile CollectionAttribute<Partner, PartnerRole> partnerRoleCollection;
    public static volatile CollectionAttribute<Partner, PartnerIdentity> partnerIdentityCollection;
    public static volatile SingularAttribute<Partner, String> name2;
    public static volatile SingularAttribute<Partner, String> name1;
    public static volatile CollectionAttribute<Partner, PartnerContact> partnerContactCollection;
    public static volatile SingularAttribute<Partner, Date> validTo;

}