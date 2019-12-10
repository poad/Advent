import sbt.Keys.{libraryDependencies, scalaVersion}

scalaVersion := "2.13.1"

name := "hello-world"
organization := "com.github.poad"
version := "1.0"

val cdkVersion = "1.18.0"
val scalaTestVersion = "3.0.8"
libraryDependencies ++= Seq(
  "software.amazon.awscdk" % "core" % cdkVersion,
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)
