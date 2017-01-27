name := """microservice-template-play"""

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
resolvers ++= Seq(
  "Eigenroute maven repo" at "http://mavenrepo.eigenroute.com/",
  "SpinGo OSS" at "http://spingo-oss.s3.amazonaws.com/repositories/releases"
)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.eigenroute" % "eigenroute-scalikejdbc-helpers_2.11" % "0.0.1",
  "com.eigenroute" % "eigenroute-authenticated-action_2.11" % "0.0.1",
  "com.eigenroute" % "eigenroute-publish-subscribe-rabbitmq-play_2.11" % "0.0.5",
  "com.eigenroute" % "eigenroute-messagebroker-messages_2.11" % "0.0.4",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "org.flywaydb" %% "flyway-play" % "3.0.1",
  "org.postgresql" % "postgresql" % "9.4.1208.jre7",
  "org.scalikejdbc" %% "scalikejdbc"             % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-config"      % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-test"        % "2.4.2"   % Test,
  "com.eigenroute" % "eigenroute-scalikejdbc-test-helpers_2.11" % "0.0.1" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test,
  "com.typesafe.akka" %% "akka-testkit" % "2.4.16" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

