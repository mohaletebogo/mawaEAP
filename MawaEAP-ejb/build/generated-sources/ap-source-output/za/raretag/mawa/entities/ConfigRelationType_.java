package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.raretag.mawa.entities.PartnerRelation;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(ConfigRelationType.class)
public class ConfigRelationType_ { 

    public static volatile CollectionAttribute<ConfigRelationType, PartnerRelation> partnerRelationCollection;
    public static volatile SingularAttribute<ConfigRelationType, String> description;
    public static volatile SingularAttribute<ConfigRelationType, String> id;
    public static volatile SingularAttribute<ConfigRelationType, Date> validFrom;
    public static volatile SingularAttribute<ConfigRelationType, Date> validTo;

}