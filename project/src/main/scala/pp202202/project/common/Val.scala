package pp202202.project.common

sealed trait Val

case class VInt(n: Int) extends Val

case class VFloat(f: Float) extends Val

case class VString(s: String) extends Val

case object VNil extends Val

case class VCons(head: Val, tail: Val) extends Val

// function as first class value
case class VFunc[Env](funcName: String, params: List[Arg], body: Expr, env: Env) extends Val



