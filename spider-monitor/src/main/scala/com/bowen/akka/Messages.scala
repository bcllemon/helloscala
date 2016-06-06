package com.bowen.akka

import com.bowen.util.CarInfo
import com.jimubox.engine.model.{CalcPremiumResult, PolicyInfoResult}

/**
  * Created by bcl on 16/5/25.
  */
object Messages {
    sealed trait  SpiderMessage
    case class StartJob() extends SpiderMessage
    case class TimerJob(timeout:Long) extends SpiderMessage
    case class QueryJob(carInfo:CarInfo, isRe:Boolean, companyId:Int) extends SpiderMessage
    case class CalcJob(carInfo: CarInfo, companyId:Int)
    case class QueryResult(result:PolicyInfoResult,companyId:Int)
    case class CalcResult(result:CalcPremiumResult,companyId:Int)
}
