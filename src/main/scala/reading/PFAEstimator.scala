package reading

import java.io.FileInputStream

import com.opendatagroup.hadrian.jvmcompiler.PFAEngine
import streaming.ml.Estimator

import scala.collection.mutable

case class PFAEstimator(val engine: PFAEngine[AnyRef, AnyRef]  = PFAEngine.fromJson(new FileInputStream("to_deploy")).head) extends Estimator[mutable.HashMap[Int, Double]] {

  def display(): Any = {engine.callGraph("(action)").foreach(println)}

  def fit(model: mutable.HashMap[Int, Double], featureVector: collection.Map[Int, Double], label: Int): mutable.HashMap[Int, Double] = ???

  def predict(path: String): Unit = {
    val inputData = engine.jsonInputIterator(new FileInputStream(path))
        var output: Any = 1
        while (inputData.hasNext) {
          output = engine.action(inputData.next())
          println(output)
        }
  }
  override def predict(model: mutable.HashMap[Int, Double], featureVector: collection.Map[Int, Double]): Double = ???
}
