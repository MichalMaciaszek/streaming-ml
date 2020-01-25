import java.io.{File, FileInputStream, FileOutputStream, PrintWriter}

import com.opendatagroup.hadrian.jvmcompiler.PFAEngine
import org.apache.flink.core.fs.FileSystem
import org.apache.spark
import reading.PFAEstimator
import streaming.ml.features.FeatureExtractor

object MiniHadrian {
  def main(args: Array[String]) {


    val Est = PFAEstimator()

    var a = Vector.fill(13366)(0.0)
    val start = """{"x":["""
    val end = """]}"""
    val json = a.mkString(start, ",", end)

    val pw = new PrintWriter(new File("file.json"))
    pw.write(json)
    pw.close

    Est.predict("file.json")

  }
}