package streaming.ml.features

import streaming.ml.RawEvent

object Day extends Feature {

  override def size: Int = 4

  override def extract(event: RawEvent): (Int, Double) = {
    val hour = event.data("Timestamp").toLowerCase
    (hour.substring(8,10).toInt % 6,1.0)
  }
}
