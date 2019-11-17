import sbt.Keys.{libraryDependencies, scalaVersion}

scalaVersion := "2.13.1"

name := "hello-world"
organization := "com.github.poad"
version := "1.0"

val cdkVersion = "1.16.3.DEVPREVIEW"
libraryDependencies ++= Seq(
  "software.amazon.awscdk" % "core" % cdkVersion,
  "software.amazon.awscdk" % "iam" % cdkVersion,
  "software.amazon.awscdk" % "s3" % cdkVersion,
  "software.amazon.awscdk" % "sns" % cdkVersion,
  "software.amazon.awscdk" % "sns-subscriptions" % cdkVersion,
  "software.amazon.awscdk" % "sqs" % cdkVersion,
)
