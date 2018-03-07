name := "Matcher"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq("org.scalatest" % "scalatest_2.10" % "2.0" % "test",
   "com.typesafe.scala-logging"      %% "scala-logging"              % "3.7.2" withSources() withJavadoc())