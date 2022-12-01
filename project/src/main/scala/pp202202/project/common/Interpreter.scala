package pp202202.project.common

import scala.util.Try

trait Interpreter[E, V] {
  def interpret(expr: E): Try[V]
}
