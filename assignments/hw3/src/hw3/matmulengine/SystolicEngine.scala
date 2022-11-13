/**
 * Implementation of non-parallel Systolic Array.
 *
 * See http://ecelabs.njit.edu/ece459/lab3.php to know how systolic array works.
 * However, you don't need to understand how it works if you have properly implemented `NDArray`.
 *
 * WARNING: YOU MUST NOT FIX THIS CODE. We will compile your code with an unmodified code.
 */
package hw3.matmulengine

import hw3.ndarray.{Matrix, NNException, Vector}

import scala.annotation.tailrec

private final class Processor {
  var cumul: Float = 0
  var toRight: Float = 0
  var toDown: Float = 0
  var right: Option[Processor] = None
  var down: Option[Processor] = None

  def step(): Unit = cumul += toRight * toDown

  @tailrec
  def sendRight(fromLeft: Float): Unit = {
    val temp = toRight
    toRight = fromLeft
    if (right.isDefined) {
      right.get.sendRight(temp)
    }
  }

  @tailrec
  def sendDown(fromUp: Float): Unit = {
    val temp = toDown
    toDown = fromUp
    if (down.isDefined) {
      down.get.sendDown(temp)
    }
  }
}

private final class SystolicArray(left: Matrix, right: Matrix) {
  private val n = left.getShape(0)
  private val k = left.getShape(1)
  private val m = right.getShape(1)
  private val stepN = (k - 1) + (if (n < m) m else n)

  private val grid = {
    val arr = Array.fill(n)(Array.fill(m)(new Processor))

    for (i <- 0 until n - 1) {
      arr(i).zip(arr(i + 1)).foreach(ud => { ud._1.down = Some(ud._2) })
    }

    for (row <- arr; i <- 0 until m - 1) {
      row(i).right = Some(row(i + 1))
    }

    arr
  }

  private def sheeredLeft(idx: Int): Array[Float] = {
    val arr: Array[Float] = Array.fill(n)(0)

    for (i <- 0 until n if i <= idx && idx < i + k) {
      arr(i) = left.getArr(i).get(i + k - 1 - idx)
    }

    arr
  }

  private def sheeredUp(idx: Int): Array[Float] = {
    val arr: Array[Float] = Array.fill(m)(0)

    for (i <- 0 until m if i <= idx && idx < i + k) {
      arr(i) = right.getArr(i + k - 1 - idx).get(i)
    }

    arr
  }

  private def shiftRight(input: Array[Float]): Unit = {
    for ((f, row) <- input.zip(grid)) {
      row(0).sendRight(f)
    }
  }

  private def shiftDown(input: Array[Float]): Unit = {
    for ((f, col) <- input.zip(grid(0))) {
      col.sendDown(f)
    }
  }

  def run: Matrix = {
    for (i <- 0 until stepN) {
      shiftRight(sheeredLeft(i))
      shiftDown(sheeredUp(i))
      grid.foreach(_.foreach(_.step()))
    }

    Matrix(grid.map(v => Vector(v.map(_.cumul))))
  }
}

object SystolicEngine extends MatmulEngine {
  def matmul(left: Matrix, right: Matrix): Matrix = {
    // WARNING: YOU MUST NOT FIX ANY CODE IN THIS BLOCK
    val leftShape = left.getShape
    val rightShape = right.getShape

    if (leftShape(1) != rightShape(0)) {
      throw new NNException(
        f"""
           |matmul Dimension Mismatch:
           |  left: ${leftShape.mkString("(", ", ", ")")}, right: ${rightShape.mkString("(", ", ", ")")}
           |""".stripMargin)
    }

    val array = new SystolicArray(left, right)

    array.run
  }
}
