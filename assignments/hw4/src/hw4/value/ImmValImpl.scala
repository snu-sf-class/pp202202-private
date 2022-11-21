package hw4.value

/**
 * Implementation of the naive eager evaluation.
 */
object ImmValImpl {
  // Instance of IValue[ImmVal]
  implicit val immValImpl: IValue[ImmVal] = ???

  // Instance of ToFloat[Immval]
  implicit val immValToFloatImpl: ToFloat[ImmVal] = ???
}
