package hw3.nnmodule

import hw3.matmulengine.{MatmulEngine, SystolicEngine}
import hw3.ndarray.Matrix

/**
 * Linear module with a systolic engine.
 */
class SystolicLinear(weight: Matrix) extends Linear(weight) {
  override val me: MatmulEngine = SystolicEngine
}