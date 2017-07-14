scalacOptions in Test ++= Seq("-Yrangepos")
resolvers ++= Seq(
  "bintray-sbt-plugins" at "http://dl.bintray.com/sbt/sbt-plugin-releases/"
)
