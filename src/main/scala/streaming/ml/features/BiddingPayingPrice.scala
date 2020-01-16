package streaming.ml.features

import streaming.ml.RawEvent

case class BiddingPayingPrice(columnName: String) extends Feature {

  private val PRICES = Vector(0, 100, 200, 400, 700, 1000)

  override def size: Int = PRICES.size + 1

  override def extract(event: RawEvent): (Int, Double) = {
    val price = event.data(columnName).toLowerCase.toInt
    val index = {
      if (price == PRICES(0)) 0 // [0]
      else if (price > PRICES(0) && price < PRICES(1)) 1 // [1-100]
      else if (price >= PRICES(1) && price < PRICES(2)) 2 // [101-200]
      else if (price >= PRICES(2) && price <= PRICES(3)) 3 // [201-400]
      else if (price >= PRICES(3) && price <= PRICES(4)) 4  // [401-700]
      else if (price >= PRICES(4) && price <= PRICES(5)) 5 // [701-1000]
      else 6 // [>1000]
    }
    (index, 1.0)
  }
}