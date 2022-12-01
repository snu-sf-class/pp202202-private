package pp202202.project.common

sealed trait Arg

case class AVName(x: String) extends Arg // call by value

case class ANName(x: String) extends Arg // call by name

sealed trait Bind

case class BDef(f: String, params: List[Arg], body: Expr) extends Bind

case class BVal(x: String, e: Expr) extends Bind

case class BLVal(x: String, e: Expr) extends Bind

case class CCase(effect: Expr, x: String, handler: Expr)

sealed trait Expr

case class EInt(n: Int) extends Expr

case class EFloat(f: Float) extends Expr

case class EString(s: String) extends Expr

case class EName(x: String) extends Expr

case object ENil extends Expr

case class ECons(head: Expr, tail: Expr) extends Expr

case class EFst(e: Expr) extends Expr

case class ESnd(e: Expr) extends Expr

case class ENilP(e: Expr) extends Expr

case class EIntP(e: Expr) extends Expr

case class EFloatP(e: Expr) extends Expr

case class EStringP(e: Expr) extends Expr

case class EPairP(e: Expr) extends Expr

case class ESubstr(e: Expr, start: Expr, end: Expr) extends Expr
case class ELen(e: Expr) extends Expr

case class EIf(cond: Expr, ifTrue: Expr, ifFalse: Expr) extends Expr

case class ELet(bs: List[Bind], e: Expr) extends Expr

case class EApp(f: Expr, args: List[Expr]) extends Expr

case class EAdd(left: Expr, right: Expr) extends Expr

case class ESub(left: Expr, right: Expr) extends Expr

case class EMul(left: Expr, right: Expr) extends Expr

case class EDiv(left: Expr, right: Expr) extends Expr

case class EMod(left: Expr, right: Expr) extends Expr

case class EEq(left: Expr, right: Expr) extends Expr

case class ELt(left: Expr, right: Expr) extends Expr

case class EGt(left: Expr, right: Expr) extends Expr

case class ETry(e: Expr, handlers: List[CCase]) extends Expr

case class EEffect(effect: Expr, value: Expr) extends Expr
