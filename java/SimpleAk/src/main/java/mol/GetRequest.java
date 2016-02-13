package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/12/16.
 */
public class GetRequest implements Serializable {
    public final String key;
    public GetRequest(String key){
        this.key = key;
    }
}
