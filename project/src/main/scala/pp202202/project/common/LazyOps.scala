package pp202202.project.common

trait LazyOps[Imm, Lazy] {
  /**
   * Trivially convert the evaluated value to a lazy value.
   * The value is not pended.
   */
  def to(value: Imm): Lazy

  /**
   * Make new lazy value which has a pending operation.
   *
   * @param pending Lazy value generator
   * @return lazy thunk
   */
  def pend(pending: () => Imm): Lazy

  /**
   * Evaluate the lazy value and generate an immediate value.
   */
  def evaluate(value: Lazy): Imm
}
