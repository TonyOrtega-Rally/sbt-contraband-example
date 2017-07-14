import Dependencies._

lazy val commonSettings = Seq(
  organization := "api.schema",
  name := "API Schema",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.1",
  libraryDependencies ++= dependencies
)

lazy val root = (project in file(".")).
  settings(commonSettings).
  enablePlugins(ContrabandPlugin, JsonCodecPlugin)
