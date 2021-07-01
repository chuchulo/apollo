name := "apollo"

version := "0.1"

scalaVersion := "2.13.6"


libraryDependencies ++= Seq(

  "org.http4s" %% "http4s-blaze-server" % "1.0.0-M4",
  "org.http4s" %% "http4s-blaze-client" % "1.0.0-M4",
  "org.http4s" %% "http4s-dsl"          % "1.0.0-M4",
  "org.http4s" %% "http4s-circe"        % "1.0.0-M4",
  "io.circe"   %% "circe-generic"       % "0.14.0-M4",
  "io.circe"   %% "circe-literal"       % "0.14.0-M4",
  "com.github.pureconfig" %% "pureconfig" % "0.14.1",
  "org.slf4j" % "slf4j-simple" % "1.7.26",
  "org.scalatest" %% "scalatest" % "3.2.2" % "test"
)
assemblyOutputPath in assembly := file("build/matejovic.jar")