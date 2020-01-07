package com.aisiot.bigdata.flinkcore

import org.apache.flink.api.common.functions.MapFunction

case class MakeString() extends MapFunction[Sample, String] {

  override def map(value: Sample): String = {
    val label = value.etykieta
    val indexOf1 = value.featureVector
    var str = ""

    val keyes = indexOf1.flatMap(_.keys)

    str += label
    keyes.foreach({ x =>
      val temp = " " + x + ":1"
      str += temp
    })

    str
  }
}
