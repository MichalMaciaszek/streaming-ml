package streaming.ml.evaluation

case class Summary(auroc: Double, averagePrecision: Double, sampleSize: Int) {

  override def toString: String = s"$auroc\t$averagePrecision\t$sampleSize"
}
