package com.bowen.kafka

import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object WebPagePopularityValueCalculator extends App {
    val checkpointDir = "popularity-data-checkpoint"
    val msgConsumerGroup = "user-behavior-topic-message-consumer-group"
    val processingInterval = 10
    val conf = new SparkConf().setAppName("WebPagePopularityValueCalculator")
//    val conf = new SparkConf().setAppName("WebPagePopularityValueCalculator").setJars(List("/Users/bcl/Workspaces/scala/helloscala/hellospark/target/hellospark-1.0-SNAPSHOT.jar")).setMaster("spark://192.168.211.168:7077")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(processingInterval.toInt))
    ssc.checkpoint(checkpointDir)
    val kafkaStream = KafkaUtils.createStream(ssc, "localhost:2181", msgConsumerGroup, Map("test" -> 1))
    val msgDataRDD = kafkaStream.map(_._2)
    val popularityData = msgDataRDD.map { msgLine => {
        val dataArr: Array[String] = msgLine.split("\\|")
        val pageID = dataArr(0)
        //calculate the popularity value
        val popValue: Double = dataArr(1).toFloat * 0.8 + dataArr(2).toFloat * 0.8 + dataArr(3).toFloat * 1
        (pageID, popValue)
    }
    }
    //sum the previous popularity value and current value
    val updatePopularityValue = (iterator: Iterator[(String, Seq[Double], Option[Double])]) => {
        iterator.flatMap(t => {
            val newValue: Double = t._2.sum
            val stateValue: Double = t._3.getOrElse(0);
            Some(newValue + stateValue)
        }.map(sumedValue => (t._1, sumedValue)))
    }
    val initialRDD = ssc.sparkContext.parallelize(List(("page1", 0.00)))
    val stateDstream = popularityData.updateStateByKey[Double](updatePopularityValue,
        new HashPartitioner(ssc.sparkContext.defaultParallelism), true, initialRDD)
    //set the checkpoint interval to avoid too frequently data checkpoint which may
    //may significantly reduce operation throughput
    stateDstream.checkpoint(Duration(8 * processingInterval.toInt * 1000))
    //after calculation, we need to sort the result and only show the top 10 hot pages
    stateDstream.foreachRDD { rdd => {
        val sortedData = rdd.map { case (k, v) => (v, k) }.sortByKey(false)
        val topKData = sortedData.take(10).map { case (v, k) => (k, v) }
        topKData.foreach(x => {
            println(x)
        })
    }
    }
    ssc.start()
    ssc.awaitTermination()
}