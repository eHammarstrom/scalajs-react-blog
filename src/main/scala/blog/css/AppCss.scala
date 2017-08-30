package blog.css

import scalacss.DevDefaults._
import scalacss.internal.mutable.GlobalRegistry
import blog.components.{Header, Loading}
import blog.pages.PostList

object AppCss {

  def load = {
    GlobalRegistry.register(
      GlobalStyle,
      Header.Style,
      Loading.Style,
      PostList.Style
    )

    GlobalRegistry.onRegistration(_.addToDocument())
  }

}
