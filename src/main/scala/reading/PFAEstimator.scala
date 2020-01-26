package reading

import java.io.{File, FileInputStream, PrintWriter}

import com.opendatagroup.hadrian.jvmcompiler.PFAEngine
import streaming.ml.Estimator


case class PFAEstimator(engine: PFAEngine[AnyRef, AnyRef]) extends Estimator[Unit] {

  def display(): Any = {engine.callGraph("(action)").foreach(println)}

  def fit(model: Unit, featureVector: collection.Map[Int, Double], label: Int): Unit = model

  def predict(model: Unit, featureVector: collection.Map[Int, Double]): Double =
  {
    //create json to load
    var a = Vector.fill(13366)(0.0)
    for ((k,v) <- featureVector) if(v== 1.0) {a = a.updated(k,1.0)  }
    val start = """{"x":["""
    val end = """]}"""
    val json = a.mkString(start, ",", end)
    val pw = new PrintWriter(new File("file.jsons"))
    pw.write(json)
    pw.close

    val inputData = engine.jsonInputIterator(new FileInputStream("file.jsons"))
    var output: Any = 0.0
    var result : Double = 3.0
      while (inputData.hasNext) {
        output = engine.action(inputData.next())
        result = output.asInstanceOf[Double]
    }
      return result
  }
}
