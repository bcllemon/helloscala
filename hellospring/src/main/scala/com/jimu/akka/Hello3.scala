package com.jimu.akka

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}

/**
  * Created by bcl on 16/5/10.
  */
object Hello3 extends App {

    import Greeter._

    val system = ActorSystem("actor-demo-scala")
    val bob = system.actorOf(props("Bob", "Howya doing"))
    val alice = system.actorOf(props("Alice", "Happy to meet you"))
    println(bob.getClass)
    println(alice.getClass)
    bob ! Greet(alice)
    alice ! Greet(bob)
    Thread sleep 1000
    system terminate

    object Greeter {

        case class Greet(peer: ActorRef)

        case object AskName

        case class TellName(name: String)

        def props(name: String, greeting: String) = Props(new Greeter(name, greeting))
    }

    class Greeter(myName: String, greeting: String) extends Actor with ActorLogging{

        import Greeter._

        def receive = {
            case Greet(peer) => peer ! AskName
            case AskName => sender ! TellName(myName)
            case TellName(name) => println(s"$greeting, $name")
        }
    }

}
