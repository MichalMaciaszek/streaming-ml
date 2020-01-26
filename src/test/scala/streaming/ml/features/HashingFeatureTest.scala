package streaming.ml.features

import org.scalatest.FunSuite
import streaming.ml.RawEvent

import scala.collection.mutable.ListBuffer

class HashingFeatureTest extends FunSuite {

  test("testHashingExtract") {
    val hf = HashingFeature("BidId",10000)
    val set = ListBuffer[Int]()
    for(x <- 1 to 100) {
      val re = RawEvent(Map("BidId" -> java.util.UUID.randomUUID().toString))
      set += hf.extract(re)._1
    }
    val result = set.distinct.length
    assert(result >= 95)
  }

}