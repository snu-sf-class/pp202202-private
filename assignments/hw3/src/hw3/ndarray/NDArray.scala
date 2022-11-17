/**
 * Interface of an N-Dimensional array and its companion object.
 *
 * You should fill every ??? before the compilation
 */
package hw3.ndarray

/**
 * Raise this exception when a calculation cannot be executed.
 */
class NNException(val reason: String) extends Exception

/**
 * Interface of the N-Dimensional array. (Row-major)
 */
trait NDArray {
  /**
   * Number of dimensions of this N-dimensional array. (returns N)
   */
  val ndim: Int

  /**
   * Shape of this N-dimensional array.
   *
   * e.g.) Let `a` is `[[0,1,2,3],[4,5,6,7]]`, then `a.getShape == Array(2, 3)`.
   */
  val getShape: Array[Int]

  /**
   * Get the i-th element along the first axis.
   *
   * If the shape of an array `a` is (3, 4, 5), then `a.getArr(2).getShape == Array(4, 5)`.
   * If the given `i` is not in the range of the first axis (e.g. `a.getArr(3)`),
   * it should raise an `NNException`.
   *
   * @param i index along the first axis (starting from 0)
   * @return i-th NDArray
   */
  def getArr(i: Int): NDArray

  /**
   * Get the i-th value of this 1-dimensional array (i.e. only applied to a Vector).
   * If this array is not a Vector, raise an `NNException`.
   *
   * @param i index (starting from 0)
   * @return i-th value
   */
  def get(i: Int): Float

  /**
   * Reshape this array by the given shape.
   *
   * e.g.) Matrix(((2, 3, 5), (3, 4, 7))).reshape(6).equals(Vector(2, 3, 5, 3, 4, 7)) == true
   *
   * NOTE 1: You can refer to the behavior of the NumPy `reshape` function.
   * https://www.w3schools.com/python/numpy/numpy_array_reshape.asp
   * For simplification, you don't have to implement `-1` dimension.
   *
   * NOTE 2: `Int*` type means that we can pass variable number of parameters.
   * You can treat an `Int*` value like `Seq[Int]` type.
   * e.g.) `arr.reshape(3, 5, 10)` is a valid expression.
   *
   * @param shape target shape
   * @return reshaped
   */
  def reshape(shape: Int*): NDArray

  /**
   * Apply an element-wise binary operator to all elements along the first axis.
   * Note that f can be non-associative.
   *
   * e.g) [[2, 3, 5], [3, 1, 0]].reduceLeft(_ + _) = [5, 4, 5]
   *  [[1, 2, 3]].reduceLeft(_ + _) = [1, 2, 3]
   *  [2, 3, 4].reduceLeft(_ - _) = ((2 - 3) - 4) = -5
   *
   * @param f binary operator
   * @tparam T type of the inner element: NDArray (if this is Matrix or StackedArray) or Float (if this is Vector)
   * @return see Seq.reduceLeft
   */
  def reduceLeft[T](f: (Float, Float) => Float): T

  /**
   * Element-wise unary operation.
   *
   * @param f unary operator (e.g. neg, exp)
   * @return the result of the element-wise operation
   */
  def unaryOp(f: Float => Float): NDArray

  /**
   * Element-wise binary operation.
   * If the `ndim` of this and that are not same, then raise an `NNException`.
   *
   * @param f binary operator (e.g. add, mul)
   * @param that NDArray with a same size
   * @return the result of the element-wise operation
   */
  def binOp(f: (Float, Float) => Float, that: NDArray): NDArray


  /**
   * Element-wise addition.
   *
   * @param that NDArray with a same size
   * @return the result of the element-wise addition
   */
  def add(that: NDArray): NDArray = ???

  /**
   * Element-wise multiplication.
   *
   * @param that NDArray with a same size
   * @return the result of the element-wise multiplication
   */
  def mul(that: NDArray): NDArray = ???

  /**
   * Check the two arrays have the same values.
   *
   * @param that NDArray to compare
   * @return `true` if the two arrays have the same values
   */
  def equals(that: NDArray): Boolean
}


trait NDArrayOps[+T <: NDArray] {
  /**
   * Return new array with the given shape.
   * If the number of value in `values` does not match to the given `shape`,
   * raise `NNException`
   *
   * @param shape shape of the result array
   * @param values values to fill
   * @return new array
   */
  def apply(shape: Seq[Int], values: Seq[Float]): T

  /**
   * Return new array with the given shape.
   * If the number of value in `values` does not match to the given `shape`,
   * raise `NNException`
   *
   * @param shape  shape of the result array
   * @param value values to fill
   * @return new array
   */
  def fill(shape: Seq[Int], value: Float): T

  /**
   * Stack the arrays with a one dimension lower along the first axis.
   * If there is an array in the values with a different shape,
   * then raise `NNException`.
   *
   * @param values sequence of the same-shaped (N-1)-dim arrays.
   * @return new array
   */
  def stack(values: Seq[NDArray]): T
}

