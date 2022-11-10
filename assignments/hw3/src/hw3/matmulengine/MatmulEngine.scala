package hw3.matmulengine

import hw3.ndarray.Matrix

/**
 * Matrix multiplication engine.
 */
trait MatmulEngine {
  /**
   * Matrix multiplication.
   *
   * If the second dimension of the left matrix is not equal to
   * the first dimension of the right matrix, raise `NNException`
   *
   * @param left N x K matrix
   * @param right K x M matrix
   * @return K x M matrix
   */
  def matmul(left: Matrix, right: Matrix): Matrix
}
