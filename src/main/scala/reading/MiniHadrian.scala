import java.io.FileInputStream

import com.opendatagroup.hadrian.jvmcompiler.PFAEngine
import reading.PFAEstimator
import streaming.ml.Sample
import streaming.ml.features.FeatureExtractor

import scala.collection.mutable.ListBuffer
import scala.io.Source

object MiniHadrian {
  def main(args: Array[String]) {


    val Est = PFAEstimator(PFAEngine.fromJson(new FileInputStream("to_deploy")).head, 0)
    val lines = Source.fromFile("advertiser.2997-test.txt")
      .getLines().take(1).toList
    var samples = ListBuffer[Sample]()
    val fe = FeatureExtractor()
    for (line <- lines) samples += fe.map(line)
    val samplesList = samples.toList
    val sample = samplesList(0)
    val features = sample.featureVector

    //print(Est.fit(Est, features, 1))
    print(Est.predict(Est, features))

  }
}