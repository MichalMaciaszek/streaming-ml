package data

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.core.fs.FileSystem
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.collection.Map


object log_reg {

  //  def test(Ds:DataStream[String]) = {
  //    //test
  //    val y = new ListBuffer[Int]()
  //    val yp = new ListBuffer[Double]()
  //
  //    val test: DataStream[String] = Ds
  //    val mappedTest = test.map { x =>
  //
  //      val Line = new ListBuffer[Float]()
  //      x.split(",").map(Line += _.trim.toFloat)
  //      val IntLine = ints(Line)
  //      val LineList = IntLine.toList
  //
  //      val clk = LineList(0)
  //      val mp = LineList(1)
  //      val fsid = 0
  //      var pred = 0.0
  //
  //      for(i <- fsid to LineList.size-1){
  //        val feat = LineList(i)
  //        if(featWeight.contains(feat)){
  //          pred += featWeight(feat)
  //        }
  //      }
  //      pred = sigmoid(pred)
  //      y += clk
  //      yp += pred
  //    }
  //  }

  val r = scala.util.Random
  val initWeight = 0.05
  val bufferCaseNum = 1000000
  val eta = 0.01
  val lamb = 1E-6
  var featWeight = scala.collection.mutable.Map[Int, Double]()
  val trainRounds = 1
  var lineNum = 0

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
    //var model = scala.collection.mutable.Map[Double,Double]()
    val clk = vector(0)
    val fsid = 2 //feature start id
    var pred = 0.0

    for (i <- fsid to vector.size) {
      val feat = vector(i).toInt
      if (!model.contains(feat)) {
        model.apply(feat)
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

  def predict(model: Map[Int,Double], sample: Sample) = ???

  def main(args: Array[String]) {

    //read the train file to Stream
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val input: DataStream[String] = env.readTextFile(
      "file:/home/kate/Desktop/input.txt")

    val sampleStream: DataStream[Sample] = input.map(FeatureExtractor())

    sampleStream
      .keyBy(x => 0)
      .mapWithState((sample: Sample, state: Option[Map[Int,Double]]) => {
        val p, model =
        state match {
          case Some(model) => val model = fit(model, sample)
            val p = predict(model, sample)
            (p, Some(model))
          case None => val model = fit(scala.collection.mutable.Map[Int,Double],sample)
            val p = predict(model, sample)
            (p, Some(model))
        }
        //val model = fit(state, sample)
        //val p = predict(model, sample)
        (p, Some(model))
    })

    sampleStream.writeAsText("output.txt", FileSystem.WriteMode.OVERWRITE)
    env.execute()
  }
}
