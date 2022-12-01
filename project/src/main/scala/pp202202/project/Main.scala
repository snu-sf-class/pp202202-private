package pp202202.project

import pp202202.project.common.Utils._
import pp202202.project.impl.LazyOpsImpl._
import pp202202.project.impl.MapEnvImpl._
import pp202202.project.impl.ExprInterpreter._

import pp202202.project.common._

object Main extends App {
  ExprRepl.run[LazyVal[Val]]()
}
