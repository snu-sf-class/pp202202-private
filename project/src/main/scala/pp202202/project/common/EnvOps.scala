package pp202202.project.common

trait EnvOps[Env, V] {
  def emptyEnv(): Env

  def pushEmptyFrame(env: Env): Env

  def popFrame(env: Env): Env

  def setItem(env: Env, name: String, item: V): Env

  def findItem(env: Env, name: String): Option[V]
}
