package hw3.nnmodule

import hw3.matmulengine.{MatmulEngine, NaiveEngine}
import hw3.ndarray.{Matrix, NDArray, StackedArray}

/**
 * Linear transformation
 */
class Linear(val weight: Matrix) extends NNModule {
  protected val me: MatmulEngine = NaiveEngine

  def apply(input: NDArray): NDArray = {
    if (input.ndim == 1) {
      // TODO: return (weight * input) matrix x vector multiplication using `me`
      ???
    } else if (input.ndim == 2) {
      // TODO: return (weight * input) matrix x matrix multiplication using `me`
      ???
    } else {
      // Batched matmul
      val length = input.getShape(0)
      val mapped = (0 until length).map(i => this(input.getArr(i)))
      StackedArray.stack(mapped)
    }
  }
}

