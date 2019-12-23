package data

import org.apache.flink.core.fs.FileSystem
import org.apache.flink.streaming.api.scala._

import scala.collection.Map
import scala.collection.mutable.ListBuffer
import scala.util.Random

object log_reg {

  val initWeight = 0.05
  //val bufferCaseNum = 1000000
  val eta = 0.01
  val lamb = 1E-6
  var featWeight: Map[Int, Double] = Map.empty
  val trainRounds = 5
  //var lineNum = 0

  def nextInitWeight(): Double = (Random.nextFloat - 0.5) * initWeight

  def ints(s: ListBuffer[Float]): ListBuffer[Int] = {
    val res = new ListBuffer[Int]()
    s.foreach { e => res += e.toInt }
    res
  }

  def sigmoid(p: Double): Double = 1.0 / (1.0 + math.exp(-p))

  def fit(state: Map[Int, Double], sample: Sample): Map[Int, Double] = {
    val model = collection.mutable.Map(state.toSeq: _*)
    val vector = sample.getVector
    val clk = vector(0)
    val fsid = 2 //feature start id
    var pred = 0.0

    for (i <- fsid until vector.length) {
      val feat = vector(i).toInt
      if (!model.contains(feat)) {
        model(feat) = nextInitWeight()
      }
      pred += model(feat)
    }
    pred = sigmoid(pred)
    for (i <- fsid until vector.length) {
      val feat = vector(i).toInt
      model(feat) = model(feat) * (1 - lamb) + eta * (clk - pred)
    }
    model.toMap
  }

  def predict(model: Map[Int, Double], sample: Sample): Double = {
    val vector = sample.getVector
    val clk = vector(0)
    val fsid = 2 //feature start id
    var pred = 0.0

    for (i <- fsid until vector.length) {
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

    val input: DataStream[String] =
      env.readTextFile("file:/home/kate/Desktop/input.txt")

    val sampleStream: DataStream[Sample] = input.map(FeatureExtractor())

    val predictionStream = sampleStream
      .keyBy(x => 0)
      .mapWithState((sample: Sample, state: Option[Map[Int, Double]]) => {
        state match {
          case Some(m) =>
            val model = fit(m, sample)
            val p = predict(m, sample)
            (p, Some(m))
          case None =>
            val model = fit(Map.empty, sample)
            val p = predict(model, sample)
            (p, Some(model))
        }
      })

    predictionStream.writeAsText("output.txt", FileSystem.WriteMode.OVERWRITE)
    env.execute()
  }
}