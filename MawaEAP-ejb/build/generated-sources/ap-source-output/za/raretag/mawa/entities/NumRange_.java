package za.raretag.mawa.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(NumRange.class)
public class NumRange_ { 

    public static volatile SingularAttribute<NumRange, String> startNumber;
    public static volatile SingularAttribute<NumRange, Integer> numRangeId;
    public static volatile SingularAttribute<NumRange, String> endNumber;
    public static volatile SingularAttribute<NumRange, String> currentNumber;
    public static volatile SingularAttribute<NumRange, Date> validFrom;
    public static volatile SingularAttribute<NumRange, String> objectType;
    public static volatile SingularAttribute<NumRange, Date> validTo;

}