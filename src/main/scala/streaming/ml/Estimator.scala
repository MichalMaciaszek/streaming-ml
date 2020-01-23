package streaming.ml

import scala.collection.Map

trait Estimator[MODEL] {

  def fit(model: MODEL, featureVector: Map[Int, Double], label: Int): MODEL

  def predict(model: MODEL, featureVector: Map[Int, Double]): Double
}
