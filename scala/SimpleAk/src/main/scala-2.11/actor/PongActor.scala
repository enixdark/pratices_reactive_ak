package actor

import akka.actor.{Props, Status, Actor}

import scala.util.control.Exception

/**
 * Created by cqshinn on 2/12/16.
 */

object PongActor{
  implicit val args: String = "Default"
  def props(implicit response: String): Props ={
    return Props(classOf[PongActor],response)
  }
}
class PongActor(implicit name: String) extends Actor{
  override def receive = {
    case "Pong" => {
      println(s"Ping $name")
      sender ! s"Ping $name"
    }
    case _ => sender ! Status.Failure(new Exception("unknown message"))
  }
}


