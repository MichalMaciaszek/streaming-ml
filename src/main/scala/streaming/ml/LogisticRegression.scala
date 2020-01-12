package streaming.ml

import scala.collection.{Map, mutable}
import scala.util.Random

case class LogisticRegression(initWeight: Double = 0.05,
                              learningRate: Double = 0.01,
                              lambda: Double = 1E-6
                             ) {

  private def nextInitWeight(): Double = (Random.nextFloat - 0.5) * initWeight

  private def sigmoid(p: Double): Double = 1.0 / (1.0 + math.exp(-p))

  def fit(model: mutable.HashMap[Int, Double],
          featureVector: Map[Int, Double], label: Int): mutable.HashMap[Int, Double] = {
    // init weights
    featureVector.keys.filter(!model.contains(_)).foreach(model(_) = nextInitWeight())

    // predict
    val p = predict(model, featureVector)

    // update weights
    // w_i = w_i + learning_rate * [ (y - p) * x_i - lambda * w_i ]
    for (feat <- featureVector.keys) {
      model(feat) = model(feat) * (1 - lambda) + learningRate * (label - p)
    }

    model
  }

  def predict(model: mutable.HashMap[Int, Double], featureVector: Map[Int, Double]): Double =
    sigmoid(featureVector.map { case (feat, value) => model.getOrElse(feat, 0.0) * value }.sum)

}
