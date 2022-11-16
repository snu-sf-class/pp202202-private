package hw3.nnmodule

import hw3.ndarray.{Matrix, NDArray, StackedArray}

/**
 * Softmax
 *
 * Return e^(x_i) / sum(e^(x_t))
 */
object Softmax extends NNModule {
  def apply(input: NDArray): NDArray = {
    if (input.ndim == 1) {
      val max = input.reduceLeft[Float]((a, b) => if (a > b) a else b)
      val exp = input.unaryOp(f => Math.exp(f - max).toFloat)
      val sum = exp.reduceLeft[Float](_ + _)
      exp.unaryOp(f => f / sum)
    } else if (input.ndim == 2) {
      val length = input.getShape(0)
      val mapped = (0 until length).map(i => this(input.getArr(i)))
      Matrix.stack(mapped)
    } else {
      val length = input.getShape(0)
      val mapped = (0 until length).map(i => this(input.getArr(i)))
      StackedArray.stack(mapped)
    }
  }
}
