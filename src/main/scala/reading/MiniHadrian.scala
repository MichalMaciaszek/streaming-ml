import java.io.{FileInputStream, FileOutputStream}

import com.opendatagroup.hadrian.jvmcompiler.PFAEngine

object MiniHadrian {
  def main(args: Array[String]) {
    val engine = PFAEngine.fromJson(new FileInputStream("to_deploy")).head

    engine.callGraph("(action)").foreach(println)

    val inputData = engine.jsonInputIterator(new FileInputStream("data3.json"))
    var output: Any = 1
    while (inputData.hasNext) {
      output = engine.action(inputData.next())
      println(output)
    }

  }
}