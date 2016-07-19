package com.bowen.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by bcl on 16/6/6.
  */
object SimpleDataOperation extends App{
    val conf = new SparkConf().setAppName("SimpleDataOperation").setJars(List("/Users/bcl/Workspaces/scala/helloscala/hellospark/target/hellospark-1.0-SNAPSHOT.jar"))
//    conf.setExecutorEnv("spark.driver.extraClassPath","/Users/bcl/app/spark-1.6.1/lib/mysql-connector-java-5.1.36.jar")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    val url =
        "jdbc:mysql://192.168.211.168:3306/spark_data?user=root&password=root"
    val df = sqlContext
        .read
        .format("jdbc")
        .option("url", url)
        .option("dbtable", "test")
        .option("driver", "com.mysql.jdbc.Driver")
        .load()
    df.printSchema()
    val countsByCode = df.groupBy("code").count()
    countsByCode.show()
}
