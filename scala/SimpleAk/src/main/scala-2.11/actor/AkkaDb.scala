package actor

import java.util

import akka.actor.{Status, Actor}
import akka.event.{Logging, LoggingAdapter}
import mol.{KeyNotFoundException, GetRequest, SetRequest}


/**
 * Created by cqshinn on 2/11/16.
 */
class AkkaDb extends Actor{
  val log: LoggingAdapter = Logging(context.system, this)
  val map: util.Map[Option[String],Option[String]] = new util.HashMap[Option[String],Option[String]]

  override def receive = {
    case SetRequest(key,value) => {
      log.info("Received set request – key: {}\n value: {}\n", key, value)
      map.put(key,value)
    }
    case GetRequest(key) => {
      log.info("Received set request – key: {}\n", key)
      map.get(key) match {
        case Some(x) => sender ! x
        case None => sender ! Status.Failure(KeyNotFoundException(key))
      }
    }
    case obj => sender ! Status.Failure(new ClassNotFoundException("not found"))
  }
}
