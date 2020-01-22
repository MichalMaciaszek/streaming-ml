package streaming.ml.features

import streaming.ml.RawEvent

object Day extends Feature {

  override def size: Int = 4

  override def extract(event: RawEvent): (Int, Double) = {
    val hour = event.data("Timestamp").toLowerCase
    val pd = hour.substring(8,10).toInt % 6
    val index = {
        if (pd >= 0 && pd < 6) 0
        else if (pd >= 6 && pd < 12) 1
        else if (pd >= 12 && pd < 18) 2
        else 3
    }
    (index, 1.0)
  }
}