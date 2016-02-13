package client

import java.util.concurrent.CompletionStage

import akka.actor.ActorSystem
import akka.util.Timeout
import mol.{GetRequest, SetRequest}

import scala.language.postfixOps
import scala.concurrent.duration._
import akka.pattern._

/**
 * Created by cqshinn on 2/13/16.
 */
class SClient(val address: String) {
  private implicit val timeout: Timeout = Timeout(2 seconds)
  private implicit val system = ActorSystem("local")
  private val remote = system.actorSelection(s"akka.tcp://akkademy@$address/user/akkadb")

  def set(key: String, value: String) = {
    remote ? SetRequest(Some(key),Some(value))
  }

  def get(key: String) = {
    remote ? GetRequest(Some(key))
  }
}
