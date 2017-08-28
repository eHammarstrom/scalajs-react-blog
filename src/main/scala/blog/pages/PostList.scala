package blog.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, _}

object PostList {
  val component = ScalaComponent
    .static("Post list")(<.div(s"This is the blog page!"))

  def apply() = component().vdomElement
}
