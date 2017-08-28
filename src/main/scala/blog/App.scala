package blog

import org.scalajs.dom.document

object App {

  def main(args: Array[String]): Unit = {
    println("Starting app...")

    val root = document.querySelector(".root")
    AppRouter.router().renderIntoDOM(root)
  }

}
