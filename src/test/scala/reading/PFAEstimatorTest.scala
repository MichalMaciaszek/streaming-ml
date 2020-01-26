package reading

import java.io.{File, FileInputStream, PrintWriter}

import com.opendatagroup.hadrian.jvmcompiler.PFAEngine
import org.scalatest.FunSuite
import streaming.ml.Sample
import streaming.ml.features.FeatureExtractor

import scala.collection.mutable.ListBuffer
import scala.io.Source

class PFAEstimatorTest extends FunSuite {

//  test("testEngine") {
//
//  }

  test("testPredict") {
    val Est = PFAEstimator(PFAEngine.fromJson(new FileInputStream("to_deploy")).head)
    val lines = Source.fromFile("advertiser.2997-test.txt")
      .getLines().take(1).toList
    var samples = ListBuffer[Sample]()
    val fe = FeatureExtractor()
    for (line <- lines) samples += fe.map(line)
    val samplesList = samples.toList
    val sample = samplesList(0)
    val features = sample.featureVector
    val result = (Est.predict(Est, features))

   // val result = Est.predict(sample)
//    val dropped = result.filter(_ == 1.0)
//    val dropped2 = dropped.filter(_ == 0.0)
    //assert(dropped2.isEmpty)
  assert(result == 0.0)
  }

}
