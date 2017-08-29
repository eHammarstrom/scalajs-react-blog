package blog.pages

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^.{<, _}
import fr.hmil.roshttp.HttpRequest
import fr.hmil.roshttp.Protocol.HTTP
import monix.execution.Scheduler.Implicits.global

import play.api.libs.json._
import blog.Model

import scala.concurrent.Future

object PostList {

  case class State(data: List[Model.Post], failed: Boolean)

  class Backend(t: BackendScope[Unit, State]) {
    def initialize(S: State): Callback =
      t.modState(S => S.copy(S.data, S.failed))

    def render(S: State) =
      if (S.data.isEmpty) {
        <.div("No posts")
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
  }

  def retrievePosts(): Future[List[Model.Post]] = {
    HttpRequest()
      .withProtocol(HTTP)
      .withHost("localhost")
      .withPort(3000)
      .withPath("/api/blog/posts")
      .send()
      .map(s => s.body)
      .map(s => Json.fromJson[List[Model.Post]](Json.parse(s)))
      .map({
        case s: JsSuccess[List[Model.Post]] => s.value
        case _ => List.empty
      })
  }

  val component = ScalaComponent
    .builder[Unit]("Post list")
    .initialState(State(Nil, failed = false))
    .renderBackend[Backend]
    .componentWillMount { c =>
      retrievePosts().value match {
        case Some(posts) => c.backend.initialize(State(posts.get, failed = false))
        case _ => c.backend.initialize(State(Nil, failed = true))
      }
    }
    .build

  def apply() = component()
}
