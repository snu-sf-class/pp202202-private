package hw4.value

/**
 * Type class to convert a value into float
 */
trait ToFloat[V] {
  /**
   * Convert this to a float value
   * @return converted value
   */
  def toFloat(value: V): Float
}
