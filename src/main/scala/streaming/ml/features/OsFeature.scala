package streaming.ml.features

import streaming.ml.RawEvent

object OsFeature extends Feature {

  private val BROWSERS = Vector("windows", "ios", "mac", "android", "linux")

  override def size: Int = BROWSERS.size

  override def extract(event: RawEvent): (Int, Double) = {
    val ua = event.data("UserAgent").toLowerCase
    (BROWSERS.map(ua.contains).zipWithIndex.filter(_._1).map(_._2).headOption.getOrElse(size), 1.0)
  }
}
