package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/11/16.
 */
public class SetRequest implements Serializable {
    private final String key;
    private final Object value;

    public SetRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
