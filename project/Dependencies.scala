import sbt._

object Dependencies {
  lazy val dependencies = {
    val ScalaJsonVersion = "0.8.0-M2"
    val jodaTimeVersion = "2.9.9"
    val Specs2Version = "2.4.17"

    Seq(
      "com.eed3si9n"        %%  "sjson-new-scalajson" % ScalaJsonVersion,
      "joda-time"           %   "joda-time"           % jodaTimeVersion,
      "org.specs2"          %%  "specs2-core"         % Specs2Version     % "test",
      "org.specs2"          %%  "specs2-mock"         % Specs2Version     % "test",
      "org.specs2"          %%  "specs2-junit"        % Specs2Version     % "test"
    )
  }
}