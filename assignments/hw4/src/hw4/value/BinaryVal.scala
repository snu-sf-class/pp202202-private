package hw4.value

/**
 * 32-bit raw bit stream.
 *
 * bits.length must be equal to 32
 * bits.head must be an MSB of the value.
 */
sealed trait BinaryVal {
  val bits: List[Boolean]
}

// 32-bit raw int
case class BinaryInt(bits: List[Boolean]) extends BinaryVal

// 32-bit raw float
case class BinaryFloat(bits: List[Boolean]) extends BinaryVal

