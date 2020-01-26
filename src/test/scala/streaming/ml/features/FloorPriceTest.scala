package streaming.ml.features

import org.scalatest.FunSuite
import streaming.ml.RawEvent

class FloorPriceTest extends FunSuite {

  test("testFloorPriceExtract_1") {
    val sample = RawEvent("f1ec266f9a99c4d7dc56de5523e72c30\t20130611000103446\t1\tZ0THZpN6L6ByeQj\tMozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1\t61.131.48.*\t124\t129\t2\ttrqRTuToMTNUjM9r5rMi\t816aa0d5ae0b172a675569557691f2ed\tnull\t433287550\t468\t60\t1\t0\t5\t612599432d200b093719dd1f372f7a30\t300\t68\tbebefa5efe83beee17a3d245e7c5085b\t1458\t13800,10006,10063,10111")
    val result = FloorPrice.extract(sample)
    val expected = (1,1.0)
    assertResult(expected)(result)
  }

  test("testFloorPriceExtract_2") {
    val sample = RawEvent("bd859649acd561aa30c85c918694a84\t20130606145622025\t1\tZYj7Li96Oc2Igt\tMozilla/5.0 (Windows NT 6.1; rv:21.0) Gecko/20100101 Firefox/21.0\t222.180.64.*\t275\t275\t3\t5F1RQS9rg5scFsf\t56706886d6e5ca308fe07d0c21d409ec\tnull\tNews_F_Width1\t1000\t90\t0\t0\t80\t832b91d59d0cb5731431653204a76c0e\t300\t80\tbebefa5efe83beee17a3d245e7c5085b\t1458\t10077,10024,10083,10006,10079,10057,10059,14273,10075,13403,10063,10052,10110")
    val result = FloorPrice.extract(sample)
    val expected = (3,1.0)
    assertResult(expected)(result)
  }
}