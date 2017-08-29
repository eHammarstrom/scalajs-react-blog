package blog.css

import scalacss.DevDefaults._
import scalacss.internal.mutable.GlobalRegistry
import blog.components.Header

object AppCss {

  def load = {
    GlobalRegistry.register(
      GlobalStyle,
      Header.Style
    )

    GlobalRegistry.onRegistration(_.addToDocument())
  }

}
