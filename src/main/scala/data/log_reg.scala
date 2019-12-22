package data

import org.apache.flink.core.fs.FileSystem
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala._
import scala.collection.mutable.ListBuffer
import scala.collection.Map

object log_reg {

  val r = scala.util.Random
  val initWeight = 0.05
  //val bufferCaseNum = 1000000
  val eta = 0.01
  val lamb = 1E-6
  var featWeight = scala.collection.mutable.Map[Int, Double]()
  val trainRounds = 5
  //var lineNum = 0

  def nextInitWeight() = (r.nextFloat - 0.5) * initWeight

  def ints(s: ListBuffer[Float]): ListBuffer[Int] = {
    val res = new ListBuffer[Int]()
    s.foreach { e => res += e.toInt }
    res
  }

  def sigmoid(p: Double) = 1.0 / (1.0 + math.exp(-p))

  def fit(state: scala.collection.mutable.Map[Int,Double], sample: Sample): Map[Int,Double] = {
    val model = state
    val vector = sample.getVector
    val clk = vector(0)
    val fsid = 2 //feature start id
    var pred = 0.0

    for (i <- fsid to vector.size) {
      val feat = vector(i).toInt
      if (!model.contains(feat)) {
        model(feat) = nextInitWeight()
      }
      pred += model(feat)
    }
    pred = sigmoid(pred)
    for (i <- fsid to vector.size) {
      val feat = vector(i).toInt
      model(feat) = model(feat) * (1 - lamb) + eta * (clk - pred)
    }
    model
  }

  def predict(model: Map[Int,Double], sample: Sample) = {
    val model = collection.mutable.Map[Int,Double](model)
    val vector = sample.getVector
    val clk = vector(0)
    val fsid = 2 //feature start id
    var pred = 0.0

    for (i <- fsid to vector.size) {
      val feat = vector(i).toInt
      if (model.contains(feat)) {
        pred += model(feat)
      }
    }
    pred = sigmoid(pred)
    pred
  }

  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val input: DataStream[String] = env.readTextFile(
      "file:/home/kate/Desktop/input.txt")

    val sampleStream: DataStream[Sample] = input.map(FeatureExtractor())

    for( w <- 0 to trainRounds-1) {
      sampleStream
        .keyBy(x => 0)
        .mapWithState((sample: Sample, state: Option[Map[Int, Double]]) => {
          val p, model =
            state match {
              case Some(model) => val model = fit(model, sample)
                val p = predict(model, sample)
                (p, Some(model))
              case None => val model = fit(scala.collection.mutable.Map[Int, Double], sample)
                val p = predict(model, sample)
                (p, Some(model))
            }
          //val model = fit(state, sample)
          //val p = predict(model, sample)
          (p, Some(model))
        })
    }

    sampleStream.writeAsText("output.txt", FileSystem.WriteMode.OVERWRITE)
    env.execute()
  }
}