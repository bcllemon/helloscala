package com.bowen.kafka

import java.util.Properties

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

class UserBehaviorMsgProducer(brokers: String, topic: String) extends Runnable {
    private val brokerList = brokers
    private val targetTopic = topic
    private val props = new Properties()
    props.put("bootstrap.servers",this.brokerList)
    props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    props.put("retries", "0")
    props.put("batch.size", "16384")
    props.put("linger.ms", "1")
    props.put("buffer.memory", "33554432")
    private val producer = new KafkaProducer[String, String](this.props)

    private val PAGE_NUM = 100
    private val MAX_MSG_NUM = 3
    private val MAX_CLICK_TIME = 5
    private val MAX_STAY_TIME = 10
    //Like,1;Dislike -1;No Feeling 0
    private val LIKE_OR_NOT = Array[Int](1, 0, -1)

    def run(): Unit = {
        val rand = new Random()
        while (true) {
            //how many user behavior messages will be produced
            val msgNum = rand.nextInt(MAX_MSG_NUM) + 1
            try {
                //generate the message with format like page1|2|7.123|1
                for (i <- 0 to msgNum) {
                    var msg = new StringBuilder()
                    msg.append("page" + (rand.nextInt(PAGE_NUM) + 1))
                    msg.append("|")
                    msg.append(rand.nextInt(MAX_CLICK_TIME) + 1)
                    msg.append("|")
                    msg.append(rand.nextInt(MAX_CLICK_TIME) + rand.nextFloat())
                    msg.append("|")
                    msg.append(LIKE_OR_NOT(rand.nextInt(3)))
                    println(msg.toString())
                    //send the generated message to broker
                    sendMessage(msg.toString())
                }
                println("%d user behavior messages produced.".format(msgNum + 1))
            } catch {
                case e: Exception => println(e)
            }
            try {
                //sleep for 5 seconds after send a micro batch of message
                Thread.sleep(5000)
            } catch {
                case e: Exception => println(e)
            }
        }
    }

    def sendMessage(message: String) = {
        try {
            producer.send(new ProducerRecord[String,String](this.topic,message))
        } catch {
            case e: Exception => println(e)
        }
    }
}

object UserBehaviorMsgProducerClient extends App{
        //start the message producer thread
        new Thread(new UserBehaviorMsgProducer("localhost:9092", "test")).start()
}