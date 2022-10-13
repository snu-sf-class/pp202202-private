package hw2
import hw2.Data._
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
   * 2. From now on, you can use some classes in scala.collections.* (except scala.collections.mutable.*). 
   *    You can also make your custom case classes.
   *
   * Again, your score will be zero if you do not follow these rules.
   *
   * Note that these rules will be gradually relaxed through the next assignments.
   *
   * WARNING: Do not modify Data.scala.
   * We will grade your assignment only with the submitted Main.scala file.
   *
   */

  /**
   * Problem 1: Structural subtype (5 points)
   *
   * Find the **least** (i.e. most specific) common supertype of Ty1 and Ty2.
   *
   *   `CommonTy >: Ty1 && CommonTy >: Ty2`
   *
   * We will check your answer by comparing it with our answer as follows:
   *
   * `checkType(Ty1 <: CommonTy && Ty2 <: CommonTy && CommonTy <: Answer)`
   *
   * DO NOT USE "Any" in anywhere in your code
   */
  object Problem1 {
    class MyClass[A, B, C, D, E, F]() {
      type Func1 = {val a: B} => {val b: A}
      type Func2 = {val b: D} => {val a: A}
      type Func3 = {val c: C} => {val a: B}
      type Func4 = {val f: E} => {val d: F}

      type Ty1 = {
        def apply: {val func: Func1; val c: C} => {val b: B; val c: C; val f: F}
        def function1: {val func: Func3} => {val a: A; val func: Func2}
        val a: A
        val b: B
        val f: F
      }

      type Ty2 = {
        def apply: {val func: Func2; val e: E} => {val b: B; val e: E }
        def function1: {val func: Func4} => {val c: C; val func: Func1}
        val a: A
        val c: C
        val d: D
      }

      /**
       * WRITE YOUR ANSWER
       */
      type CommonTy = ???
    }
  }


  /**
   * Problem 2: Reversed Binary Representation (5 points each)
   *
   * Implement the basic operations of reversed binary representation (RBB).
   * You should use `BNum` type from
   *
   * RBB is one way to represent natural number through ADT.
   *
   * BHead: Most significant bit (1)
   * B1: One (1)
   * B0: Zero (0)
   *
   * e.g.)
   * number 1 -> BHead
   * number 6 -> /binarize/ 110 (2) -> /reverse/ 011 -> /RBB/ B0(B1(BHead))
   * number 11 -> /binarize/ 1011 (2) -> /reverse/ 1101 -> /RBB/ B1(B1(B0(BHead)))
   **/

  /**
   * Addition (a + b)
   * e.g.) add(B0(B1(BHead)), B0(BHead)) == B0(B0(B0(BHead))) // 6 + 2 = 8
   */
  def add(a: BNum, b: BNum): BNum = ???

  /**
   * Multiplication (a * b)
   * e.g.) mul(B0(B1(BHead)), B0(BHead)) == B0(B0(B1(BHead))) // 6 * 2 = 12
   */
  def mul(a: BNum, b: BNum): BNum = ???

  /**
   * Comparison (a > b)
   * e.g.) compare(B0(B1(BHead)), B0(BHead)) == 1 // 6 > 2
   *
   * return
   * | 1  (a > b)
   * | 0  (a == b)
   * | -1 (a < b)
   */
  def compare(a: BNum, b: BNum): Int = ???

  /**
   * Problem 3: Binomial Heap (5 points each)
   *
   * Implement the basic operations of binomial heap.
   * https://en.wikipedia.org/wiki/Binomial_heap
   *
   * We will grade your score by checking the result heap satisfies the binomial heap conditions:
   * 1. Every binomial tree in a heap obeys the minimum-heap property:
   *    the value of a node is greater than or equal to the value of its parent.
   * 2. There can be at most one binomial tree for each order, including zero order.
   * 3. findMin and deleteMin correctly return the minimum value.
   *
   * Which means, you don't need to concern the exact order of the values in your heap.
   *
   * WARNING: You should not confuse it with *Binary* heap.
   *
   * cf) We can use type restriction on T that T is comparable.
   *     That feature will be presented on the latter classes.
   **/
  def merge[T](left: BMHeap[T], right: BMHeap[T], compare: (T, T) => Int): BMHeap[T] = ???

  def insert[T](heap: BMHeap[T], value: T, compare: (T, T) => Int): BMHeap[T] = ???

  // return Some(minimum value) if the heap is not empty
  // return None if the heap is empty
  def findMin[T](heap: BMHeap[T], compare: (T, T) => Int): Option[T] = ???

  // return (Some(minimum value), remained heap) if the heap is not empty
  // return (None, empty heap) if the heap is empty
  def deleteMin[T](heap: BMHeap[T], compare: (T, T) => Int): (Option[T], BMHeap[T]) = ???
}
