package za.raretag.mawa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:37")
@StaticMetamodel(DirectDeposit.class)
public class DirectDeposit_ { 

    public static volatile SingularAttribute<DirectDeposit, String> checkingId;
    public static volatile SingularAttribute<DirectDeposit, Date> depositDate;
    public static volatile SingularAttribute<DirectDeposit, BigDecimal> amount;
    public static volatile SingularAttribute<DirectDeposit, Integer> id;
    public static volatile SingularAttribute<DirectDeposit, String> depositReference;

}