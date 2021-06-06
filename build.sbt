name := "wordcount"

version := "0.1"

scalaVersion := "2.13.6"

val akkaVersion = "2.6.14"

libraryDependencies ++= Seq(
"com.typesafe.akka" %% "akka-stream" % akkaVersion
)
