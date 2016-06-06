package com.bowen.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.DataFrame
/**
  * Created by bcl on 16/6/6.
  */
object TextSearch extends App{
    val textFilePath= "hdfs://192.168.211.168:9000/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("TextSearch").setJars(List("/Users/bcl/Workspaces/scala/helloscala/hellospark/target/hellospark-1.0-SNAPSHOT.jar"))
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    val textFile = sc.textFile(textFilePath)
    import sqlContext.implicits._
    val df:DataFrame = textFile.toDF("line")
    import df._
    println(df.count())
    println(df.filter(col("line").like("%spark%")).count())
}
