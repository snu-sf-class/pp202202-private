/**
 * You should fill every ??? and TODOs before the compilation
 */
package hw3.ndarray

/**
 * Implementation of mathematical vector (i.e. 1-dim array).
 *
 * @param values values of this array
 */
final class Vector(val values: Array[Float]) extends NDArray {
  val ndim: Int = 1

  val getShape: Array[Int] = Array(values.length)

  def getArr(i: Int): NDArray = throw new NNException("getArr cannot be applied to a Vector.")

  def get(i: Int): Float = ???

  def reshape(shape: Int*): NDArray = ???

  def reduceLeft[T](f: (T, T) => T): T = ???

  def unaryOp(f: Float => Float): NDArray = ???

  def binOp(f: (Float, Float) => Float, that: NDArray): NDArray = ???

  def equals(that: NDArray): Boolean = ???
}

object Vector extends NDArrayOps[Vector] {
  def apply(values: Seq[Float]): Vector = new Vector(values.toArray)

  def apply(shape: Seq[Int], values: Seq[Float]): Vector = {
    if (shape.length != 1) {
      throw new NNException("the length of the given shape is not 1.")
    } else if (shape.head != values.length) {
      throw new NNException("the given shape does not equal to the number of values.")
    } else {
      new Vector(values.toArray)
    }
  }

  def fill(shape: Seq[Int], value: Float): Vector = {
    if (shape.length != 1) {
      throw new NNException("the length of the given shape is not 1.")
    } else {
      new Vector(Array.fill(shape.head)(value))
    }
  }

  def stack(values: Seq[NDArray]): Vector =
    throw new NNException("There is not an implementation of 0-dim array.")
}