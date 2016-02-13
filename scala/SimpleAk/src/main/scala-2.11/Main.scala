import java.util.logging.Logger

import actor.{PongActor, AkkaDb}
import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import akka.event.{Logging, LoggingAdapter}
import mol.SetRequest

import scala.collection.immutable.HashMap

/**
 * Created by cqshinn on 2/11/16.
 */
object Main {

  def main (args: Array[String]){
    import PongActor._
    implicit val args: String = "Default"
    val actor = ActorSystem.create("akkademy");
//    val ref: ActorRef = actor.actorOf(Props[AkkaDb])
//    val ref: ActorRef = actor.actorOf(props)
//
////    ref ! SetRequest(Some("hello"),Some("world"))
//    ref ! "Pong"
//    ref ! "Pong"
//    actor.shutdown()

    val ref = actor.actorOf(Props[AkkaDb], name = "akkadb")
    println(ref.path)
  }
}
