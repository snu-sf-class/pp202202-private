package hw4

import hw4.value.{Evaluated, ImmInt, ImmVal, LazyVal, ToFloat, BinaryVal}
import hw4.value.LazyValImpl._
import hw4.value.ImmValImpl._
import hw4.value.BinaryValImpl._


object Test extends App {
    def print_result(b: Boolean): Unit =
        if (b) println("O") else println("X")

    def toFloat[V](v: V)(implicit iv: ToFloat[V]): Float = iv.toFloat(v)

    val eval = new FnEvaluator[LazyVal](Add(EInt(3), EInt(4))).eval()
    val eval2 = new FnEvaluator[ImmVal](Sub(EInt(3), EInt(4))).eval()
    val eval3 = new FnEvaluator[BinaryVal](Add(EInt(3), Sub(EInt(4), EInt(5)))).eval()

    print_result(toFloat(eval) == 7)
    print_result(toFloat(eval2) == -1)
    print_result(toFloat(eval3) == 2)

    val eval4 = new FnEvaluator[LazyVal](Mul(ValX, ValY)).eval(5, 6)
    val eval5 = new FnEvaluator[ImmVal](Div(ValX, ValY)).eval(6.0f, 5.0f)
    val eval6 = new FnEvaluator[BinaryVal](Div(ValX, ValY)).eval(-6.0f, 5.0f)

    print_result(toFloat(eval4) == 30)
    print_result(toFloat(eval5) == 1.2f)
    print_result(toFloat(eval6) == -1.2f)

    /*
    Expression of the Ackermann function
    def)
      A(0, y) = y + 1
      A(x + 1, 0) = A(x, 1)
      A(x + 1, y + 1) = A(x, A(x + 1, y))

    =>
    ack(x, y) =
      IfZero(x,
        y + 1,
        IfZero(y,
          ack(x - 1, 1),
          ack(x - 1, ack(x, y - 1))
        )
      )
     */
    val ackermann =
        IfZero(ValX,
            Add(ValY, EInt(1)),
            IfZero(ValY,
                CallThis(Sub(ValX, EInt(1)), EInt(1)),
                CallThis(Sub(ValX, EInt(1)), CallThis(ValX, Sub(ValY, EInt(1)))),
            )
        )

    val return2Y = Mul(ValY, EInt(2))

    // super huge number but lazy!
    val lazyAck99 = new FnEvaluator[LazyVal](ackermann).eval(9, 9)

    // will not use X value
    val lazyVal2 = new FnEvaluator[LazyVal](return2Y).eval(lazyAck99, Evaluated(ImmInt(1)))

    print_result(toFloat(lazyVal2) == 2)

    // The below cannot be calculated in time because immAck99 needs very long time
    // val immAck99 = new FnEvaluator[ImmVal](ackermann).eval(9, 9)
    // val immVal2 = new FnEvaluator[ImmVal](return2Y).eval(immAck99, ImmInt(1))
    // print_result(toFloat(immVal2) == 2)
}
