package hw3.nnmodule

import hw3.ndarray.NDArray

/**
 * Sequence of submodules.
 *
 * @param submodules sequence of submodules. must not be empty
 */
class Sequential(val submodules: Array[NNModule]) extends NNModule {
  def apply(input: NDArray): NDArray = ???
}

object Sequential {
  def apply(submodules: NNModule*) = new Sequential(submodules.toArray)
}
