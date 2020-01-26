package streaming.ml.features

import streaming.ml.RawEvent

class BrowserFeatureTest extends org.scalatest.FunSuite {

  test("testBrowserExtract_1") {
    val sample = RawEvent("f1ec266f9a99c4d7dc56de5523e72c30\t20130611000103446\t1\tZ0THZpN6L6ByeQj\tMozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1\t61.131.48.*\t124\t129\t2\ttrqRTuToMTNUjM9r5rMi\t816aa0d5ae0b172a675569557691f2ed\tnull\t433287550\t468\t60\t1\t0\t5\t612599432d200b093719dd1f372f7a30\t300\t68\tbebefa5efe83beee17a3d245e7c5085b\t1458\t13800,10006,10063,10111")
    val result = BrowserFeature.extract(sample)
    val expected = (0,1.0)
    assertResult(expected)(result)
  }

  test("testBrowserExtract_2") {
    val sample = RawEvent("4b139ee5036e219a35d00ce01c231983\t20130611000103972\t1\tVh5_ZAnR3U16wMn\tMozilla/5.0 (Windows NT 6.1; rv:21.0) Gecko/20100101 Firefox/21.0\t123.193.205.*\t393\t393\t1\tk1xjTv1cQoq81m58uG\tacbea646c75fcda7907b42a9f9a4b81f\tnull\tmm_30734321_2787575_10288767\t336\t280\t255\t1\t0\t77819d3e0b3467fe5c7b16d68ad923a1\t300\t40\tbebefa5efe83beee17a3d245e7c5085b\t1458\tnull")
    val result = BrowserFeature.extract(sample)
    val expected = (4,1.0)
    assertResult(expected)(result)
  }
}