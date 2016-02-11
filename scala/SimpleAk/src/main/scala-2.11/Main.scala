import java.util.logging.Logger

import actor.AkkaDb
import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import akka.event.{Logging, LoggingAdapter}
import mol.SetRequest

import scala.collection.immutable.HashMap

/**
 * Created by cqshinn on 2/11/16.
 */
object Main {

  def main (args: Array[String]){
    val actor = ActorSystem.create();
    val ref: ActorRef = actor.actorOf(Props[AkkaDb])
    ref ! SetRequest(Some("hello"),Some("world"))

  }
}
