package blog.components

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Loading {

  object Style extends StyleSheet.Inline {
    import dsl._

    val loader = style(
      width(100 %%),
      height(100 vh),
      display.flex,
      justifyContent.center,
      alignItems.center
    )

    val image = style(
      fontSize(72.pt)
    )
  }

  val component = ScalaComponent
    .builder[Unit]("Loading")
    .renderStatic(
      <.div(
        Style.loader,
        <.img(
          Style.image,
          ^.src := "images/circles.svg"
        )
      )
    )
    .build

  def apply() = component()
}
