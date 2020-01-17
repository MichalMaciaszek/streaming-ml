package streaming.ml.features

import streaming.ml.RawEvent

object AdSlotFormat extends Feature {

  private val FORMATS = Vector("0", "1", "2")

  override def size: Int = FORMATS.size + 1

  override def extract(event: RawEvent): (Int, Double) = {
    val format = event.data("AdSlotFormat").toLowerCase
    (FORMATS.map(format.contains).zipWithIndex.filter(_._1).map(_._2).headOption.getOrElse(size), 1.0)
  }
}
