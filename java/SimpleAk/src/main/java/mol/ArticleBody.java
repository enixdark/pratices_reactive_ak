package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/13/16.
 */
public class ArticleBody implements Serializable{
    public final String url;
    public final String body;

    public ArticleBody(String url, String body){
        this.url = url;
        this.body = body;
    }
}
