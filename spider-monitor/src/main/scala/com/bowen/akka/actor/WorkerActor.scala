package com.bowen.akka.actor

import akka.actor.{Actor, ActorLogging}
import com.bowen.akka.Messages.QueryJob

/**
  * Created by bcl on 16/5/25.
  */
class WorkerActor extends Actor with ActorLogging{
    override def receive: Receive = {
        case QueryJob(carInfo,isRe,companyId)=>{
            
        }
    }
}
