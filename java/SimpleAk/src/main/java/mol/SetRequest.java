package mol;

/**
 * Created by cqshinn on 2/11/16.
 */
public class SetRequest {
    private String key;
    private Object value;

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
