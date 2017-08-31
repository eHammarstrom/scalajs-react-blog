package blog

import play.api.libs.json.{Json, Reads}

object Model {
  case class LightPost(id: Int,
                       tags: List[String],
                       title: String,
                       description: String,
                       createdAt: String)

  implicit val readsLightPost: Reads[LightPost] = Json.reads[LightPost]

  case class FullPost(id: Int,
                      tags: List[String],
                      title: String,
                      description: String,
                      body: String,
                      createdAt: String,
                      updatedAt: String)

  implicit val readsFullPost: Reads[FullPost] = Json.reads[FullPost]
}
