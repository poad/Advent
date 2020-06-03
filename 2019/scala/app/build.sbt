import sbt.Keys.{libraryDependencies, scalaVersion}

scalaVersion := "2.13.2"

name := "hello-world"
organization := "com.github.poad"
version := "1.0"

val cdkVersion = "1.42.1"
val scalaTestVersion = "3.1.2"
libraryDependencies ++= Seq(
  "software.amazon.awscdk" % "core" % cdkVersion,
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)
