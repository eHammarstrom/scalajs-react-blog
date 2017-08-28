enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin, WorkbenchPlugin)

name := "scalajs-react-blog"

version := "0.1"

scalaVersion := "2.12.3"

scalaJSUseMainModuleInitializer := true

val scalaJSReactVersion = "1.0.0"
val scalaCssVersion = "0.5.3"

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "core" % scalaJSReactVersion,
  "com.github.japgolly.scalajs-react" %%% "extra" % scalaJSReactVersion,
  "com.github.japgolly.scalacss" %%% "core" % scalaCssVersion,
  "com.github.japgolly.scalacss" %%% "ext-react" % scalaCssVersion)


npmDependencies in Compile ++= Seq(
  "react" -> "15.6.1",
  "react-dom" -> "15.6.1")

useYarn := true