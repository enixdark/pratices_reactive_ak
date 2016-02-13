package actor

import akka.actor.{ActorSelection, Actor}
import akka.actor.Actor.Receive
import akka.util.Timeout
import mol._
import akka.pattern._
import scala.concurrent.Future
import scala.util.Success
import mol.SetRequest._
import mol.Message._
/**
 * Created by cqshinn on 2/13/16.
 */
class ArticleParser(cacheActorPath: String, httpClientActorPath: String,
                     articleParserActorPath: String,implicit
                    val timeout: Timeout) extends Actor{
  import scala.concurrent.ExecutionContext.Implicits.global
  val cacheActor: ActorSelection = context.actorSelection(cacheActorPath)
  val httpClientActor: ActorSelection = context.actorSelection(httpClientActorPath)
  val articleParserActor: ActorSelection = context.actorSelection(articleParserActorPath)

  override def receive: Receive = {
    case ParseArticle(url) =>
      val cacheResult = cacheActor ? GetRequest(Some(url))
      val result = cacheResult.recoverWith {
        case _: Exception =>
          val fRawResult = httpClientActor ? url
          fRawResult.flatMap {
            case HttpResponse(rawArticle) => articleParserActor ? ParseHtmlArticle(url, rawArticle)
            case fail => Future.failed(new Exception("unknown response"))
          }
      }



      result onComplete {
        case Success(x: String) =>
          println("cached result!")
          sender ! x
        case scala.util.Success(x: ArticleBody) =>
          cacheActor ! SetRequest(url, Some(x.body))
          sender ! x
        case scala.util.Failure(t) =>
          sender ! akka.actor.Status.Failure(t)
        case x =>
          println("unknown message! " + x)
      }
  }
}
