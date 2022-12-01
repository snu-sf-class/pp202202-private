package pp202202.project.common

import org.parboiled2._

import scala.annotation.switch

class ExprParser(val input: ParserInput) extends Parser {
  def Input: Rule1[Expr] = rule {
    WL ~ SExpr ~ WL ~ EOI
  }

  def SExpr: Rule1[Expr] = rule {
    SELiteral | '(' ~ WL ~ SEParens ~ WL ~ ')'
  }

  def SExprList: Rule1[List[Expr]] = rule {
    (SExpr ~ (&(ch(')') | EOI) | SP)).* ~> ((el: Seq[Expr]) => el.toList)
  }

  def SELiteral: Rule1[Expr] = rule {
    (atomic("nil") ~ &(SP | ')') ~ push(ENil)) |
      (SEFloat ~> EFloat) |
      (SEInteger ~> EInt) |
      (SEString ~> EString) |
      (Ident ~> EName)
  }

  def SEParens: Rule1[Expr] = rule {
    run {
      (cursorChar: @switch) match {
        case 'c' => SECons
        case 'f' => SEFst | SEFloatP
        case 's' => SESnd | SEStringP | SESubstr
        case 'a' => SEApp
        case 'l' => SELet | SELen
        case 'n' => SENilP
        case 'p' => SEPairP
        case 'i' => SEIntP | SEIf
        case 't' => SETry
        case 'e' => SEEffect
        case 'l' => SELen
        case '+' | '-' | '*' | '=' | '<' | '>' | '/' | '%' => SEBinOp
        case _ => MISMATCH
      }
    }
  }

  def SECase: Rule1[CCase] = rule {
    '(' ~ WL ~ atomic("case") ~ SP ~ SExpr ~ SP ~ Ident ~ SP ~ SExpr ~ WL ~ ')' ~> CCase
  }

  def SETry: Rule1[ETry] = rule {
    atomic("try") ~ SP ~ SExpr ~ SP ~ (SECase ~ WL).* ~> ((e: Expr, cs: Seq[CCase]) => ETry(e, cs.toList))
  }

  def SEEffect: Rule1[EEffect] = rule {
    atomic("effect") ~ SP ~ SExpr ~ SP ~ SExpr ~> EEffect
  }

  def SECons: Rule1[ECons] = rule {
    atomic("cons") ~ SP ~ SExpr ~ SP ~ SExpr ~> ECons
  }

  def SEFst: Rule1[EFst] = rule {
    atomic("fst") ~ SP ~ SExpr ~> EFst
  }

  def SESnd: Rule1[ESnd] = rule {
    atomic("snd") ~ SP ~ SExpr ~> ESnd
  }

  def SEApp: Rule1[EApp] = rule {
    atomic("app") ~ SP ~ SExpr ~ WL ~ SExprList ~> EApp
  }

  def SELet: Rule1[ELet] = rule {
    atomic("let") ~ SP ~ SBindList ~ WL ~ SExpr ~> ELet
  }

  def SENilP: Rule1[ENilP] = rule {
    atomic("nil?") ~ SP ~ SExpr ~> ENilP
  }

  def SEIntP: Rule1[EIntP] = rule {
    atomic("int?") ~ SP ~ SExpr ~> EIntP
  }

  def SEFloatP: Rule1[EFloatP] = rule {
    atomic("float?") ~ SP ~ SExpr ~> EFloatP
  }

  def SEStringP: Rule1[EStringP] = rule {
    atomic("string?") ~ SP ~ SExpr ~> EStringP
  }

  def SEPairP: Rule1[EPairP] = rule {
    atomic("pair?") ~ SP ~ SExpr ~> EPairP
  }

  def SESubstr: Rule1[ESubstr] = rule {
    atomic("substr") ~ SP ~ SExpr ~ SP ~ SExpr ~ SP ~ SExpr ~> ESubstr
  }

  def SELen: Rule1[ELen] = rule {
    atomic("len") ~ SP ~ SExpr ~> ELen
  }

  def SEIf: Rule1[EIf] = rule {
    atomic("if") ~ SP ~ SExpr ~ SP ~ SExpr ~ SP ~ SExpr ~> EIf
  }

  def SEBinOp: Rule1[Expr] = rule {
    capture(anyOf("+-*/%=<>")) ~ SP ~
      SExpr ~ SP ~ SExpr ~> ((c: String, left: Expr, right: Expr) =>
      c match {
        case "+" => EAdd(left, right)
        case "-" => ESub(left, right)
        case "*" => EMul(left, right)
        case "/" => EDiv(left, right)
        case "%" => EMod(left, right)
        case "=" => EEq(left, right)
        case "<" => ELt(left, right)
        case _ => EGt(left, right)
      }
      )
  }

  def SArg: Rule1[Arg] = rule {
    (Ident ~> AVName) | ('(' ~ WL ~ "by-name" ~ SP ~ Ident ~ WL ~ ")" ~> ANName)
  }

  def SArgList: Rule1[List[Arg]] = rule {
    '(' ~ WL ~ zeroOrMore(SArg ~ (&(')') | SP)) ~ ')' ~> ((args: Seq[Arg]) => args.toList)
  }

  def SBind: Rule1[Bind] = rule {
    '(' ~ WL ~ (SBindDef | SBindVal | SBindLazyVal) ~ WL ~ ')'
  }

  def SBindList: Rule1[List[Bind]] = rule {
    zeroOrMore(SBind ~ WL) ~> ((args: Seq[Bind]) => args.toList)
  }

  def SBindDef: Rule1[BDef] = rule {
    atomic("def") ~ SP ~ Ident ~ SP ~ SArgList ~ SP ~ SExpr ~> (
      (f: String, params: List[Arg], body: Expr) => BDef(f, params, body)
      )
  }

  def SBindVal: Rule1[BVal] = rule {
    atomic("val") ~ SP ~ Ident ~ SP ~ SExpr ~> BVal
  }

  def SBindLazyVal: Rule1[BLVal] = rule {
    atomic("lazy-val") ~ SP ~ Ident ~ SP ~ SExpr ~> BLVal
  }

  def Ident: Rule1[String] = rule {
    !CharPredicate.Digit ~ capture((CharPredicate.AlphaNum | '_').+)
  }

  def ParIdent: Rule1[String] = rule {
    '(' ~ WL ~ Ident ~ WL ~ ')'
  }

  def SEInteger: Rule1[Int] = rule {
    capture(ch('-').? ~ CharPredicate.Digit.+) ~> ((s: String) => s.toInt)
  }

  def SEFloat: Rule1[Float] = rule {
    capture(ch('-').? ~ CharPredicate.Digit.+ ~ '.' ~ CharPredicate.Digit.*) ~> ((s: String) => s.toFloat)
  }

  def SEString: Rule1[String] = rule {
    '"' ~ capture(noneOf("\"").*) ~ '"'
  }

  def WL: Rule0 = rule {
    quiet(anyOf(" \t\r\n").*)
  }

  def SP: Rule0 = rule {
    anyOf(" \t\r\n").+
  }
}
