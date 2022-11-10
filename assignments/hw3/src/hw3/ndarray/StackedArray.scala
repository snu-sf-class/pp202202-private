/**
 * You should fill every ??? and TODOs before the compilation
 */
package hw3.ndarray

/**
 * Implementation of the N-dimensional matrix with N >= 3.
 * The internal values are stored in row-major order.
 * https://en.wikipedia.org/wiki/Row-_and_column-major_order
 *
 * HINT: You should first implement Matrix and Vector.
 *
 * @param values values of this matrix (row-major)
 */
final class StackedArray(val values: Array[NDArray]) extends NDArray {
  val ndim: Int = ???

  val getShape: Array[Int] = ???

  def getArr(i: Int): NDArray = ???

  def get(i: Int): Float = ???

  def reshape(shape: Int*): NDArray = ???

  def reduceLeft[T](f: (T, T) => T): T = ???

  def unaryOp(f: Float => Float): NDArray = ???

  def binOp(f: (Float, Float) => Float, that: NDArray): NDArray = ???

  def equals(that: NDArray): Boolean = ???
}

object StackedArray extends NDArrayOps[StackedArray] {
  def apply(shape: Seq[Int], values: Seq[Float]): StackedArray = ???

  def fill(shape: Seq[Int], value: Float): StackedArray = ???

  def stack(values: Seq[NDArray]): StackedArray = ???
}