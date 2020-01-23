package streaming.ml

import org.apache.flink.api.java.functions.NullByteKeySelector
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.configuration.{ConfigConstants, Configuration}
import org.apache.flink.core.fs.FileSystem
import org.apache.flink.streaming.api.scala._
import streaming.ml.features.FeatureExtractor

import scala.collection.mutable

object FlinkJob {
  def main(args: Array[String]) {
    val parameters = ParameterTool.fromArgs(args)

    val localConfig = new Configuration
    localConfig.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, true)
    val env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(localConfig)
    env.setParallelism(1)

    val input = parameters.get("input", "C:/Users/marce/Desktop/conv.20130609.txt")
    val stream: DataStream[String] = env.readTextFile(input)

    val sampleStream = stream.map(FeatureExtractor())
    sampleStream.writeAsText("samples.libsvm", FileSystem.WriteMode.OVERWRITE)

    val lr = LogisticRegression()
    val predictions = sampleStream
      .keyBy(new NullByteKeySelector[Sample])
      .mapWithState((sample: Sample, state: Option[mutable.HashMap[Int, Double]]) => {
        state match {
          case Some(model) =>
            val p = lr.predict(model, sample.featureVector)
            val m = lr.fit(model, sample.featureVector, sample.label)
            ((sample.label, p), Some(m))
          case None =>
            ((sample.label, 0.0), Some(lr.fit(mutable.HashMap.empty, sample.featureVector, sample.label)))
        }
      })
    predictions
      .map(p => p._1 + "\t" + p._2)
      .writeAsText("predictions.tsv", FileSystem.WriteMode.OVERWRITE)

    predictions
      .countWindowAll(100000, 20000)
      .process(PredictionsEvaluator())
      .writeAsText("metrics.tsv", FileSystem.WriteMode.OVERWRITE)

    env.execute("Streaming ML")
  }
}