import org.parboiled2.{ErrorFormatter, ParseError}
import org.scalatest.flatspec.AnyFlatSpec
import pp202202.project.common._
import pp202202.project.impl.BrainFX
import pp202202.project.impl.ExprInterpreter.exprInterpreter
import pp202202.project.impl.MapEnvImpl.mapEnvImpl
import pp202202.project.impl.LazyOpsImpl.lazyOpsImpl

import scala.annotation.tailrec
import scala.util.{Failure, Success}

class InterpreterTest extends AnyFlatSpec {
  def parse(str: String): Expr = {
    val parser = new ExprParser(str)
    val result = parser.Input.run()
    result match {
      case Success(v) => v
      case Failure(e: ParseError) =>
        throw new RuntimeException(
          parser.formatError(e, new ErrorFormatter(showTraces = true))
        )
    }
  }

  def exec(code: String): Val =
    lazyOpsImpl.evaluate(
      exprInterpreter[MapEnv[LazyVal[Val]], LazyVal[Val]]
        .interpret(parse(code)).get)

  def runAssert(code: String, expect: Val): Unit = assert(exec(code) == expect)

  "Basic let" should "return 3" in {
    val adder =
      """
        |(let (val x 1) (val y 2) (+ x y))
        |""".stripMargin

    runAssert(adder, VInt(3))
  }

  "Basic let float" should "return 7.5" in {
    val mul =
      """
        |(let (val x 3) (val y 2.5) (* x y))
        |""".stripMargin

    runAssert(mul, VFloat(7.5f))
  }

  "Basic let string" should "return 'Hello, world!'" in {
    val adder =
      """
        |(let (val x "Hello") (val y ", world!") (+ x y))
        |""".stripMargin

    runAssert(adder, VString("Hello, world!"))
  }

  "Basic def" should "return 1.0" in {
    val adder =
      """
        |(let (def f (x) (+ x -1)) (app f 2.0))
        |""".stripMargin

    runAssert(adder, VFloat(1.0f))
  }

  "Basic let*" should "return 11" in {
    val adder =
      """
        |(let (val x 3) (val y (+ x 5)) (+ x y))
        |""".stripMargin

    runAssert(adder, VInt(11))
  }

  "Basic def*" should "return 11" in {
    val adder =
      """
        |(let
        |  (val x 3)
        |  (def y () 5)
        |  (def f (y) (+ x y))
        |  (+ x (+ (app y) (app f 5)))
        |)
        |""".stripMargin

    runAssert(adder, VInt(16))
  }

  "Basic lazy val*" should "return 11" in {
    val adder =
      """
        |(let (val x 3) (lazy-val y (+ x 5)) (+ x y))
        |""".stripMargin

    runAssert(adder, VInt(11))
  }

  "First-order function" should "return 13" in {
    val adder =
      """
        |(let
        |  (def mul2 (n) (* n 2))
        |  (def g (f x) (+ (app f 4) x))
        |  (app g mul2 5))
        |""".stripMargin

    runAssert(adder, VInt(13))
  }

  "Basic pair type" should "return (4 . 1)" in {
    val code =
      """
        |(let (val x 13) (val y 3) (cons (/ x y) (% x y)))
        |""".stripMargin

    runAssert(code, VCons(VInt(4), VInt(1)))
  }

  "Basic comparison" should "return 3" in {
    val code1 = "(if (< 4 1) 0 3)"
    val code2 = "(if (> 4 1) 3 0)"
    val code3 = "(if (= 4 4) 3 0)"
    val code4 = "(if (= 4.0 4) 3 0)"
    val code5 = "(if (= 2 4.0) 0 3)"
    val code6 = """(if (= "str" "str") 3 0)"""
    val code7 = """(if (= "str" "str2") 0 3)"""
    val code8 = """(if (= "str" 1) 0 3)"""
    runAssert(code1, VInt(3))
    runAssert(code2, VInt(3))
    runAssert(code3, VInt(3))
    runAssert(code4, VInt(3))
    runAssert(code5, VInt(3))
    runAssert(code6, VInt(3))
    runAssert(code7, VInt(3))
    runAssert(code8, VInt(3))
  }

  "Basic recursion" should "return 15" in {
    val adder =
      """
        |(let
        |  (def sum (n)
        |       (if (> n 0)
        |           (+ (app sum (- n 1)) n)
        |           0))
        |  (app sum 5))""".stripMargin

    runAssert(adder, VInt(15))
  }

  "Fibonacci(10)" should "return 55" in {
    val fib =
      """(let
        |  (def fib (n)
        |       (if (> n 0)
        |           (if (> n 1)
        |               (+ (app fib (- n 1)) (app fib (- n 2)))
        |               1)
        |           0))
        |  (app fib 10))""".stripMargin

    runAssert(fib, VInt(55))
  }

