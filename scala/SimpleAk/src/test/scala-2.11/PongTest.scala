/**
 * Created by cqshinn on 2/12/16.
 */

import akka.actor.{ActorSystem, Actor}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{Matchers, FunSpecLike}
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.language.postfixOps

class PongTest extends FunSpecLike with Matchers {
  import actor.PongActor._
  import scala.concurrent.ExecutionContext.Implicits.global

  val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)
  val pong = system.actorOf(props)
  def askPong(message: String): Future[String] = (pong ? message).mapTo[String]
  val futureFuture: Future[Future[String]] = askPong("Ping").map(x => { askPong(x) })
  describe("test pong"){
    it("should be response with ping"){
      askPong("Pong").onSuccess({
        case result: String => println("replied with: " + result)
      })
      Thread.sleep(1000)
//      val result = Await.result(future.mapTo[String], 1 second)
//      assert( result == "Ping")
    }

    it("should fail when send a unknown message to actor"){
      val future = pong ? "_"
      intercept[Exception]{
        Await.result(future.mapTo[String], 1 second)
      }
      askPong("causeError 1").onFailure{
        case e: Exception => println("Got exception")
      }
      askPong("causeError 2").recoverWith({
        case ex: Exception => askPong("Pong")
      });
    }
  }

}
