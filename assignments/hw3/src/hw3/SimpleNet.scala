package hw3

import hw3.ndarray.{Matrix, NDArray}
import hw3.nnmodule.{Linear, NNModule, ReLU, Sequential, Softmax, SystolicLinear}

/**
 * Simple MNIST classification network with the structure below:
 *
 * input: Batched MNIST image [B x 28 x 28] or a single image [28 x 28]
 * output: one-hot vector([B x 10] or [10])
 *
 * structure (suppose that the input is a bathed input):
 *
 * input.reshape(B, 784)
 *   | B x 784
 * Linear(784 -> 50) + ReLU
 *   | B x 50               --|        ------|
 * Linear(50 -> 20) + ReLU    |              |
 *   | B x 20                 |              |
 * Linear(20 -> 50)  Linear(50 -> 50)        |
 *   | B x 50                 |              | x 3
 *  (+) ----------------------|              |
 *   |                                       |
 * ReLU                                ------|
 *   | B x 50
 * Linear(50 -> 10)
 *   | B x 10
 * Softmax
 *   | B x 10
 * output
 *
 * @param weights every weight of the linear
 */
class SimpleNet(val weights: Array[Matrix]) extends NNModule {
  private type LN = Linear

  protected val head: NNModule = Sequential(new LN(weights(0)), ReLU)

  protected val seq1: (NNModule, NNModule) = (Sequential(new LN(weights(1)), ReLU, new LN(weights(2))), new LN(weights(3)))
  protected val seq2: (NNModule, NNModule) = (Sequential(new LN(weights(4)), ReLU, new LN(weights(5))), new LN(weights(6)))
  protected val seq3: (NNModule, NNModule) = (Sequential(new LN(weights(7)), ReLU, new LN(weights(8))), new LN(weights(9)))

  protected val tail: NNModule = Sequential(new LN(weights(10)), Softmax)

  def apply(input: NDArray): NDArray = {
    val flat = if (input.ndim == 3) input.reshape(input.getShape(0), 768) else input.reshape(768)

    val x1 = head(flat)
    val x2 = ReLU(seq1._1(x1).add(seq1._2(x1)))
    val x3 = ReLU(seq2._1(x2).add(seq2._2(x2)))
    val x4 = ReLU(seq3._1(x3).add(seq3._2(x3)))

    tail(x4)
  }
}

class SystolicNet(weights: Array[Matrix]) extends SimpleNet(weights) {
  private type LN = SystolicLinear

  override protected val head: NNModule = Sequential(new LN(weights(0)), ReLU)

  override protected val seq1: (NNModule, NNModule) = (Sequential(new LN(weights(1)), ReLU, new LN(weights(2))), new LN(weights(3)))
  override protected val seq2: (NNModule, NNModule) = (Sequential(new LN(weights(4)), ReLU, new LN(weights(5))), new LN(weights(6)))
  override protected val seq3: (NNModule, NNModule) = (Sequential(new LN(weights(7)), ReLU, new LN(weights(8))), new LN(weights(9)))

  override protected val tail: NNModule = Sequential(new LN(weights(10)), Softmax)
}

