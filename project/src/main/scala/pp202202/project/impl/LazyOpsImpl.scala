package pp202202.project.impl

import pp202202.project.common.{Val, LVLazy, LVVal, LazyOps, LazyVal}

object LazyOpsImpl {
  type LO = LazyOps[Val, LazyVal[Val]]

  implicit val lazyOpsImpl: LO = new LO {
      def to(value: Val): LVVal[Val] = ???

      def pend(pending: () => Val): LazyVal[Val] = ???

      def evaluate(value: LazyVal[Val]): Val = ???
    }
}
