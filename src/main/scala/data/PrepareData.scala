package com.aisiot.bigdata.flinkcore

import org.apache.flink.streaming.api.scala._
import org.apache.flink.core.fs.FileSystem

object flinkwc {
  def main(args: Array[String]) {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[String] = env.readTextFile("C:/Users/marce/Desktop/conv.20130609.txt")

    val sampleStream = stream.map(FeatureExtractor("01"))

    val sampletext = sampleStream.map(MakeString())

    sampletext.writeAsText("clk1.txt", FileSystem.WriteMode.OVERWRITE)

    env.execute("Window Stream")
  }
}