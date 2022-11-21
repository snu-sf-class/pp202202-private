package hw4.value

/**
 * Implementation of the lazy evaluation like the Haskell programming language.
 *
 * This is already given. You should understand it and implement `FnEvaluator`
 * correctly to take an effect of this lazy evaluator.
 */
object LazyValImpl {
  implicit val lazyValIValueImpl: IValue[LazyVal] = new IValue[LazyVal] {
    def genInt(n: Int): LazyVal = Evaluated(ImmInt(n))
    def genFloat(f: Float): LazyVal = Evaluated(ImmFloat(f))

    def matchVal(v: LazyVal)(handleInt: Int => LazyVal, handleFloat: Float => LazyVal): LazyVal = {
      v match {
        case Pending(_, Some(v)) => matchVal(Evaluated(v))(handleInt, handleFloat)
        case Pending(p, None) => Pending(() => matchVal(p())(handleInt, handleFloat), None)
        case Evaluated(ImmInt(n)) => Pending(() => handleInt(n), None)
        case Evaluated(ImmFloat(f)) => Pending(() => handleFloat(f), None)
      }
    }
  }

  implicit val lazyValToFloatImpl: ToFloat[LazyVal] = (value: LazyVal) => {
    _evalLazy(value) match {
      case ImmInt(n) => n
      case ImmFloat(f) => f
    }
  }

  private def _evalLazy(v: LazyVal): ImmVal = {
    v match {
      case v@Pending(p, None) => {
        val ret = _evalLazy(p())
        v.value = Some(ret)
        ret
      }
      case Pending(_, Some(v)) => v
      case Evaluated(v) => v
    }
  }
}
