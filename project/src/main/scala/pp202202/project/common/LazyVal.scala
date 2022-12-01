package pp202202.project.common

sealed trait LazyVal[V]

// Evaluated value
case class LVVal[V](v: V) extends LazyVal[V]

// Lazy value
case class LVLazy[V](pending: () => V) extends LazyVal[V] {
  lazy val evaluated: V = pending()
}
