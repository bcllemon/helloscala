package com.bowen.util

import java.lang.reflect.Type
import java.util.Map

import com.jimubox.insurance.car.skeleton.{Car, RiskKind}
import com.jimubox.third.spider.SpiderUtil
import org.apache.commons.io.{Charsets, IOUtils}
import org.apache.commons.lang3.StringUtils
import java.math.BigDecimal
import scala.collection.JavaConversions._
/**
  * Created by bcl on 16/5/20.
  */
object DataUtil {
    var carData:Map[String,CarInfo] = _
    var riskKindData:java.util.List[java.util.List[RiskKind]] = _
    private val carInfoType: Type = new com.google.gson.reflect.TypeToken[java.util.Map[String,CarInfo]]() {}.getType
    private val riskKindType: Type = new com.google.gson.reflect.TypeToken[java.util.List[java.util.List[RiskKind]]]() {}.getType
    private def initData(): Unit ={
        if(carData!=null){
            return
        }
        var data = StringUtils.join(IOUtils.readLines(this.getClass.getResourceAsStream("/car_info.json"), Charsets.UTF_8),"")
        carData = SpiderUtil.gson.fromJson(data,carInfoType)
        data = StringUtils.join(IOUtils.readLines(this.getClass.getResourceAsStream("/premiumCalculator.json"), Charsets.UTF_8),"")
        riskKindData = SpiderUtil.gson.fromJson(data,riskKindType)
    }
    def getCarInfo(license:String):CarInfo={
        initData()
        return carData.get(license)
    }
    def getRiskKind(sequence: Int): java.util.List[RiskKind] ={
        initData()
        return riskKindData.get(sequence)
    }
    def getCar(license:String):Car = {
        val carInfo  = this.getCarInfo(license)
        if(carInfo==null){
            return null
        }
        val car = new Car
        car.setEngineNo(carInfo.EngineNo)
        car.setRegisterDate(carInfo.RegisterDate)
        car.setPurchasePrice(carInfo.PurchasePrice)
        car.setCarOwnerName(carInfo.LicenseOwner)
        car.setFrameNo(carInfo.CarVin)
        car.setLicenseNo(carInfo.LicenseNo)
        car.setCarOwnerIdentifyNumber(carInfo.CredentislasNum)
        return car
    }
    def checkRiskKinds(source:java.util.List[RiskKind],target:java.util.List[RiskKind]):Boolean={
        val resultMap = target.filter(p => p.getKindCode != 11).map(p => p.getKindCode).toSet
        val sourceMap = source.filter(p => p.getKindCode != 11).map(p => p.getKindCode).toSet
        println(sourceMap.mkString(","))
        println(resultMap.mkString(","))
        return (sourceMap -- resultMap).size==0
    }

}
class CarInfo{
    var CredentislasNum: String = null
    var LicenseNo: String = null
    var CityCode: String = null
    var CarVin: String = null
    var ModleName: String = null
    var IdType: String = null
    var InsuredName: String = null
    var BusinessExpireDate: String = null
    var ForceExpireDate: String = null
    var CarUsedType: String = null
    var Source: String = null
    var PurchasePrice: BigDecimal = null
    var EngineNo: String = null
    var NextBusinessStartDate: String = null
    var PostedName: String = null
    var RegisterDate: String = null
    var NextForceStartDate: String = null
    var LicenseOwner: String = null
    var SeatCount: String = null
}