  "andb(true, true)" should "return true" in {
    val andb =
      """(let
        |  (def andb (a b) (if a b 0))
        |  (val x (> 3 1))
        |  (val y (< 2 3))
        |  (app andb x y)
        |)
      """.stripMargin

    runAssert(andb, VInt(1))
  }

  "isdiv(15, 5)" should "return true" in {
    val isdiv =
      """(let
        |  (def isdiv (n p) (= (% n p) 0))
        |  (app isdiv 15 5)
        |)
      """.stripMargin

    runAssert(isdiv, VInt(1))
  }

  "predicates" should "work well" in {
    val checkPred =
      """(let
        |  (def notb (p) (if p 0 1))
        |  (def andb (a b) (if a b 0))
        |  (val nilp (app andb (nil? nil) (app notb (nil? (cons 3 nil)))))
        |  (val intp (app andb (int? 3) (app notb (int? 3.0))))
        |  (val floatp (app andb (float? 3.0) (app notb (float? 3))))
        |  (val stringp (app andb (string? "") (app notb (string? 3))))
        |  (val pairp (app andb (app andb (pair? nil) (pair? (cons 3 nil))) (app notb (pair? 3))))
        |  (app andb nilp (app andb intp (app andb floatp (app andb stringp pairp))))
        |)
        |""".stripMargin


    runAssert(checkPred, VInt(1))
  }

  "basic len" should "return 8" in {
    val isdiv =
      """(let
        |  (val slen (len "asdfe"))
        |  (val clen (len (cons 3 (cons "a" (cons 1.0 nil)))))
        |  (+ slen clen)
        |)
          """.stripMargin

    runAssert(isdiv, VInt(8))
  }

  "basic substr" should "return sting" in {
    val isdiv =
      """(let
        |  (def remove (s n) (+ (substr s 0 n) (substr s (+ n 1) (len s))))
        |  (val test "string")
        |  (app remove test 2)
        |)
            """.stripMargin

    runAssert(isdiv, VString("sting"))
  }

  "forall(isdiv, 5, [10; 5; 15])" should "return true" in {
    val isdiv =
      """(let
        |  (def isdiv (n p) (= (% n p) 0))
        |  (def andb (a b) (if a b 0))
        |  (let
        |    (def forall (f n lst)
        |         (if (nil? lst)
        |             1
        |             (app andb (app f (fst lst) n) (app forall f n (snd lst)))
        |         ))
        |    (app forall isdiv 5 (cons 10 nil))
        |  )
        |)
        """.stripMargin

    runAssert(isdiv, VInt(1))
  }


  "forall(isdiv, 5, [10; 5; 15; 31])" should "return false" in {
    val isdiv =
      """(let
        |  (def isdiv (n p) (= (% n p) 0))
        |  (def andb (a b) (if a b 0))
        |  (let
        |    (def forall (f n lst)
        |         (if (nil? lst)
        |             1
        |             (app andb (app f (fst lst) n) (app forall f n (snd lst)))
        |         ))
        |    (app forall isdiv 5 (cons 10 (cons 5 (cons 15 (cons 31 nil)))))
        |  )
        |)
      """.stripMargin

    runAssert(isdiv, VInt(0))
  }

  "(snoc [1; 2; 3; 4] 10)" should "return [1; 2; 3; 4; 10]" in {
    val snoc =
      """(let
        |    (def snoc (tl hd)
        |         (if (pair? tl)
        |             (cons (fst tl) (app snoc (snd tl) hd))
        |             (cons tl hd)
        |         )
        |    )
        |    (app snoc (cons 1 (cons 2 (cons 3 4))) 10)
        |)
      """.stripMargin
    runAssert(
      snoc,
      VCons(VInt(1), VCons(VInt(2), VCons(VInt(3), VCons(VInt(4), VInt(10)))))
    )
  }

  "by-name" should "return 11" in {
    runAssert(
      """
        |(let
        |  (lazy-val x (+ 3 5))
        |  (def f ((by-name a) (by-name b)) (+ a b))
        |  (app f x (+ 1 2)))
        |""".stripMargin,
      VInt(11)
    )
  }

  "lazy infinite loop" should "return" in {
    runAssert(
      """
        |(let
        |  (def loop () (app loop))
        |  (lazy-val x (app loop))
        |  (def f (a (by-name b)) a)
        |  (val bb (app f x (app loop)))
        |  3)
        |""".stripMargin,
      VInt(3)
    )
  }

