package com.bowen.spark

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

/**
  * Created by bcl on 16/5/24.
  */
object MonteCarlo extends App {
    val conf = new SparkConf().setAppName("MonteCarlo")
    val sc = new SparkContext(conf)

    val total = 100 * 10000
    val local_collection = new Array[Int](total)
    for (i <- 1 until total) {
        local_collection(i - 1) = i
    }
    val rdd = sc.parallelize(local_collection).setName("parallelized_data").cache()

    def map_func(element: Int): (Double, Double) = {
        val x = Random.nextDouble()
        val y = Random.nextDouble()
        return (x, y)
    }

    def map_func2(element: (Double, Double)): Int = {
        val x = element._1
        val y = element._2
        val sum = x * x + y * y
        if (sum < 1) {
            return 1
        }
        return 0
    }

    val rdd2 = rdd.map(map_func).setName("random_point").cache()
    val rdd3 = rdd2.map(map_func2).setName("").cache()
    val in_circle = rdd3.reduce((a, b) => a + b)
    val pi = 4.0 * in_circle / total
    println(s"iterate ${total} times")
    println( s"estimated pi : ${pi}")
}
