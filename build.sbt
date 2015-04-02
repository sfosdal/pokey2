import ScoverageSbtPlugin._
import scalariform.formatter.preferences._

name := """pokey2"""

version := "2.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.7",
  "org.scalactic" %% "scalactic" % "2.2.4",
  "org.scaldi" %% "scaldi" % "0.5.3",
  "org.scaldi" %% "scaldi-akka" % "0.5.3",
  "org.scaldi" %% "scaldi-play" % "0.5.3",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.4" % "test",
  "org.scalatestplus" %% "play" % "1.2.0" % "test"
)

scalacOptions in (Compile, compile) ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",       // yes, this is 2 args
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",        // N.B. doesn't work well with the ??? hole
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
)

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignParameters, true)

//scalariformCompile = taskKey[Unit]("scalariformCompile")
//scalariformCompile := scalariformFormat.in(Compile).toTask("").value
//(compile in Compile) <<= (compile in Compile) dependsOn scalariformCompile

////////////////////////
// Test Configuration

javaOptions in Test += "-Dconfig.resource=test.conf"

ScoverageKeys.coverageMinimum := 90

ScoverageKeys.coverageFailOnMinimum := true

ScoverageKeys.coverageExcludedPackages := Seq(
  "<empty>",
  ".*\\.controller\\.javascript",
  ".*\\.controller\\.ref"
).mkString(";")
