package streaming.ml.evaluation

case class Metrics(ranking: Array[Int]) {

  def summary(): Summary = Summary(auroc(), average_precision(), ranking.length)

  def auroc(): Double = {
    var roc = 0.0
    var true_positive = 0
    var false_positive = 0
    for (value <- ranking) {
      if (value > 0) true_positive += 1 // relevant hit
      else {
        // missed
        false_positive += 1
        roc += true_positive
      }
    }
    if (true_positive * false_positive > 0.0) roc / (true_positive * false_positive)
    else if (true_positive > 0) 1.0
    else 0.0
  }

  def average_precision(): Double = {
    var avg_p = 0.0
    var positives = 0
    for (k <- ranking.indices) {
      if (ranking(k) > 0) {
        positives += 1
        avg_p += positives.toDouble / (k + 1)
      }
    }
    avg_p / positives
  }
}

object Metrics {

  def apply(trueLabels: Iterable[Int], predictions: Iterable[Double]): Metrics = {
    val indices = predictions.toArray.zipWithIndex.sortBy(_._1)(Ordering.Double.reverse).map(_._2)
    val ranking = indices.map(trueLabels.zipWithIndex.map(e => e._2 -> e._1).toMap)
    new Metrics(ranking)
  }
}
