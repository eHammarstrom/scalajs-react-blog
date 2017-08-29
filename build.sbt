enablePlugins(ScalaJSPlugin, WorkbenchPlugin)

name := "scalajs-react-blog"

version := "0.1"

scalaVersion := "2.12.3"

scalaJSUseMainModuleInitializer := true

val scalaJSReactVersion = "1.0.0"
val scalaCssVersion = "0.5.3"

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "extra" % scalaJSReactVersion,
  "com.github.japgolly.scalajs-react" %%% "core" % scalaJSReactVersion,
  "com.github.japgolly.scalacss" %%% "core" % scalaCssVersion,
  "com.github.japgolly.scalacss" %%% "ext-react" % scalaCssVersion)

val reactJSVersion = "15.6.1"

jsDependencies ++= Seq(
  "org.webjars.npm" % "react" % reactJSVersion
    /             "react-with-addons.js"
    minified      "react-with-addons.min.js"
    commonJSName  "React",
  "org.webjars.npm" % "react-dom" % reactJSVersion
    /             "react-dom.js"
    minified      "react-dom.min.js"
    dependsOn     "react-with-addons.js"
    commonJSName  "ReactDOM",
  "org.webjars.npm" % "react" % reactJSVersion
    /             "react-dom-server.js"
    minified      "react-dom-server.min.js"
    dependsOn     "react-dom.js"
    commonJSName  "ReactDOMServer")

refreshBrowsers <<= refreshBrowsers.triggeredBy(fastOptJS in Compile)
