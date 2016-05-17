package com.bowen.akka

import java.util.List

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.RoundRobinPool
import com.bowen.model.CarInfo
import org.asynchttpclient._
import org.asynchttpclient.proxy.ProxyServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query

import scala.collection.JavaConversions._

/**
  * Created by bcl on 16/5/13.
  */
@SpringBootApplication
class Car extends CommandLineRunner {
    @Autowired
    private val mongoTemplate: MongoTemplate = null

    @Autowired
    private val springContext:ConfigurableApplicationContext = null

    sealed trait CarMessage

    case class Start(carList: List[CarInfo]) extends CarMessage

    case class SavePremiumCalcInfo(data: CarInfo) extends CarMessage

    case class PolicyRecommend() extends CarMessage

    case class PolicyCompare() extends CarMessage

    case class PolicyModify() extends CarMessage

    case class Result(data: AnyRef) extends CarMessage

    val cf: AsyncHttpClientConfig = new DefaultAsyncHttpClientConfig.Builder().setProxyServer(new ProxyServer.Builder("127.0.0.1", 8888)).setRequestTimeout(120*1000).setConnectTimeout(120*1000).build()
    val asyncHttpClient: AsyncHttpClient = new DefaultAsyncHttpClient(cf)

    //    class Worker extends Actor with ActorLogging {
    //        override def receive: Receive = {
    //            case SavePremiumCalcInfo => {
    //                log.info("receive msg")
    //                val current_sender = sender()
    //                val asyncHttpClient: AsyncHttpClient = new DefaultAsyncHttpClient(cf)
    //                val builder: BoundRequestBuilder = asyncHttpClient.preparePost("http://insurance-remote.qa-01.jimubox.com/car/savePremiumCalcInfo.act")
    //                builder.addFormParam("licenseNo", "京YH9386")
    //                builder.addFormParam("carOwnerName", "张贺礼")
    //                builder.addFormParam("registerDate", "2008-11-28")
    //                builder.addFormParam("frameNo", "LFV2A11G283152467")
    //                builder.addFormParam("lastBusinessEnd", "2016-11-28")
    //                builder.addFormParam("phone", "15801303167")
    //                builder.addFormParam("carOwnerIdentifyNumber", "132801196505153618")
    //                builder.addFormParam("engineNo", "043658")
    //                builder.addFormParam("driveDistrict", "110000")
    //                builder.addFormParam("planNo", "1")
    //                builder.execute(new AsyncCompletionHandler[Response]() {
    //                    @throws[Exception]
    //                    def onCompleted(response: Response): Response = {
    //                        current_sender ! Result(response.getResponseBody)
    //                        log.info("finish")
    //                        return response
    //                    }
    //
    //                    override
    //                    def onThrowable(t: Throwable): Unit = {
    //                        t.printStackTrace()
    //                    }
    //                })
    //            }
    //        }
    //    }
    class Worker extends Actor with ActorLogging {
        override def receive: Receive = {
            case SavePremiumCalcInfo(data) => {
                log.info("receive msg,license:" + data.LicenseNo)
                val current_sender = sender()
                val builder: BoundRequestBuilder = asyncHttpClient.preparePost("http://localhost:8080/car/savePremiumCalcInfo.act")
                builder.addFormParam("licenseNo", data.LicenseNo)
                builder.addFormParam("carOwnerName", data.LicenseOwner)
                builder.addFormParam("registerDate", data.RegisterDate)
                builder.addFormParam("frameNo", data.CarVin)
                builder.addFormParam("lastBusinessEnd", data.BusinessExpireDate)
                builder.addFormParam("phone", "15801303167")
                builder.addFormParam("carOwnerIdentifyNumber", data.CredentislasNum)
                builder.addFormParam("engineNo", data.EngineNo)
                builder.addFormParam("driveDistrict", "110000")
                builder.addFormParam("planNo", "1")
                builder.execute(new AsyncCompletionHandler[Response]() {
                    @throws[Exception]
                    def onCompleted(response: Response): Response = {
                        current_sender ! Result(response.getResponseBody)
                        log.info("finish,license:"+data.LicenseNo)
                        return response
                    }

                    override
                    def onThrowable(t: Throwable): Unit = {
                        log.info("finish,license:"+data.LicenseNo)
                        log.error(t.getMessage,t)
                        current_sender ! Result(t.getMessage)
                    }
                })
            }

        }

    }

    class Master(nrOfWorkers: Int) extends Actor with ActorLogging {
        val workerRouter = context.actorOf(
            Props(new Worker()).withRouter(RoundRobinPool(nrOfWorkers)), name = "workerRouter")
        var sampleNumber: Int = 0
        var resultNumber: Int = 0

        override def receive: Receive = {
            case Result(data) => {
                println(data)
                resultNumber += 1
                log.info("receive result")
                if (resultNumber >= sampleNumber) {
                    log.info("finish")
                    context.system.terminate()
                    context.stop(self)
                    springContext.close()
                }
            }
            case Start(carList) => {
                println(carList size)
                sampleNumber = carList.size()
                carList.foreach(car => {
                    workerRouter ! SavePremiumCalcInfo(car)
                })
            }
        }
    }

    override def run(args: String*): Unit = {
        val system = ActorSystem("PiSystem")
        val master = system.actorOf(Props(new Master(4)), name = "master")
        val query: Query = new Query()
        query.limit(3)
        val carList = mongoTemplate.find(query, classOf[CarInfo], "car_info");
        //        println(carList size)
        master ! Start(carList)
    }
}
