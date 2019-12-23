package data

case class Sample(label: Boolean, featureVector: Map[Int, Double]) {

  def getVector: Array[Double] = {
    val v = Array.fill(10)(0.0)
    featureVector.keys.foreach(ind => v(ind) = 1)
    v
  }

  def render(): String = {
    if (label) "1 " else "0 "
  }

  override def equals(that: Any): Boolean = ???
}
