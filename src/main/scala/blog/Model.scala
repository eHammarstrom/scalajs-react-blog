package blog

import play.api.libs.json.{Json, Reads}

object Model {
  case class Post(id: Int,
                  tags: List[String],
                  title: String,
                  description: String,
                  body: String,
                  createdAt: String,
                  updatedAt: String
                 )

  implicit val readsPost: Reads[Post] = Json.reads[Post]
}
