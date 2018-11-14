import Dependencies._

// Uncomment when you're ready to start building 1.0.0-...-SNAPSHOT versions.
rallyVersioningSnapshotLowerBound in ThisBuild := "1.0.0"

lazy val commonSettings = Seq(
  organization := "api.schema",
  name := "api-schema",
  //version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.1",
  libraryDependencies ++= dependencies
)

lazy val root = (project in file(".")).
  settings(commonSettings).
  enablePlugins(ContrabandPlugin, JsonCodecPlugin, SemVerPlugin)
