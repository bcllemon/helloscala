package com.bowen.akka.actor

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorLogging, ActorSystem, Scheduler}
import com.bowen.akka.Messages.{StartJob, TimerJob}

import scala.concurrent.duration._

/**
  * Created by bcl on 16/5/25.
  */
class TimerActor(system: ActorSystem) extends Actor with ActorLogging {

    val masterActor = context.actorSelection("akka://SpiderMonitor/user/master")
    import system.dispatcher
    override def receive: Receive = {
        case TimerJob(timeout) => {
            log.info(s"timer job ${timeout}")

            system.scheduler.scheduleOnce(new FiniteDuration(timeout, TimeUnit.MILLISECONDS),new Runnable {
                override def run(): Unit = {
                    masterActor ! StartJob()
                }
            })
        }
    }
}
