package streaming.ml.features

import org.apache.flink.api.common.functions.MapFunction
import streaming.ml.{RawEvent, Sample}

case class FeatureExtractor() extends MapFunction[String, Sample] {

  val definitions: List[Feature] = List(
    Time, Day, BrowserFeature, OsFeature, HashingFeature("Region", 100), 
    HashingFeature("City", 1000), AdSlotFormat, AdSlotVisibility)

  lazy val featureOffsets: List[(Feature, Int)] = definitions.zip(
    definitions.dropRight(1).foldLeft(List(0))((offsets, f) => offsets :+ (offsets.last + f.size))
  )

  override def map(value: String): Sample = {
    val event = RawEvent(value)

    val featureValues = featureOffsets.map(f => f._1.extract(event, f._2)).toMap

    Sample(if (event.data("LogType").toInt > 1) 1 else 0, featureValues)
  }
}