scalacOptions in Test ++= Seq("-Yrangepos")
resolvers ++= Seq(
  "bintray-sbt-plugins" at "http://dl.bintray.com/sbt/sbt-plugin-releases/"
)

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers += Resolver.url(
  "Rally Plugin Releases",
  url("https://artifacts.werally.in/artifactory/ivy-plugins-release"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.rallyhealth" %% "rally-versioning" % "1.2.0")
addSbtPlugin("com.rallyhealth" %% "rally-sbt-plugin" % "0.5.0")
