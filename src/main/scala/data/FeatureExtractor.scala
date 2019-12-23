package data

import org.apache.flink.api.common.functions.MapFunction

import scala.collection.mutable.ListBuffer

import java.lang.Integer

case class FeatureExtractor() extends MapFunction[String, Sample] {

  override def map(value: String): Sample = {
    val atributeList = value.split("\\t").toBuffer
    val label = false // dorobić odpowiednie LABELe
    val features = ListBuffer[Map[Int, Int]]()

    // wyrzucamy niepotrzebne rzeczy
    atributeList.remove(23,1)
    atributeList.remove(21,1)
    atributeList.remove(18,1)
    atributeList.remove(8,1)
    atributeList.remove(3,1)
    atributeList.remove(0,1)

    // wyciągamy przeglądarke i os
    val browser = extractBrowser(atributeList(2))
    val os = extractOS(atributeList(2))

    // robimy atyrbuty
    val temp = ListBuffer[Int]()

    if(atributeList(0) != "null") { // nie chce się timestamp przerobić na Int
      //temp += atributeList(0).toInt
    }

    if(atributeList(1) != "null") {
      temp += atributeList(1).toInt
    }

    temp += browser
    temp += os

    if(atributeList(4) != "null") {
      val f4 = atributeList(4).hashCode
      temp += f4.toInt
    }

    if(atributeList(5) != "null") {
      temp += atributeList(5).toInt
    }

    if(atributeList(6) != "null") {
      temp += atributeList(6).toInt
    }

    if(atributeList(7) != "null") {
      val f7 = atributeList(7).hashCode
      temp += f7.toInt
    }

    if(atributeList(8) != "null") {
      val f8 = atributeList(8).hashCode
      temp += f8.toInt
    }

    if(atributeList(9) != "null"){
      val f9 = atributeList(9).hashCode
      temp += f9.toInt
    }

    if(atributeList(10) != "null") {
      val f10 = atributeList(10).hashCode
      temp += f10.toInt
    }

    if(atributeList(11) != "null") {
      temp += atributeList(11).toInt
    }

    if(atributeList(12) != "null") {
      temp += atributeList(12).toInt
    }

    if(atributeList(13) != "null") {
      temp += atributeList(13).toInt
    }

    if(atributeList(14) != "null") {
      temp += atributeList(14).toInt
    }

    if(atributeList(15) != "null") {
      temp += atributeList(15).toInt
    }

    if(atributeList(16) != "null") {
      temp += atributeList(16).toInt
    }

    if(atributeList(17) != "null") {
      temp += atributeList(17).toInt
    }

    val tmp = ListBuffer[String]()
    temp.foreach { x =>
      tmp += Integer.toBinaryString(x)
    }

    // mapowanie
    var len = 0
    tmp.foreach{ y =>
      y.foreach { x =>
        if (x == '1') {
          features += Map(tmp.indexOf(x) + len -> 1)
        }
      }
      len += y.length
    }

    Sample(label, features)
  }

  def extractBrowser(str: String): Int = {
    if (str.contains("Mozilla")) 0
    else if (str.contains("MQQ")) 1
    else if (str.contains("Safari")) 2
    else if (str.contains("Chrome")) 3
    else 4
  }

  def extractOS(str: String): Int = {
    if (str.contains("Windows")) 0
    else if (str.contains("Mac")) 1
    else if (str.contains("Linux")) 2
    else 3
  }

}