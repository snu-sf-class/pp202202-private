package hw2
import hw2.Data._
import hw2.Main._
import scala.reflect.runtime.universe.typeOf

object Test extends App {
    def print_result(b: Boolean) : Unit =
      if (b) println("O") else println("X")
    
    val mc = new Problem1.MyClass[Int, Boolean, Unit, Float, (Int, Int), (Int, Int, Int)]()
    val typeTest = typeOf[mc.Ty1]<:<typeOf[mc.CommonTy] && typeOf[mc.Ty2]<:<typeOf[mc.CommonTy] && !(typeOf[Any]<:<typeOf[mc.CommonTy])
    
    print_result(typeTest)

    val one = BHead
    val two = B0(BHead)
    val six = B0(B1(BHead))
    val eight = B0(B0(B0(BHead)))
    val twelve = B0(B0(B1(BHead)))

    print_result(add(one, one) == two)
    print_result(add(six, two) == eight)

    print_result(mul(one, one) == one)
    print_result(mul(six, two) == twelve)

    print_result(compare(six, two) == 1)
    print_result(compare(one, two) == -1)
    print_result(compare(two, B0(BHead)) == 0)

    val empty: BMHeap[BNum] = BMHeap(Nil)
    val h1 = insert(empty, one, compare)
    val h12 = insert(h1, two, compare)
    val h18 = insert(h1, eight, compare)
    val h126 = insert(h12, six, compare)
    val h11268 = merge(h126, h18, compare)

    val (v1, h1268) = deleteMin(h11268, compare)
    val (v1another, h268) = deleteMin(h1268, compare)

    print_result(v1.contains(one))
    print_result(v1another.contains(one))

    val isV2 = findMin(h268, compare)
    print_result(isV2.contains(two))

    val (v2, h68) = deleteMin(h268, compare)
    val (v6, h8) = deleteMin(h68, compare)
    val (v8, hEmpty) = deleteMin(h8, compare)
    val (vNone, _) = deleteMin(hEmpty, compare)

    print_result(v2.contains(two))
    print_result(v6.contains(six))
    print_result(v8.contains(eight))
    print_result(vNone.isEmpty)
}
