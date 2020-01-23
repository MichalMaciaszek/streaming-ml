package streaming.ml

import org.apache.flink.streaming.api.scala.function.ProcessAllWindowFunction
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow
import org.apache.flink.util.Collector
import streaming.ml.evaluation.{Metrics, Summary}

case class PredictionsEvaluator() extends ProcessAllWindowFunction[(Int, Double), Summary, GlobalWindow] {

  override def process(context: Context, elements: Iterable[(Int, Double)], out: Collector[Summary]): Unit = {
    val metrics = Metrics(elements.map(_._1), elements.map(_._2))
    out.collect(metrics.summary())
  }
}
