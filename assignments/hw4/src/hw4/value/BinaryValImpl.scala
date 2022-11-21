package hw4.value

/**
 * Instance of IValue[BinaryVal] and ToFloat[BinaryVal]
 * This should convert 32-bit binary stream to integer or float.
 *
 * You can use some JVM functions to implement it, such as:
 * java.lang.Float.floatToIntBits(yourFloat)
 * java.lang.Integer.toBinaryString(intBits)
 * java.lang.Long.parseLong(myString, 2).toInt
 * java.lang.Float.intBitsToFloat(intBits)
 *
 * See https://stackoverflow.com/questions/5157664/java-how-to-convert-a-string-of-binary-values-to-a-float-and-vice-versa
 */
object BinaryValImpl {
  // Instance of IValue[BinaryImpl]
  implicit val binaryValImpl: IValue[BinaryVal] = ???

  // Instance of ToFloat[BinaryVal]
  implicit val binaryValToFloatImpl: ToFloat[BinaryVal] = ???
}
