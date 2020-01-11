package streaming.ml.features

import streaming.ml.RawEvent

case class HashingFeature(columnName: String,
                          override val size: Int
                         ) extends Feature {

  override def extract(event: RawEvent): (Int, Double) = (math.abs(event.data(columnName).hashCode) % size, 1.0)
}
