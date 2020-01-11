package streaming.ml

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.configuration.{ConfigConstants, Configuration}
import org.apache.flink.streaming.api.scala._
import org.apache.flink.core.fs.FileSystem
import streaming.ml.features.FeatureExtractor

// bid  00
// clk  01
// conv 10
// imp  11

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

    sampleStream.writeAsText("clk1.txt", FileSystem.WriteMode.OVERWRITE)

    env.execute("Window Stream")
  }
}