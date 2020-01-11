package streaming.ml.features

import streaming.ml.RawEvent

trait Feature extends Serializable {

  def size: Int

  def extract(event: RawEvent): (Int, Double)

  def extract(event: RawEvent, offset: Int): (Int, Double) = {
    val iv = extract(event)
    (iv._1 + offset, iv._2)
  }

}
