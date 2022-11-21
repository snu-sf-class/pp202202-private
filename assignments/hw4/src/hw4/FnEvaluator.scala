package hw4

import hw4.value.IValue

/**
 * Evaluator for a recursive function of 2 variables.
 * The variables and the values can be accessed by an abstract `IValue` type class.
 *
 * @param fnExp function body
 * @param iv instance of the type class `IValue`
 * @tparam V Value type
 */
class FnEvaluator[V](fnExp: Exp)(implicit iv: IValue[V]) {
  /**
   * Return the evaluated value of `fnExp(xVal, yVal)`
   *
   * @param xVal Value for `ValX`
   * @param yVal Value for `ValY`
   * @return evaluated value
   */
  def eval(xVal: V, yVal: V): V = _eval(xVal, yVal, fnExp)

  // overloaded eval
  def eval(): V = eval(iv.genInt(0), iv.genInt(0))
  def eval(x: Int, y: Int): V = eval(iv.genInt(x), iv.genInt(y))
  def eval(x: Int, y: Float): V = eval(iv.genInt(x), iv.genFloat(y))
  def eval(x: Float, y: Int): V = eval(iv.genFloat(x), iv.genInt(y))
  def eval(x: Float, y: Float): V = eval(iv.genFloat(x), iv.genFloat(y))

  /**
   * Helper function for `eval`
   *
   * @param xVal Value of `Exp.ValX`
   * @param yVal Value of `Exp.ValY`
   * @param e    Evaluation target
   * @return Evaluated result of e
   */
  private def _eval(xVal: V, yVal: V, e: Exp): V = {
    e match {
      // TODO: fill the every other case
      case EInt(n) => iv.genInt(n)
      case ValX => xVal
      case Add(l, r) => {
        // TODO: Fix this block to handle float values
        iv.matchInt(_eval(xVal, yVal, l))(i1 => iv.matchInt(_eval(xVal, yVal, r))(i2 => iv.genInt(i1 + i2)))
      }
      case CallThis(xExp, yExp) =>
        val xV = _eval(xVal, yVal, xExp)
        val yV = _eval(xVal, yVal, yExp)
        _eval(xV, yV, fnExp)
    }
  }
}
