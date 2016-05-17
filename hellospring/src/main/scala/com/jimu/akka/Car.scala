package com.jimu.akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import org.asynchttpclient._
import org.asynchttpclient.proxy.ProxyServer

/**
  * Created by bcl on 16/5/13.
  */
object Car extends App {

    sealed trait CarMessage

    case class SavePremiumCalcInfo() extends CarMessage

    case class PolicyRecommend() extends CarMessage

    case class PolicyCompare() extends CarMessage

    case class PolicyModify() extends CarMessage

    case class Result(data: AnyRef) extends CarMessage

    val cf: AsyncHttpClientConfig = new DefaultAsyncHttpClientConfig.Builder().setProxyServer(new ProxyServer.Builder("127.0.0.1", 8888)).build
    class Worker extends Actor with ActorLogging {
        override def receive: Receive = {
            case SavePremiumCalcInfo => {
                log.info("receive msg")
                val current_sender = sender()
                val asyncHttpClient: AsyncHttpClient = new DefaultAsyncHttpClient(cf)
                val builder: BoundRequestBuilder = asyncHttpClient.preparePost("http://insurance-remote.qa-01.jimubox.com/car/savePremiumCalcInfo.act")
                builder.addFormParam("licenseNo", "京YH9386")
                builder.addFormParam("carOwnerName", "张贺礼")
                builder.addFormParam("registerDate", "2008-11-28")
                builder.addFormParam("frameNo", "LFV2A11G283152467")
                builder.addFormParam("lastBusinessEnd", "2016-11-28")
                builder.addFormParam("phone", "15801303167")
                builder.addFormParam("carOwnerIdentifyNumber", "132801196505153618")
                builder.addFormParam("engineNo", "043658")
                builder.addFormParam("driveDistrict", "110000")
                builder.addFormParam("planNo", "1")
                builder.execute(new AsyncCompletionHandler[Response]() {
                    @throws[Exception]
                    def onCompleted(response: Response): Response = {
                        current_sender ! Result(response.getResponseBody)
                        log.info("finish")
                        return response
                    }

                    override
                    def onThrowable(t: Throwable): Unit = {
                        t.printStackTrace()
                    }
                })
            }
        }
    }

    class Master extends Actor with ActorLogging{
        val workerRouter = context.actorOf(Props[Worker], name = "listener")

        override def receive: Receive = {
            case Result(data) => println(data)
            case SavePremiumCalcInfo => {
                log.info("receive msg")
                workerRouter ! SavePremiumCalcInfo
            }
        }
    }

    val system = ActorSystem("PiSystem")
    val master = system.actorOf(Props[Master])
    master ! SavePremiumCalcInfo

}
