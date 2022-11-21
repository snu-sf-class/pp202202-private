package hw4.value

/**
 * Type class for the abstract value for the evaluator.
 * The abstract value can be either integer or float value.
 */
trait IValue[V] {
  /**
   * Generate an abstract value from the given literal integer
   */
  def genInt(n: Int): V

  /**
   * Generate an abstract value from the given literal float
   */
  def genFloat(f: Float): V


  /**
   * Matching function with handlers.
   * If the v is integer, call handleInt(v), otherwise, call handleFloat(v)
   *
   * @param v target value
   * @param handleInt integer handler
   * @param handleFloat float handler
   * @return result of the one of the handlers
   */
  def matchVal(v: V)(
    handleInt: Int => V,
    handleFloat: Float => V
  ): V

  def matchInt(v: V)(handleInt: Int => V): V = {
    matchVal(v)(handleInt, _ => throw new Exception("expected Int, got Float") )
  }

  def matchFloat(v: V)(handleFloat: Float => V): V = {
    matchVal(v)(_ => throw new Exception("expected Float, got Int"), handleFloat)
  }
}
