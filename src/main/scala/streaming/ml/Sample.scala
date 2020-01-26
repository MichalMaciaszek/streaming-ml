package streaming.ml

case class Sample(label: Int, featureVector: Map[Int, Double]) {

  override def toString: String = {
    label + " " +
      (featureVector.toSeq.sortBy(_._1).map(
        f => f._1.toString + ":" + BigDecimal(f._2).underlying.stripTrailingZeros.toString
      ) mkString " ")
  }
}

object Sample {
  def apply(s: String): Sample = {
    val values = s.trim.split(' ')
    val label = values.head.toInt
    val features = values.tail.map(_.split(':')).map(f => f(0).toInt -> f(1).toDouble).toMap
    Sample(label, features)
  }
}


