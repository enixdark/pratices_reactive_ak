package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/13/16.
 */
public class Message implements Serializable{
    public final StringBuffer mutableBuffer;

    public Message(StringBuffer mutableBuffer){
        this.mutableBuffer = mutableBuffer;
    }
}
