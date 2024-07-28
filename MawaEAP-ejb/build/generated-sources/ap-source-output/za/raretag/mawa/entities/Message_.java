package za.raretag.mawa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-01T13:21:36")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, String> messageType;
    public static volatile SingularAttribute<Message, String> messageBody;
    public static volatile SingularAttribute<Message, String> recipient;
    public static volatile SingularAttribute<Message, Integer> messageId;

}