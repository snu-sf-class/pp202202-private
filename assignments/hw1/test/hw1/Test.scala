package hw1
import hw1.Main._

object Test extends App {
    def print_result(b: Boolean) : Unit =
      if (b) println("O") else println("X")

    print_result(sum(5L, 5L) == 5)
    print_result(sum(10L, 1000000L) == 500000499955L)
    print_result(sum(10, 5) == 0)

    def sub(a: Long, b: Long): Long = a - b

    print_result(fold(sub, 5, 10) == -3)
    print_result(fold(sub, 5L, 1000000L) == -499998L)
    print_result(fold(sub, 10, 5) == 0)
    print_result(fold(sub, 10, 10) == 0)

    print_result(coeff(5, 5) == 1)
    print_result(coeff(10, 5) == 252)
    print_result(coeff(64, 32) == 1832624140942590534L)

    def pred(n: Long): Boolean = n <= 1
    def collatz(n: Long): Long = if (n % 2 == 0) n / 2 else 3 * n + 1

    print_result(terminate(pred, collatz, 97) == 118)
    print_result(terminate(pred, collatz, 77031) == 350)
    print_result(terminate(pred, collatz, 989345275647L) == 1348)
}
