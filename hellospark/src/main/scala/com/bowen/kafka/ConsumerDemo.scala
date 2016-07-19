package com.bowen.kafka

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConversions._
/**
  * Created by bcl on 16/6/7.
  */
object ConsumerDemo extends App{
    val props = new Properties();
    props.put("bootstrap.servers", "mac-bcl.mini:9092");
    props.put("group.id", "test");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    val consumer = new KafkaConsumer[String,String](props);
    consumer.subscribe(util.Arrays.asList("test"))
    while(true){
        val records = consumer.poll(1000)
        records.foreach(p=>{
            println(s"offset = ${p.offset()}, key = ${p.key()}, value = ${p.value()}")
        })
    }

}
