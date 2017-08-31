package blog.pages

import blog.Model
import blog.Model.FullPost
import blog.components.{Header, Loading}
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.Protocol.HTTP
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, _}
import play.api.libs.json.{JsError, JsSuccess, Json}
import monix.execution.Scheduler.Implicits.global

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._
import scala.concurrent.Future

object Post {

  object Style extends StyleSheet.Inline {
    import dsl._

    val content = style(
      marginTop(Header._height.px),
      display.flex,
      justifyContent.center
    )

    val post = style(
      media.screen.maxWidth(768.px)(
        width(100 %%)
      ),
      width(768.px),
      padding(10.px),
      border(2.px, solid, mediumaquamarine)
    )

    val postTitle = style(
      textAlign.center
    )

    val postDescription = style(
      fontSize.medium,
      fontWeight._700,
      textAlign.center
    )

    val postBody = style(
      textIndent(10.px)
    )
  }

  case class State(post: FullPost, isLoading: Boolean)

    class Backend(t: BackendScope[blog.AppRouter.Post, State]) {
    def initialize(P: blog.AppRouter.Post): Callback = Callback.future {
      for {
        p <- retrievePost(P.id)
      } yield {
        p match {
          case Some(p_) => t.setState(State(p_, isLoading = false))
          case None => t.setState(State(null, isLoading = false))
        }
      }
    }

    def render(S: State) =
      <.div(Style.content,

        if (S.isLoading) {
          Loading()
        } else if (S.post == null) { // TODO: Route to 404
          <.h1("Error: no posts")
        } else {
          <.div(Style.post,
            <.h1(Style.postTitle, S.post.title),
            <.p(Style.postDescription, S.post.description),
            <.p(Style.postBody, S.post.body)
          )
        }

      )
  }

  def retrievePost(id: Int): Future[Option[FullPost]] = {
    HttpRequest()
      .withProtocol(HTTP)
      .withHost("localhost")
      .withPort(3000)
      .withPath(s"/api/blog/posts/$id")
      .send()
      .map(res => Json.parse(res.body))
      .map(json => Json.fromJson[Model.FullPost](json))
      .map({
        case s: JsSuccess[Model.FullPost] => Some(s.value)
        case e: JsError => println(e); None // 404?
      })
  }

  val component = ScalaComponent
    .builder[blog.AppRouter.Post]("TodoList")
    .initialState(State(null, isLoading = true))
    .renderBackend[Backend]
    .componentWillMount(c => c.backend.initialize(c.props))
    .build

  def apply(props: blog.AppRouter.Post) = component(props)
}
