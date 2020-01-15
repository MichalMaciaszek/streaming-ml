package streaming.ml.features

import streaming.ml.RawEvent

object Time extends Feature {

  override def size: Int = 24

  override def extract(event: RawEvent): (Int, Double) = {
    val hour = event.data("Timestamp").toLowerCase
    (hour.substring(8,10).toInt, 1.0)
  }
}
