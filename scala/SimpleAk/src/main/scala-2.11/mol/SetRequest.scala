package mol

/**
 * Created by cqshinn on 2/11/16.
 */
case class SetRequest(val key: Option[String], val value: Option[String])
case class GetRequest(val key: Option[String]) extends Serializable
case class KeyNotFoundException(val key: Option[String]) extends Exception with Serializable

