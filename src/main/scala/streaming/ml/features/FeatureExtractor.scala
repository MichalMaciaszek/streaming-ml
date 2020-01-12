package streaming.ml.features

import org.apache.flink.api.common.functions.MapFunction
import streaming.ml.{RawEvent, Sample}

case class FeatureExtractor() extends MapFunction[String, Sample] {

  val definitions: List[Feature] = List(
    BrowserFeature, OsFeature, HashingFeature("Region", 100), HashingFeature("City", 1000))

  lazy val featureOffsets: List[(Feature, Int)] = definitions.zip(
    definitions.dropRight(1).foldLeft(List(0))((offsets, f) => offsets :+ (offsets.last + f.size))
  )

  override def map(value: String): Sample = {
    val event = RawEvent(value)

    val featureValues = featureOffsets.map(f => f._1.extract(event, f._2)).toMap

    Sample(if (event.data("LogType").toInt > 1) 1 else 0, featureValues)
    //    val atributeList = value.split("\\t").toBuffer
    //    var features = ListBuffer[Map[Int, Char]]()
    //
    //    val five = "00000"
    //    val fiveteen = "000000000000000"
    //    val hundred = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
    //
    //    // wyrzucamy niepotrzebne rzeczy
    //    atributeList.remove(23,1)
    //    atributeList.remove(21,1)
    //    atributeList.remove(18,1)
    //    atributeList.remove(8,1)
    //    atributeList.remove(5,1)
    //    atributeList.remove(3,1)
    //    atributeList.remove(0,1)
    //
    //    // wyciągamy przeglądarke i os
    //    val browser = extractBrowser(atributeList(2))
    //    val os = extractOS(atributeList(2))
    //
    //
    //    var tmp_str = ""
    //
    //    if(atributeList(0) != "null") { // nie chce się timestamp przerobić na Int
    //      //temp += 0
    //      val timestamp = atributeList(0).toLong
    //      var temp = Integer.toBinaryString(timestamp.toInt)
    //      for(x <- (temp.length()) until 100){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = hundred + tmp_str
    //
    //    if(atributeList(1) != "null") { // log type
    //      val log_type = atributeList(1).toInt
    //      var temp = Integer.toBinaryString(log_type)
    //      for(x <- (temp.length()) until 5){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = five + tmp_str
    //
    //    var tempbr = Integer.toBinaryString(browser) // browser
    //    for(x <- (tempbr.length()) until 15){
    //      tempbr = '0' + tempbr
    //    }
    //    tmp_str = tempbr + tmp_str
    //
    //    var tempos = Integer.toBinaryString(os) // os
    //    for(x <- (tempos.length()) until 15){
    //      tempos = '0' + tempos
    //    }
    //    tmp_str = tempos + tmp_str
    //
    //    if(atributeList(3) != "null") { // region
    //      val region = atributeList(3).toInt
    //      var temp = Integer.toBinaryString(region)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(4) != "null") { // city
    //      val city = atributeList(4).toInt
    //      var temp = Integer.toBinaryString(city)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(5) != "null") { // domain
    //      val f5 = atributeList(5).hashCode
    //      val domain = f5.toInt
    //      var temp = Integer.toBinaryString(domain)
    //      for(x <- (temp.length()) until 100){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = hundred + tmp_str
    //
    //    if(atributeList(6) != "null") { // url
    //      val f6 = atributeList(6).hashCode
    //      val url = f6.toInt
    //      var temp = Integer.toBinaryString(url)
    //      for(x <- (temp.length()) until 100){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = hundred + tmp_str
    //
    //    if(atributeList(7) != "null") { // anonymous url
    //      val f7 = atributeList(7).hashCode
    //      val an_url = f7.toInt
    //      var temp = Integer.toBinaryString(an_url)
    //      for(x <- (temp.length()) until 100){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = hundred + tmp_str
    //
    //    if(atributeList(8) != "null") { // ad slot id
    //      val f8 = atributeList(8).hashCode
    //      val slot_id = f8.toInt
    //      var temp = Integer.toBinaryString(slot_id)
    //      for(x <- (temp.length()) until 100){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = hundred + tmp_str
    //
    //    if(atributeList(9) != "null"){ // ad slot width
    //      val slot_width = atributeList(9).toInt
    //      var temp = Integer.toBinaryString(slot_width)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(10) != "null") { // ad slot height
    //      val slot_height = atributeList(10).toInt
    //      var temp = Integer.toBinaryString(slot_height)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(11) != "null") { // ad slot visibility
    //      val slot_vis = atributeList(11).toInt
    //      var temp = Integer.toBinaryString(slot_vis)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(12) != "null") { //  ad slot format
    //      val slot_format = atributeList(12).toInt
    //      var temp = Integer.toBinaryString(slot_format)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(13) != "null") { // ad slot florr price
    //      val slot_floor_price = atributeList(13).toInt
    //      var temp = Integer.toBinaryString(slot_floor_price)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(14) != "null") { // bidding price
    //      val bid_price = atributeList(14).toInt
    //      var temp = Integer.toBinaryString(bid_price)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(15) != "null") { // paying price
    //      val pay_price = atributeList(15).toInt
    //      var temp = Integer.toBinaryString(pay_price)
    //      for(x <- (temp.length()) until 15){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else tmp_str = fiveteen + tmp_str
    //
    //    if(atributeList(16) != "null") { // advertiser id
    //      val adv_id = atributeList(16).toInt
    //      var temp = Integer.toBinaryString(adv_id)
    //      for(x <- (temp.length()) until 30){
    //        temp = '0' + temp
    //      }
    //      tmp_str = temp + tmp_str
    //    } else {
    //      tmp_str = fiveteen + tmp_str
    //      tmp_str = fiveteen + tmp_str
    //    }
    //
    //    val reverseStr = tmp_str.reverse
    //
    //    var lastindex = 0
    //    reverseStr.foreach({ x =>
    //      if(x == '1'){
    //        val y = reverseStr.indexOf(x, lastindex)
    //        features += Map(y -> '1')
    //        lastindex = y + 1
    //      }
    //    })
    //
    //    Sample(logtype == "1", features.)
  }


  def extractBrowser(str: String): Int = {
    if (str.contains("MQQ")) 0
    else if (str.contains("Safari")) 1
    else if (str.contains("Chrome")) 2
    else if (str.contains("Mozilla")) 3
    else 4
  }

  def extractOS(str: String): Int = {
    if (str.contains("Mac")) 0
    else if (str.contains("Linux")) 1
    else if (str.contains("Windows")) 2
    else 3
  }

}