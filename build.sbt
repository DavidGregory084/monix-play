val scala211Version = "2.11.12"
val scala212Version = "2.12.12"
val scala213Version = "2.13.4"

lazy val scalaVersions = List(scala211Version, scala212Version, scala213Version)

lazy val commonSettings = Def.settings(
  scalaVersion := PlayCrossCompilation.playScalaVersion(scalaVersions)(scala213Version),
  crossScalaVersions := PlayCrossCompilation.playCrossScalaBuilds(scalaVersions),
  libraryDependencies += "org.scalameta" %% "munit" % "0.7.19" % Test,
  testFrameworks += new TestFramework("munit.Framework")
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(PlayCrossCompilation.playCrossCompilationSettings)
  .settings(libraryDependencies ++= compileDependencies ++ testDependencies)

lazy val compileDependencies = PlayCrossCompilation.dependencies(
  shared = Seq(
    "io.monix" %% "monix-eval" % "3.3.0",
  ),
  play26 = Seq(
    "com.typesafe.play" %% "play" % "2.6.25",
  ),
  play27 = Seq(
    "com.typesafe.play" %% "play" % "2.7.8",
  )
)

lazy val testDependencies = PlayCrossCompilation.dependencies(
  shared = Seq(

  ),
  play26 = Seq(
    "com.typesafe.play" %% "play-test" % "2.6.25",
  ),
  play27 = Seq(
    "com.typesafe.play" %% "play-test" % "2.7.8",
  )
).map(_ % Test)
