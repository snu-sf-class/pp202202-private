package hw4.value

/**
 * Lazy values
 */
trait LazyVal

/**
 * Value which should be evaluated later
 * If `pend` is evaluated into a single ImmVal, that value should be stored to `value`
 *
 * @param pend continuation of this value
 * @param value memoized value of this value
 */
case class Pending(pend: () => LazyVal, var value: Option[ImmVal]) extends LazyVal

/**
 * Evaluated value
 */
case class Evaluated(v: ImmVal) extends LazyVal
