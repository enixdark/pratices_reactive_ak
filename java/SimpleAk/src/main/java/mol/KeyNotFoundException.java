package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/12/16.
 */
public class KeyNotFoundException extends Exception implements Serializable {
    public final String key;
    public KeyNotFoundException(String key){
        this.key = key;
    }
}
