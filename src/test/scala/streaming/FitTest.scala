import streaming.ml.{LogisticRegression, Sample}
import streaming.ml.features.FeatureExtractor
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class FitTest extends org.scalatest.FunSuite{

  test("test_fit") {
    //data to Samples
    val lines = io.Source.fromFile("C:/Users/kasia/IdeaProjects/streaming-ml-master/src/main/resources/advertiser.1458-test.txt")
      .getLines().take(100000).toList
    var samples = ListBuffer[Sample]()
    val fe = FeatureExtractor()
    for (line <- lines) samples += fe.map(line)
    val samplesList = samples.toList

    //fit model
    val lr = LogisticRegression()
    var model: mutable.HashMap[Int, Double] = mutable.HashMap.empty[Int, Double]
    for (line <- samplesList) model = lr.fit(model, line.featureVector, line.label)

    //test
    var non_0 = 0
    for (element <- model) if (element._2 != 0) non_0 += 1
    assert(non_0 == model.size)
  }
}

