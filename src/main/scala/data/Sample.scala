package data

case class Sample(label: Boolean, featureVector: Map[Int, Double]) {

  val etykieta = label
  val wektorcech = featureVector
  
  def getVector: Array[Double] = {
    val v = Array.fill(10)(0.0)
    featureVector.keys.foreach(ind => v(ind) = 1)
    v
  }

  override def equals(that: Any): Boolean = ???
}
