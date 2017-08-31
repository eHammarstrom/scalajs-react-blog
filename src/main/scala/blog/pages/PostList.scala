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
import japgolly.scalajs.react.extra.router.Router

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._
import scala.concurrent.Future

object PostList {

  object Style extends StyleSheet.Inline {
    import dsl._

    val content = style(
      media.screen.maxWidth(768.px)(
        width(100 %%)
      ),
      margin(Header._height.px, auto),
      width(768.px),
      display.flex,
      flexDirection.column
    )

    val post = style(
      position.relative,
      boxShadow := "0 3px 6px rgba(0,0,0,0.16), 0 3px 6px rgba(0,0,0,0.23)",
      padding(20.px),
      width(100 %%),
      marginBottom(20.px),
    )

    val readButton = style(
      position.absolute,
      right(10.px),
      bottom(10.px),
      paddingLeft(5.px),
      paddingRight(5.px),
      textDecoration := "none",
      borderLeft(1.px, solid, mediumaquamarine),
      borderRight(1.px, solid, mediumaquamarine),
      color.mediumaquamarine,
      backgroundColor.white,
      fontSize(16.pt),
      fontWeight.lighter,
      cursor.pointer,
      transition := "0.1s ease-in",
      &.hover(
        backgroundColor.mediumaquamarine,
        color.white,
        transition := "0.06s ease-in"
      )
    )
  }

  case class State(data: List[Model.LightPost], isLoading: Boolean)

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
          S.data.map(post =>
            <.div(Style.post,
              <.h1(Post.Style.postTitle, post.title),
              <.p(post.description),
              <.a(Style.readButton, "Read", ^.href := s"#/post/${post.id}")
            )
          ).toTagMod
        }

      )
  }

  def retrievePosts(): Future[List[Model.LightPost]] = {
    HttpRequest()
      .withProtocol(HTTP)
      .withHost("localhost")
      .withPort(3000)
      .withPath("/api/blog/posts")
      .send()
      .map(res => Json.parse(res.body))
      .map(json => Json.fromJson[List[Model.LightPost]](json))
      .map({
        case s: JsSuccess[List[Model.LightPost]] => s.value
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
