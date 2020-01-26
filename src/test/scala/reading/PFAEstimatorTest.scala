package reading

import java.io.InputStream

import com.opendatagroup.hadrian.jvmcompiler.PFAEngine
import org.scalatest.FunSuite

class PFAEstimatorTest extends FunSuite {

  test("testPredict") {
    val stream: InputStream = getClass.getResourceAsStream("/model.json")
    val Est = try {
      PFAEstimator(PFAEngine.fromJson(stream).head, 13366)
    } finally stream.close()
    val features = Map(12344 -> 1.0, 11256 -> 1.0, 25 -> 1.0, 7733 -> 1.0, 12347 -> 1.0,
      12282 -> 1.0, 765 -> 1.0, 64 -> 1.0, 12182 -> 1.0, 35 -> 1.0, 12360 -> 1.0,
      12355 -> 1.0, 12685 -> 1.0, 36 -> 1.0, 19 -> 1.0
    )
    val result = Est.predict(Est, features)

    assert(result == 0.0)
  }

}
