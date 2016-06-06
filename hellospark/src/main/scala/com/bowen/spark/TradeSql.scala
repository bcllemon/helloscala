package com.bowen.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by bcl on 16/6/3.
  */
class TradeSql extends App{
    val conf = new SparkConf().setAppName("TradeSql").setJars(List("/Users/bcl/Workspaces/scala/helloscala/hellospark/target/hellospark-1.0-SNAPSHOT.jar"))
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)

}
