package data

import org.apache.flink.api.common.functions.MapFunction

case class FeatureExtractor() extends MapFunction[String, Sample] {
  override def map(value: String): Sample = {
    val values = value.split(" ")
    val label = values(0) == "1"

    val browser = extractBrowser(values(1))
    val os = extractOS(values(1))
    val features = Map(browser -> 1.0)
    val features2 = Map(os + 3 -> 1.0)

    Sample(label, features)
  }

  def extractBrowser(str: String): Int = {
    if (str.contains("chrome")) 0
    else if (str.contains("ie")) 1
    else 2
  }

  def extractOS(str: String): Int = {
    if (str.contains("win")) 0
    else if (str.contains("mac")) 1
    else 2
  }
}