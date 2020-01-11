package streaming.ml

case class RawEvent(data: Map[String, String])

object RawEvent {

  val SCHEMA: Vector[String] = Vector(
    "BidId", "Timestamp", "LogType", "iPinYouID", "UserAgent", "IP", "Region",
    "City", "AdExchange", "Domain", "URL", "AnonymousUrlId", "AdSlotId",
    "AdSlotWidth", "AdSlotHeight", "AdSlotVisibility", "AdSlotFormat",
    "AdSlotFloorPrice", "CreativeId", "BiddingPrice", "PayingPrice",
    "KeyPageUrl", "AdvertiserId", "UserTags"
  )

  def apply(record: String): RawEvent = new RawEvent(SCHEMA.zip(record.split("\\t")).toMap)
}
