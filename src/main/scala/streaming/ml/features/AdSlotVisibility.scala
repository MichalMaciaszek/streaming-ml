package streaming.ml.features

import streaming.ml.RawEvent

object AdSlotVisibility extends Feature {

  private val VISIBILITIES = Vector("0", "1", "2")

  override def size: Int = VISIBILITIES.size

  override def extract(event: RawEvent): (Int, Double) = {
    val vs = event.data("AdSlotVisibility").toLowerCase
    (VISIBILITIES.map(vs.contains).zipWithIndex.filter(_._1).map(_._2).headOption.getOrElse(size), 1.0)
  }
}
