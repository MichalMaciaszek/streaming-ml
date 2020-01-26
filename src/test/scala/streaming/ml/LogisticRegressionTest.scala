package streaming.ml

import java.io.InputStream

import org.scalatest.{BeforeAndAfterEach, FunSuite}
import streaming.ml.evaluation.Metrics

import scala.collection.mutable

class LogisticRegressionTest extends FunSuite with BeforeAndAfterEach {

  private val lr = LogisticRegression()
  private var model: mutable.HashMap[Int, Double] = mutable.HashMap.empty
  private var samples: Array[Sample] = Array.empty

  override def beforeEach() {
    // read test samples
    val stream: InputStream = getClass.getResourceAsStream("/advertiser.1458-test-sample.libsvm")
    samples = try scala.io.Source.fromInputStream(stream).getLines.map(Sample(_)).toArray finally stream.close()
    // fit model
    for (s <- samples) model = lr.fit(model, s.featureVector, s.label)
  }

  test("testPredict") {
    // predict
    val predictions = samples.map(s => lr.predict(model, s.featureVector))

    //test
    val metrics = Metrics(samples.map(_.label), predictions).auroc()
    assert(0.6 < metrics && 1.0 > metrics)
  }

  test("testFit") {
    // count non-zero features
    val result = model.count(_._2 != 0.0)
    val expected = samples.map(_.featureVector.keys.toSet).reduce(_ ++ _).size
    assertResult(expected)(result)
  }

}
