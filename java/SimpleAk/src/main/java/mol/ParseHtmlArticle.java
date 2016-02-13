package mol;

import java.io.Serializable;

/**
 * Created by cqshinn on 2/13/16.
 */
public class ParseHtmlArticle implements Serializable {
    public final String url;
    public final String htmlString;

    public ParseHtmlArticle(String url, String htmlString){
        this.url = url;
        this.htmlString = htmlString;
    }
}
