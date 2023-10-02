ThisBuild / version := "1.0"
ThisBuild / scalaVersion := "2.12.16"
ThisBuild / organization := "org.example"

val spinalVersion = "1.9.3"
val spinalCore = "com.github.spinalhdl" %% "spinalhdl-core" % spinalVersion
val spinalLib = "com.github.spinalhdl" %% "spinalhdl-lib" % spinalVersion
val spinalIdslPlugin = compilerPlugin("com.github.spinalhdl" %% "spinalhdl-idsl-plugin" % spinalVersion)
val scalactic = "org.scalactic" %% "scalactic" % "3.2.16"
val scalatest = "org.scalatest" %% "scalatest" % "3.2.16" % "test"

lazy val projectname = (project in file("."))
  .settings(
    Compile / scalaSource := baseDirectory.value / "hw" / "spinal",
    libraryDependencies ++= Seq(spinalIdslPlugin),
    libraryDependencies ++= Seq(spinalCore, spinalLib, scalactic, scalatest).map(_.withSources())
  )

fork := true