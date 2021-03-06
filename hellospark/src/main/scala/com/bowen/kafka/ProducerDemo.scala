package com.bowen.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by bcl on 16/6/7.
  */
object ProducerDemo extends App{
    val props = new Properties()
    props.put("bootstrap.servers", "mac-bcl.mini:9092")
    props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks", "all")
    props.put("retries", "0")
    props.put("batch.size", "16384")
    props.put("linger.ms", "1")
    props.put("buffer.memory", "33554432")
    val producer = new KafkaProducer[String,String](props)
    producer.send(new ProducerRecord("test","hello,world"))
    producer.close()
}
