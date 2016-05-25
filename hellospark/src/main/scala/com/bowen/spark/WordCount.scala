package com.bowen.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by bcl on 16/5/24.
  */
object  WordCount extends App{
    val logFile = "/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("WordCount")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
}
