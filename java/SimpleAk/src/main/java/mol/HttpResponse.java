package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/13/16.
 */
public class HttpResponse implements Serializable {
    public final String body;

    public HttpResponse(String body){
        this.body = body;
    }
}