  "tailrec" should "return n" in {
    val recrec =
      """
        |(let
        |  (def f (x sum fn)
        |       (if (> x 0)
        |           (app fn (- x 1) (+ x sum))
        |           sum
        |       ))
        |  (def f2 (x sum)
        |       (if (> x 0)
        |           (app f (- x 1) (+ x sum) f2)
        |           sum
        |       ))
        |  (app f 20000 0 f2))
        |""".stripMargin
    runAssert(recrec, VInt(200010000))
  }

  "basic handler" should "handle correctly" in {
    val handle =
      """
        |(let
        |  (val mul2 "mul2")
        |  (try
        |    (effect mul2 3)
        |    (case mul2 x (* x 2))
        |  )
        |)
        |""".stripMargin
    runAssert(handle, VInt(6))
  }

  "outer handler" should "handle correctly" in {
    val handle =
      """
        |(let
        |  (val muln "muln")
        |  (def callEff (f n) (effect f n))
        |  (+
        |    (try
        |      (app callEff muln 3)
        |      (case muln x (* x 2))
        |    )
        |    (try
        |      (app callEff muln 5)
        |      (case muln x (* x 3))
        |    )
        |  )
        |)
        |""".stripMargin
    runAssert(handle, VInt(21))
  }

  "multiple handler" should "handle correctly" in {
    val handle =
      """
        |(let
        |  (val mul2 "mul2")
        |  (val add10 "add10")
        |  (def callEff (eff x) (effect eff x))
        |  (try
        |    (let
        |       (def callMul2 (x) (effect mul2 x))
        |       (val x2 (app callEff add10 20))
        |       (val x3 (app callMul2 6))
        |       (+ x2 x3)
        |    )
        |    (case mul2 x (* x 2))
        |    (case add10 x (+ x 10))
        |  )
        |)
        |""".stripMargin
    runAssert(handle, VInt(42))
  }

  "multiple try" should "handle correctly" in {
    val handle =
      """
        |(let
        |  (val mul2 "mul2")
        |  (val add10 "add10")
        |  (try
        |    (let
        |       (val x10 (effect mul2 5))
        |       (try
        |         (let
        |           (val x15 (effect add10 5))
        |           (val x6 (effect mul2 3))
        |           (* (+ x6 x15) x10)
        |         )
        |         (case add10 x (+ x 10))
        |       )
        |    )
        |    (case mul2 x (* x 2))
        |  )
        |)
        |""".stripMargin
    runAssert(handle, VInt(210))
  }

  def inputsToList(inputs: Seq[Int]): String = {
    if (inputs.isEmpty)
      return "nil"
    inputs.map(n => s"(cons ${n} ").mkString("") + " nil" + (")" * inputs.length)
  }

  def outputStreamToStr(outputs: Val): List[Int] = {
    @tailrec
    def toStr(out: Val, lst: List[Int]): List[Int] = {
      out match {
        case VNil => lst
        case VCons(VInt(n), tl) => toStr(tl, n :: lst)
      }
    }
    toStr(outputs, Nil)
  }
  def execBFX(bfxInterpreter: String, code: String, inputs: Seq[Int]): List[Int] = {
    val itp = s"""(let
                 |  (val fx $bfxInterpreter)
                 |  (def getNth (l n)
                 |    (if (= n 0) (fst l) (app getNth (snd l) (- n 1))))
                 |  (val inputs ${inputsToList(inputs)})
                 |  (try
                 |    (app fx "$code")
                 |    (case "getInput" nth (app getNth inputs nth))
                 |  )
                 |)
                 |  """.stripMargin
    outputStreamToStr(exec(itp))
  }

  def execBFX(bfxInterpreter: String, code: String, inputs: String): String =
    execBFX(bfxInterpreter, code, inputs.toCharArray.map(_.toInt)).map(_.toChar).mkString

  ">>>>>" should "return nothing" in {
    execBFX(BrainFX.brainFXInterpreter, ">>>>>", "")
  }

  ",." should "echo" in {
    assert(execBFX(BrainFX.brainFXInterpreter, ",.", "A") == "A")
  }

  ",+." should "inc" in {
    assert(execBFX(BrainFX.brainFXInterpreter, ",+.", "A") == "B")
  }

  ",>,>,.<.<." should "reverse 3" in {
    assert(execBFX(BrainFX.brainFXInterpreter, ",>,>,.<.<.", "ABC") == "CBA")
  }

  "[->+<]" should "add left value to right" in {
    assert(execBFX(BrainFX.brainFXInterpreter, ",>,<[->+<].>.", Array(10, 20)) == List(0, 30))
  }

  "Hello World!" should "print correct word" in {
    val code = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++."
    assert(execBFX(BrainFX.brainFXInterpreter, code, "") == "Hello World!\n")
  }
}
