package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/13/16.
 */
public class ParseArticle implements Serializable{
    public final String url;
    public ParseArticle(String url){
        this.url = url;
    }
}
