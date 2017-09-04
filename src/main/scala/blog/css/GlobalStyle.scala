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

  val button = style(

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
