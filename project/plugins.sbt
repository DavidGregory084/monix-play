resolvers += Resolver.url(
  "hmrc-sbt-plugin-releases",
  url("https://dl.bintray.com/hmrc/sbt-plugin-releases")
)(Resolver.ivyStylePatterns)

resolvers += Resolver.bintrayRepo("hmrc", "releases")

addSbtPlugin("com.geirsson"              % "sbt-ci-release"             % "1.5.5")
addSbtPlugin("org.scalameta"             % "sbt-scalafmt"               % "2.4.2")
addSbtPlugin("com.timushev.sbt"          % "sbt-updates"                % "0.5.1")
addSbtPlugin("com.typesafe"              % "sbt-mima-plugin"            % "0.8.1")
addSbtPlugin("io.chrisdavenport"         % "sbt-mima-version-check"     % "0.1.2")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"               % "0.1.16")
addSbtPlugin("uk.gov.hmrc"               % "sbt-play-cross-compilation" % "1.2.0")
