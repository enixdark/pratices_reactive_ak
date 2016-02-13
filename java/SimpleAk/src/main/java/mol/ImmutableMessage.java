package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/13/16.
 */
public class ImmutableMessage implements Serializable {
    public final String immutableType;
    public ImmutableMessage(String immutableType) {
        this.immutableType = immutableType;
    }
}

