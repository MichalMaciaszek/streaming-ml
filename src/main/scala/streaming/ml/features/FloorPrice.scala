package streaming.ml.features

import streaming.ml.RawEvent

object FloorPrice extends Feature {

  private val PRICES = Vector(0, 11, 51, 100)

  override def size: Int = PRICES.size + 1

  override def extract(event: RawEvent): (Int, Double) = {
    val price = event.data("AdSlotFloorPrice").toLowerCase.toInt
    val index = {
        if (price == PRICES(0)) 0 // [0]
        else if (price > PRICES(0) && price < PRICES(1)) 1 // [1-10]
        else if (price >= PRICES(1) && price < PRICES(2)) 2 // [11-50]
        else if (price >= PRICES(2) && price <= PRICES(3)) 3 // [51-100]
        else 3 // [>100]
    }
    (index, 1.0)
  }
}