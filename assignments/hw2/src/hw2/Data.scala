package hw2

object Data {
  // for the problem 2
  sealed trait BNum
  case object BHead extends BNum
  case class B0(next: BNum) extends BNum
  case class B1(next: BNum) extends BNum

  // for the problem 3
  case class BMTree[T](value: T, order: Int, children: List[BMTree[T]])
  case class BMHeap[T](trees: List[BMTree[T]])
}
