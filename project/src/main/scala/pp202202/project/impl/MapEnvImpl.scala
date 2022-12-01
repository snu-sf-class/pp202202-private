package pp202202.project.impl

import pp202202.project.common.MapEnv
import pp202202.project.common._

import scala.annotation.tailrec

object MapEnvImpl {
  implicit def mapEnvImpl[V]: EnvOps[MapEnv[V], V] =
    new EnvOps[MapEnv[V], V] {
      def emptyEnv(): MapEnv[V] = ???

      def pushEmptyFrame(env: MapEnv[V]): MapEnv[V] = ???

      def popFrame(env: MapEnv[V]): MapEnv[V] = ???

      def setItem(env: MapEnv[V], name: String, item: V): MapEnv[V] = ???

      def findItem(env: MapEnv[V], name: String): Option[V] = ???
    }
}
