package blog.components

import scalacss.Defaults._
import scalacss.ScalaCssReact._

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Header {

  object Style extends StyleSheet.Inline {
    import dsl._

    val header = style(
      textAlign.center,
      fontSize(24.pt),
      height(63.px),
      backgroundColor.mediumaquamarine,
      color.azure
    )
  }

  val component = ScalaComponent.builder
    .static("Header")(<.div(Style.header, "Super BLOG"))
    .build

  def apply() = component()
}
