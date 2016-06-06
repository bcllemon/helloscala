package com.bowen.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by bcl on 16/5/24.
  */
object  WordCount extends App{
    val logFile = "hdfs://192.168.211.168:9000/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("WordCount").setJars(List("/Users/bcl/Workspaces/scala/helloscala/hellospark/target/hellospark-1.0-SNAPSHOT.jar"))
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))

    val counts = logData.flatMap(line => line.split(" "))
        .map(word => (word, 1))
        .reduceByKey(_ + _)
    println(counts.count())
//    counts.collect().foreach(t=>{println(s"${t._1}:${t._2}")})
}
