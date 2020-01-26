package streaming.ml.features

import org.scalatest.FunSuite
import streaming.ml.RawEvent

class OsFeatureTest extends FunSuite {

  test("testOSExtract_1") {
    val sample = RawEvent("f1ec266f9a99c4d7dc56de5523e72c30\t20130611000103446\t1\tZ0THZpN6L6ByeQj\tMozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1\t61.131.48.*\t124\t129\t2\ttrqRTuToMTNUjM9r5rMi\t816aa0d5ae0b172a675569557691f2ed\tnull\t433287550\t468\t60\t1\t0\t5\t612599432d200b093719dd1f372f7a30\t300\t68\tbebefa5efe83beee17a3d245e7c5085b\t1458\t13800,10006,10063,10111")
    val result = OsFeature.extract(sample)
    val expected = (0,1.0)
    assertResult(expected)(result)
  }

  test("testOSExtract_2") {
    val sample = RawEvent("50e5c2379c504a0f7736904ca0fe432e\t20130606150314641\t1\tVhk7PJd6DeTHJsa\tMozilla/5.0 (iPad; CPU OS 6_0_1 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10A8426\t60.211.177.*\t146\t154\t1\ttrqRTJdmBqT71m58uG\t627a620597f8108918e1e629de1bc2c9\tnull\tmm_10012625_2411961_9431518\t728\t90\t1\t1\t0\t48f2e9ba15708c0146bda5e1dd653caa\t300\t57\tbebefa5efe83beee17a3d245e7c5085b\t1458\tnull")
    val result = OsFeature.extract(sample)
    val expected = (2,1.0)
    assertResult(expected)(result)
  }
}