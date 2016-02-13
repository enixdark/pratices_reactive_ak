package actor

import java.util.concurrent.TimeoutException

import akka.actor.Status.Failure
import akka.actor.{Actor, Props, ActorRef}
import akka.actor.Actor.Receive
import akka.util.Timeout
import mol._

/**
 * Created by cqshinn on 2/13/16.
 */
class TellArticleParser(cacheActorPath: String,
                        httpClientActorPath: String,
                        acticleParserActorPath: String,
                        implicit val timeout: Timeout) extends Actor{
  val cacheActor = context.actorSelection(cacheActorPath)
  val httpClientActor = context.actorSelection(httpClientActorPath)
  val articleParserActor = context.actorSelection(acticleParserActorPath)

  implicit val dispatcher = context.dispatcher

  override def receive: Receive = {
    case mes @ ParseArticle(url) => {
      val extraActor: ActorRef = buildExtraActor(sender(), url)
      cacheActor ! (GetRequest(Some(url)), extraActor)
      httpClientActor ! ("test", extraActor)
      context.system.scheduler.scheduleOnce(timeout.duration, extraActor, "timeout")
    }
  }

  private def buildExtraActor(actor: ActorRef, url: String): ActorRef = {
    return context.actorOf(Props(new Actor {
      override def receive: Actor.Receive = {
        case "timeout" =>
          actor ! Failure(new TimeoutException("timeout"))
          context.stop(self)
        case HttpResponse(body) =>
          articleParserActor ! ParseHtmlArticle(url, body)

        case body: String =>
          actor ! body
          context.stop(self)

        case ArticleBody(uri, body) =>
          cacheActor ! SetRequest(Some(url), Some(body))
          actor ! body
          context.stop(self)
        case t =>
          println("Any")
      }
    }));
  }
}
