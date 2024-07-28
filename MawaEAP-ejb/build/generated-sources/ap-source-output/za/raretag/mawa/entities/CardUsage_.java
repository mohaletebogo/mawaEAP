package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(CardUsage.class)
public class CardUsage_ { 

    public static volatile SingularAttribute<CardUsage, BigDecimal> amount;
    public static volatile SingularAttribute<CardUsage, Integer> cardUsageId;
    public static volatile SingularAttribute<CardUsage, String> supplier;
    public static volatile SingularAttribute<CardUsage, String> cardNo;
    public static volatile SingularAttribute<CardUsage, Date> swipeDate;

}