name := "SimpleAk"

version := "1.0"

scalaVersion := "2.11.7"



libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.11"  % "2.4.1",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.1",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "com.typesafe.akka" %% "akka-remote" % "2.4.1",
//  "com.syncthemail" % "boilerpipe" % "1.2.2",
  "org.scalaz" % "scalaz-scalacheck-binding_2.11" % "7.2.0",
//  "org.scalaz" % "scalaz-core_2.11" % "7.2.0",
//  "com.twitter" % "finagle-core_2.11" % "6.33.0",
//  "com.twitter" % "finagle-http_2.11" % "6.33.0",
//  "com.twitter" % "finagle-thrift_2.11" % "6.33.0"
  "com.typesafe.akka" % "akka-http-experimental_2.11" % "2.0.3",
  "com.typesafe.akka" % "akka-http-core-experimental_2.11" % "2.0.3"
//  "com.typesafe.akka" % "akka-stream-experimental_2.11" % "2.0.3",
//  "com.typesafe.akka" % "akka-cluster_2.11" % "2.4.1",
//  "com.typesafe.akka" % "akka-agent_2.11" % "2.4.1",
//  "org.reactivestreams" % "reactive-streams" % "1.0.0.final"
  //"org.scala-lang.modules" %% "scala-java8-compat_2.11" % "0.7.0"//,
//  "com.google.guava" %% "guava" % "19.0"
)

resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
//resolvers += "central" at "http://repo1.maven.org/maven2/"
mappings in (Compile, packageBin) ~= { _.filterNot {
  case (_, name) => Seq("application.conf").contains(name)
}}