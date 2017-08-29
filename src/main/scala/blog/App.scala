package blog

import blog.css.AppCss
import org.scalajs.dom.document

import scala.scalajs.js.annotation.JSExport

@JSExport
object App {

  @JSExport
  def main(args: Array[String]): Unit = {
    println("Starting app...")

    val root = document.querySelector(".root")

    AppCss.load
    AppRouter.router().renderIntoDOM(root)
  }

}
