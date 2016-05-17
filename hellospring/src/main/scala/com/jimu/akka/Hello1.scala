package com.jimu.akka

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by bcl on 16/5/10.
  */
object Hello1 extends App {
    val system = ActorSystem("actor-demo-scala")
    val hello = system.actorOf(Props[Hello])
    hello ! "Bob"
    Thread sleep 1000
    system terminate

    class Hello extends Actor {
        def receive = {
            case name: String => println(s"Hello $name")
        }
    }

}
