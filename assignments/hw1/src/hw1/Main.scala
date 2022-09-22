package hw1
import scala.annotation.tailrec
import scala.util.control.TailCalls._

object Main {
  /**
   * Implement given functions, which are currently left blank. (???)
   * **WARNING: Please read the restrictions below carefully.**
   *
   * If you do not follow these, **your submission will not be graded.**
   *
   * 1. Do not use the keyword `var`. Use `val` and `def` instead.
   * 2. Do not use any library functions or data structures like `List`, `Array`,
   *   `Range` (`1 to n`, `1 until n` ...), `fold`, `map`, `reduce` or etc.
   *    You can only use tuples, `scala.annotation.tailrec`, and
   *    `scala.util.control.TailCalls._` from the library.
   * 3. Do not use Scala's iteration(loop) syntax (`for`, `while`, `do-while`, `yield`)
   *
   * Again, your score will be zero if you do not follow these rules.
   *
   * Note that these rules will be gradually relaxed through the next assignments.
   *
   *
   * For problem 1 and 3, 50% of the test cases will require tail call optimizations (i.e., large inputs)
   *   and the other 50% will not (i.e., small inputs).
   *
   * So, you will get at least 50% of the score if you submit a correct program without
   *   tail call optimization.
   */

  /**
   * Exercise 1-1: Basic summation
   *
   * Calculate a + (a + 1) + ... + b  `(0 <= a, b <= 10^5)`
   *
   * when a > b, return 0
   */
  def sum(a: Long, b: Long): Long = ???


  /**
   * Exercise 1-2: fold
   *
   * Calculate f(a, f(a + 1, f(a + 2, ... f(b - 1, b))))  `(0 <= a, b <= 10^6)`
   *
   * when a >= b, return 0
   *
   * We guarantee that any (f, a, b) in the test set will not raise integer overflow.
   */
  def fold(f: (Long, Long) => Long, a: Long, b: Long): Long = ???

  /**
   * Exercise 2: Binomial Coefficient
   *
   * Calculate the binomial coefficient with n and k, i.e. nCk  (0 <= k <= n <= 64)
   *
   * Check https://en.wikipedia.org/wiki/Binomial_coefficient
   *
   * WARNING: You must not raise any integer overflow during the calculation.
   * In other words, every basic calculation should be in the range of `Long`.
   *
   * Hint: Find an appropriate formula from the above link.
   * Hint 2: GCD
   **/
  def coeff(n: Long, k: Long): Long = ???

  /**
   * Exercise 3: Termination checker
   *
   * Find the first integer `n` which makes `pred(f^n(init))` True.
   *
   * In other words, if pred(init), pred(f(init)), and pred(f(f(init))) is all False,
   *   but pred(f(f(f(init)))) gives the first True value, then return 3.
   * If pred(init) is True, return 0.
   *
   * f: repeating function
   * pred: Termination predicate. If p(n) returns True,
   * init: initial input
   *
   * We guarantee that every input in the test set will always
   *   terminate the checker if your checker is correct and efficient.
   **/
  def terminate(pred: Long => Boolean, f: Long => Long, init: Long): Long = ???
}