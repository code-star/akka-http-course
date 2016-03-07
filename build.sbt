name := "akka-http-hello-world"
organization := "CodeStar"
version := "1.0"
scalaVersion := "2.11.7"

libraryDependencies ++= {
  val akkaV = "2.4.2"
  val akkaStreamV = "2.0.3"
  Seq(
    //"com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamV,
    //"com.typesafe.akka" %% "akka-http-experimental" % akkaV,
    
    //test deps
    "com.typesafe.akka" %% "akka-http-testkit-experimental" % "2.4.2-RC3" % Test,
    "com.typesafe.akka" %% "akka-stream-testkit-experimental" % akkaStreamV % Test,
    "org.scalatest" %% "scalatest" % "2.2.5" % Test,
    "junit" % "junit" % "4.10" % Test
  )
}

fork in run := true
