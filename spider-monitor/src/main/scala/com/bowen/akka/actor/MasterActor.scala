package com.bowen.akka.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.routing.RoundRobinPool
import com.bowen.akka.Messages.{StartJob, TimerJob}

/**
  * Created by bcl on 16/5/25.
  */
class MasterActor(timerActor: ActorRef) extends Actor with ActorLogging {
    val workerRouter = context.actorOf(
        Props(new WorkerActor()).withRouter(RoundRobinPool(4)), name = "workerRouter")

    override def receive: Receive = {
        case StartJob() => {
            log.info("start job")
            timerActor ! TimerJob(20 * 1000)
        }
    }
}
