package hw3

import hw3.ndarray.Matrix

/**
 * NOTE: we will give more complicate test cases later.
 */
object Test extends App {
    def print_result(b: Boolean) : Unit =
      if (b) println("O") else println("X")

    def rand(i: Int, j: Int): Matrix = Matrix(Array(i, j), Array.fill(i * j)(Math.random().toFloat % 1.0f))
    def params: Array[Matrix] = Array(
        rand(784, 50),
        rand(50, 20), rand(20, 50), rand(50, 50),
        rand(50, 20), rand(20, 50), rand(50, 50),
        rand(50, 20), rand(20, 50), rand(50, 50),
        rand(50, 10)
    )

    val net = new SimpleNet(params)
    val sysnet = new SystolicNet(params)

    val input = rand(28, 28)

    println(net(input))
    println(sysnet(input))
}
