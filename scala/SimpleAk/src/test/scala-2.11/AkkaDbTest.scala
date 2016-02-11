import actor.AkkaDb
import akka.actor.{Props, Actor, ActorSystem}
import akka.testkit.TestActorRef
import mol.SetRequest
import org.scalatest.{BeforeAndAfterEach, Matchers, FunSpecLike}

/**
 * Created by cqshinn on 2/11/16.
 */
class AkkaDbTest extends FunSpecLike with Matchers with BeforeAndAfterEach{
  implicit val system = ActorSystem()
  describe("AkkaDb"){
    describe("test with actor"){
      val actorRef = TestActorRef(new AkkaDb)
      actorRef ! SetRequest(Some("hello"),Some("world"))
      val A = actorRef.underlyingActor
      A.map.get("hello") should equal(Some("world"))
    }
  }
}
