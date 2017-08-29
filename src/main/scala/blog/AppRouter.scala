package blog

import japgolly.scalajs.react.extra.router.{BaseUrl, Redirect, Resolution, Router, RouterConfigDsl, RouterCtl}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, _}

object AppRouter {
  sealed trait BlogPage

  // 404
  case object NotFound extends BlogPage

  // Post List
  case object PostList extends BlogPage

  // Post by ID
  case class Post(id: Int) extends BlogPage

  val notFoundPage = ScalaComponent
    .static("404")(<.h1("404 not found")).apply().vdomElement

  val config = RouterConfigDsl[BlogPage].buildConfig { dsl =>
    import dsl._

    (emptyRule
      | staticRoute("#/404", NotFound) ~> render(notFoundPage)
      | staticRoute(root, PostList) ~> render(pages.PostList.apply())
      | dynamicRouteCT("#/post" / int.caseClass[Post]) ~> dynRender(pages.Post(_))
      ).notFound(redirectToPage(NotFound)(Redirect.Replace))
      .renderWith(layout)
      .logToConsole
  }

  def layout(c: RouterCtl[BlogPage], r: Resolution[BlogPage]) =
    <.div(
      components.Header(components.Header.Props(r.page, c)),
      r.render()
    )

  val router = Router(BaseUrl.fromWindowOrigin / "index.html", config)
}
