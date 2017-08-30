package blog.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, _}
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.Protocol.HTTP
import monix.execution.Scheduler.Implicits.global
import play.api.libs.json._
import blog.Model
import blog.components.{Header, Loading}
import japgolly.scalajs.react.Callback

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._
import scala.concurrent.Future

object PostList {

  object Style extends StyleSheet.Inline {
    import dsl._

    val content = style(
      marginTop(Header._height.px),
      display.flex,
      justifyContent.center
    )
  }

  case class State(data: List[Model.Post], isLoading: Boolean)

  class Backend(t: BackendScope[Unit, State]) {
    def initialize(): Callback = Callback.future {
      for {
        ls <- retrievePosts()
      } yield {
        t.setState(State(ls, isLoading = false))
      }
    }

    def render(S: State) =
      <.div(Style.content,

        if (S.isLoading) {
          Loading()
        } else if (S.data.isEmpty) { // TODO: Display a friendly error
          <.h1("Error: no posts")
        } else {
          <.div(
            S.data.map(post =>
              <.div(
                <.h1(post.title),
                <.p(post.description)
              )
            ).toTagMod
          )
        }

      )
  }

  def retrievePosts(): Future[List[Model.Post]] = {
    HttpRequest()
      .withProtocol(HTTP)
      .withHost("localhost")
      .withPort(3000)
      .withPath("/api/blog/posts")
      .send()
      .map(res => Json.parse(res.body))
      .map(json => Json.fromJson[List[Model.Post]](json))
      .map({
        case s: JsSuccess[List[Model.Post]] => s.value
        case _ => List.empty
      })
  }

  val component = ScalaComponent
    .builder[Unit]("Post list")
    .initialState(State(Nil, isLoading = true))
    .renderBackend[Backend]
    .componentWillMount(c => c.backend.initialize())
    .build

  def apply() = component()
}
