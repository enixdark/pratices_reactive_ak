name := "SimpleAk"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.11"  % "2.4.1",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.1",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)