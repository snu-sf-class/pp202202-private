package hw3

import hw3.ndarray.{Matrix, Vector}

import scala.collection.mutable
import scala.io.Source

object MNISTClassifier {
  def getWeight(filePath: String): Array[Matrix] =
    readWeight(filePath).map(m => Matrix.stack(m.map(v => Vector(v))))

  private def readWeight(filePath: String): Array[Array[Array[Float]]] = {
    val fn = Source.fromFile(filePath)
    val weights = mutable.ArrayDeque[Array[Array[Float]]]()
    val currWeight = mutable.ArrayDeque[Array[Float]] ()

    for (line <- fn.getLines) {
      val l = line.strip()
      if (l == "(") {
        currWeight.clear()
      } else if (l == ")") {
        weights.append(currWeight.toArray)
      } else if (l.startsWith("(")) {
        val floats = l.substring(1, l.length - 1).split(",").map(_.toFloat)
        currWeight.append(floats)
      }
    }

    fn.close()
    weights.toArray
  }

}