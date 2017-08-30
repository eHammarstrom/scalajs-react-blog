package blog.components

import blog.AppRouter.BlogPage

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.Reusability
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._

object Header {
  val _height = 63

  object Style extends StyleSheet.Inline {
    import dsl._

    val content = style(
      display.flex,
      justifyContent.center,
      alignItems.center,
      textAlign.center,
      fontSize(24.pt),
      height(_height.px),
      backgroundColor.mediumaquamarine,
      color.azure
    )
  }

  case class Props(selectedPage: BlogPage, ctrl: RouterCtl[BlogPage])

  implicit val currentPageReuse = Reusability.by_==[BlogPage]
  implicit val propsReuse = Reusability.by((_: Props).selectedPage)

  val component = ScalaComponent.builder[Props]("Header")
    .render_P { P =>
      <.div(Style.content, s"scalajs-blog/${P.selectedPage}")
    }
    .configure(Reusability.shouldComponentUpdate)
    .build

  def apply(props: Props) = component(props)
}
