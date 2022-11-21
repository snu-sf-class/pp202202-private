package hw4.value

/**
 * Immediate values
 */
sealed trait ImmVal
case class ImmInt(n: Int) extends ImmVal
case class ImmFloat(f: Float) extends ImmVal
