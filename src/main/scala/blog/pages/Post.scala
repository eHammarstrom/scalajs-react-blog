package blog.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, _}

object Post {
  val component = ScalaComponent
    .builder[blog.AppRouter.Post]("TodoList")
    .render_P(props => <.div(s"Blog post #${props.id}"))
    .build

  def apply(props: blog.AppRouter.Post) = component(props)
}
