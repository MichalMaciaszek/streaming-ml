package streaming.ml

case class Sample(label: Boolean, featureVector: Map[Int, Double]) {

  @deprecated
  def getVector: Vector[Double] = Vector.empty

  override def toString: String = {
    (if (label) "1 " else "0 ") + (featureVector.toSeq.map(f => f._1.toString + ":" + f._2.toString) mkString " ")
  }
}
