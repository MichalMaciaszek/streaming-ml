package streaming.ml

case class Sample(label: Int, featureVector: Map[Int, Double]) {

  override def toString: String = {
    label + " " +
      (featureVector.toSeq.map(
        f => f._1.toString + ":" + BigDecimal(f._2).underlying.stripTrailingZeros.toString
      ) mkString " ")
  }
}
