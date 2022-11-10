package hw3.nnmodule

import hw3.ndarray.NDArray

/**
 * Base trait of the Neural Network modules.
 *
 * The contemporary Neural Net frameworks such as TensorFlow and PyTorch
 * contains `backward` function which calculates a gradient for training.
 *
 * Since we will not run any training, you should only implement the basic
 * calculation of each module.
 */
trait NNModule {
  /**
   * Run some calculation with the given N-dim array.
   * The calculation is differ to each child class.
   *
   * @param input input N-dim array
   * @return output M-dim array
   */
  def apply(input: NDArray): NDArray
}
