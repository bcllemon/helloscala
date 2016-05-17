package com.jimu.akka

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by bcl on 16/5/10.
  */
object Hello2 extends App{

    case class Greeting(greet: String)

    case class Greet(name: String)

    val system = ActorSystem("actor-demo-scala")
    val hello = system.actorOf(Props[Hello], "hello")
    hello ! Greeting("Hello")
    hello ! Greet("Bob")
    hello ! Greet("Alice")
    hello ! Greeting("Hola")
    hello ! Greet("Alice")
    hello ! Greet("Bob")
    Thread sleep 1000
    system terminate()

    class Hello extends Actor {
        var greeting = ""

        def receive = {
            case Greeting(greet) => greeting = greet
            case Greet(name) => println(s"$greeting $name")
        }
    }

}
