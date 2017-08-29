package blog.css


import scalacss.DevDefaults._

object GlobalStyle extends StyleSheet.Inline {
  import dsl._

  style(
    unsafeRoot("body")(
      margin.`0`,
      padding.`0`,
      fontSize(16.px),
      fontFamily := "Roboto, sans-serif"
    )
  )
}
