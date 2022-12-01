package pp202202.project.impl

import pp202202.project.common._

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object ExprInterpreter {
  implicit def exprInterpreter[Env, V](
     implicit envOps: EnvOps[Env, V],
              lazyOps: LazyOps[Val, V]
   ): Interpreter[Expr, V] = new Interpreter[Expr, V] {

    private def eval(env: Env, expr: Expr): V =
      expr match {
        // TODO: implement the other cases
        case EApp(f, args) =>
          lazyOps.evaluate(eval(env, f)) match {
            case fv@VFunc(fn, params, body, fEnv: Env) => ???
              // TODO: Call f with args
            case _ => throw new Exception("EApp type error")
          }
        case EAdd(left, right) =>
          val lv = lazyOps.evaluate(eval(env, left))
          val rv = lazyOps.evaluate(eval(env, right))
          (lv, rv) match {
            case (VInt(ln), VInt(rn)) => lazyOps.to(VInt(ln + rn))
            // TODO: implement the other types
            case _ => throw new Exception("EAdd type error")
          }
      }

    def interpret(expr: Expr): Try[V] = {
      try {
        Success(eval(envOps.emptyEnv(), expr))
      } catch {
        case e: Exception => Failure(e)
      }
    }
  }
}
