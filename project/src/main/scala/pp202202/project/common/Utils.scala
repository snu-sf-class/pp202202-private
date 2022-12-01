package pp202202.project.common

object Utils {

  trait Show[-T] {
    def show(t: T): String
  }

  def show[A](a: A)(implicit sh: Show[A]): String = sh.show(a)

  def showIter[A](al: Iterable[A])(implicit sh: Show[A]): String =
    al.map(sh.show).mkString(" ")

  // NOTE: implementation of a single function trait can be reduced to
  //       single abstract method -> pattern matching anonymous function
  implicit val showArg: Show[Arg] = {
    case AVName(x) => x
    case ANName(x) => s"(by-name $x)"
  }

  implicit val showBind: Show[Bind] = {
    case BDef(f, params, body) =>
      s"(def $f (${showIter(params)}) ${show(body)})"
    case BVal(x, e) => s"(val $x ${show(e)})"
    case BLVal(x, e) => s"(lazy-val $x ${show(e)})"
  }

  implicit val showCCase: Show[CCase] = {
    case CCase(effect, x, handler) =>
      s"(case ${show(effect)} $x ${show(handler)})"
  }

  implicit val showExpr: Show[Expr] = {
    case EInt(n) => n.toString
    case EFloat(f) => f.toString
    case EString(s) => s"\"$s\""
    case EName(x) => x
    case ENil => "nil"
    case ECons(head, tail) =>
      s"(cons ${show(head)(showExpr)} ${show(tail)(showExpr)})"
    case EFst(e) => s"(fst ${show(e)(showExpr)})"
    case ESnd(e) => s"(snd ${show(e)(showExpr)})"
    case ENilP(e: Expr) => s"(nil? ${show(e)(showExpr)})"
    case EIntP(e: Expr) => s"(int? ${show(e)(showExpr)})"
    case EFloatP(e: Expr) => s"(float? ${show(e)(showExpr)})"
    case EStringP(e: Expr) => s"(string? ${show(e)(showExpr)})"
    case EPairP(e: Expr) => s"(pair? ${show(e)(showExpr)})"
    case ESubstr(e: Expr, start: Expr, end: Expr) =>
      s"(substr ${show(e)(showExpr)} ${show(start)(showExpr)} ${show(end)(showExpr)})"
    case ELen(e: Expr) => s"(len ${show(e)(showExpr)})"
    case EApp(f, args) =>
      s"(app ${show(f)(showExpr)} ${showIter(args)(showExpr)})"
    case ELet(bindings: List[Bind], e: Expr) =>
      s"(let (${bindings.map(b => show(b)(showBind)).mkString(" ")})\n${show(e)(showExpr)})"
    case EIf(cond, ifTrue, ifFalse) =>
      s"(if ${show(cond)(showExpr)}\n${show(ifTrue)(showExpr)}\n${show(ifFalse)(showExpr)})"
    case EAdd(left: Expr, right: Expr) =>
      s"(+ ${show(left)(showExpr)} ${show(right)(showExpr)})"
    case ESub(left: Expr, right: Expr) =>
      s"(- ${show(left)(showExpr)} ${show(right)(showExpr)})"
    case EMul(left: Expr, right: Expr) =>
      s"(* ${show(left)(showExpr)} ${show(right)(showExpr)})"
    case EDiv(left: Expr, right: Expr) =>
      s"(/ ${show(left)(showExpr)} ${show(right)(showExpr)})"
    case EMod(left: Expr, right: Expr) =>
      s"(% ${show(left)(showExpr)} ${show(right)(showExpr)})"
    case EEq(left: Expr, right: Expr) =>
      s"(= ${show(left)(showExpr)} ${show(right)(showExpr)})"
    case ELt(left: Expr, right: Expr) =>
      s"(< ${show(left)(showExpr)} ${show(right)(showExpr)})"
    case EGt(left: Expr, right: Expr) =>
      s"(> ${show(left)(showExpr)} ${show(right)(showExpr)})"
    case ETry(e, handlers) =>
      s"(try ${show(e)(showExpr)} ${showIter(handlers)})"
    case EEffect(effect, value) =>
      s"(effect ${show(effect)(showExpr)} ${show(value)(showExpr)})"
  }

  implicit val showImmVal: Show[Val] = {
    case VInt(n: Int) => n.toString
    case VFloat(f: Float) => f.toString
    case VString(s: String) => s
    case VNil => "nil"
    case VCons(head, tail) =>
      s"(${show(head)(showImmVal)} . ${show(tail)(showImmVal)})"
    case VFunc(fn, params, body, _) =>
      s"(def $fn (${showIter(params)}) ${show(body)})"
  }

  implicit def showLazyVal[V](implicit sv: Show[V]): Show[LazyVal[V]] = {
    case LVVal(v) => show(v)
    case l@LVLazy(_) => show(l.evaluated)
  }
}
