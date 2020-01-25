package streaming.ml.features

import org.scalatest.FunSuite
import streaming.ml.RawEvent

class HashingFeatureTest extends FunSuite {

  test("testHashingExtract") {
    val sample = RawEvent("4acf9f34b3d6f487b8a9294fc6091743\t20131023164806000\t1\tDAJI6lAbzHq\t\"Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; HTC One Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30\"\t124.164.238.*\t15\t20\tnull\t1c0a6c3e4ec60739f2e574616fd25c43\t800184b4781c886da19467797857c708\tnull\t555493003\t320\t50\tOtherView\tNa\t5\t11908\t277\t5\tnull\t2997\tnull")
    val result = HashingFeature("AdSlotWidth",100).extract(sample)
    val expected = (("320".hashCode) % 100,1.0)
    assertResult(expected)(result)
  }

}
