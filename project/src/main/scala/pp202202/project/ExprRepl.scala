package pp202202.project

import org.parboiled2.{ErrorFormatter, ParseError}
import pp202202.project.common.Utils.Show
import pp202202.project.common.{Expr, ExprParser, Interpreter}

import scala.annotation.tailrec
import scala.util.{Failure, Success}

object ExprRepl {

  @tailrec
  def run[V]()(implicit sh: Show[V], interpreter: Interpreter[Expr, V]): Unit = {
    println("Input your program [Enter for exit]:")
    val input = scala.io.StdIn.readLine()
    if (input == "") {
      println("Goodbye!")
    } else {
      val parser = new ExprParser(input)
      val result = parser.Input.run()
      val message = result match {
        case Success(e) =>
          interpreter.interpret(e) match {
            case Success(v) => s"Result: ${sh.show(v)}"
            case Failure(e) => s"Error: ${e.getMessage}"
          }
        case Failure(e: ParseError) =>
          s"Parse Error: ${parser.formatError(e, new ErrorFormatter(showTraces = true))}"
      }
      println(message)

      run()
    }
  }
}
