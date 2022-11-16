/**
 * You should fill every ??? and TODOs before the compilation
 */
package hw3.ndarray

/**
 * Implementation of mathematical matrix (i.e. 2-dim array).
 * The internal values are stored in row-major order.
 * https://en.wikipedia.org/wiki/Row-_and_column-major_order
 *
 * @param values values of this matrix (row-major)
 */
final class Matrix(val values: Array[Vector]) extends NDArray {
  val ndim: Int = 2

  val getShape: Array[Int] = Array(values.length, values(0).getShape(0))

  def getArr(i: Int): Vector = ???

  def get(i: Int): Float = ???

  def reshape(shape: Int*): NDArray = ???

  def reduceLeft(f: (Float, Float) => Float): NDArray = ???

  def unaryOp(f: Float => Float): NDArray = ???

  def binOp(f: (Float, Float) => Float, that: NDArray): NDArray = ???

  def equals(that: NDArray): Boolean = ???
}

object Matrix extends NDArrayOps[Matrix] {
  def apply(values: Seq[Vector]): Matrix = ???

  def apply(shape: Seq[Int], values: Seq[Float]): Matrix = ???

  def fill(shape: Seq[Int], value: Float): Matrix = ???

  def stack(values: Seq[NDArray]): Matrix = {
    // TODO: check the every value in the values is Vector and has same shape.

    new Matrix(values.asInstanceOf[Seq[Vector]].toArray)
  }
}