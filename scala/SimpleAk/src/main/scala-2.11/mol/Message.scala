package mol

/**
 * Created by cqshinn on 2/13/16.
 */
case class Message(var multableBuffer: StringBuffer = new StringBuffer())
case class ImmutableMessage(val immutableType: String)
case class ParseArticle(url: String)
case class ParseHtmlArticle(url: String, htmlString: String)
case class HttpResponse(body: String)
case class ArticleBody(url: String, body: String)