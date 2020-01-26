import streaming.ml.evaluation.Metrics
import streaming.ml.{LogisticRegression, Sample}
import streaming.ml.features.FeatureExtractor
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class PredictTest extends org.scalatest.FunSuite{
  test("test_predict") {
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

    //predict
    var predictions = ListBuffer[Double]()
    for (line <- samplesList) predictions += lr.predict(model, line.featureVector)
    val predictionsList = predictions.toList

    //test
    val metrics = Metrics(samplesList.map(_.label), predictionsList).auroc()
    assert( 0.5 < metrics && metrics <= 1.0 )
  }
}
