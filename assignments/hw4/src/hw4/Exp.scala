package hw4

sealed trait Exp

/** Integer literal */
case class EInt(n: Int) extends Exp

/** Float literal */
case class EFloat(f: Float) extends Exp

/**
 * X value. Correspond to `xVal` of `FnEvaluator.eval(xVal, ...)`.
 */
case object ValX extends Exp

/**
 * Y value. Correspond to `yVal` of `FnEvaluator.eval(..., yVal`.
 */
case object ValY extends Exp

// Binary operators
// Evaluation order of the operands: left to right
// If one of the operand is evaluated to float value, the result should be float.
// Otherwise (i.e. two operands are int), the result should be int.

/** Addition */
case class Add(left: Exp, right: Exp) extends Exp

/** Subtraction */
case class Sub(left: Exp, right: Exp) extends Exp

/** Multiplication */
case class Mul(left: Exp, right: Exp) extends Exp

/** Division. If the `right` value is 0, throw any Exception. */
case class Div(left: Exp, right: Exp) extends Exp

/**
 * If `cond` is evaluated to 0 or 0.0, return the result of `left` expression.
 * If not, return the result of `right` expression.
 *
 * @param cond condition
 * @param left if cond == 0
 * @param right if cond != 0
 */
case class IfZero(cond: Exp, left: Exp, right: Exp) extends Exp

/**
 * Call the recursive function with the new operands.
 *
 * @param xExp new `xVal` value for `eval(xVal, ...)`
 * @param yExp new `yVal` value for `eval(..., yVal)`
 */
case class CallThis(xExp: Exp, yExp: Exp) extends Exp